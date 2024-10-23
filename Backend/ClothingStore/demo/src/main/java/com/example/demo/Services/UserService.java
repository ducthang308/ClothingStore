package com.example.demo.Services;

import com.example.demo.DTO.UsersDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Exception.PermissonDenyException;
import com.example.demo.JWT.JwtToken;
import com.example.demo.Models.Roles;
import com.example.demo.Models.Users;
import com.example.demo.Repository.RolesRepository;
import com.example.demo.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUsersService{
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtToken jwtToken;
    private final AuthenticationManager authenticationManager;
    private final RolesRepository rolesRepository;

    @Override
    public Users createUser(UsersDTO usersDTO) throws Exception {
        String phoneNumber = usersDTO.getPhoneNumber();
        if(usersRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        Roles role = rolesRepository.findById(usersDTO.getRoleId())
                .orElseThrow(()->new DataNotFoundException("Role not found"));
        if(role.getRoleName().toUpperCase().equals(Roles.Admin)){
            throw new PermissonDenyException("You cannot register an ADMIN account");
        }
        Users newUser = Users.builder()
                .fullName(usersDTO.getFullName())
                .phoneNumber(usersDTO.getPhoneNumber())
                .address(usersDTO.getAddress())
                .email(usersDTO.getEmail())
                .password(usersDTO.getPassword())
                .dateOfBirth(usersDTO.getDateOfBirth())
                .facebookAccountId(usersDTO.getFacebookAccountId())
                .googleAccountId(usersDTO.getGoogleAccountId())
                .active(Users.Open)
                .build();
        newUser.setRoles(role);
        if(usersDTO.getFacebookAccountId() == 0 && usersDTO.getGoogleAccountId() == 0){
            String password = usersDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return usersRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<Users> users = usersRepository.findByPhoneNumber(phoneNumber);
        if(users.isEmpty()){
            throw new DataNotFoundException("Invalid phone number Or password");
        }
        Users existingUser = users.get();

        if(existingUser.getFacebookAccountId() == 0 && existingUser.getGoogleAccountId() == 0){
            if(!passwordEncoder.matches(password, existingUser.getPassword())){
                throw new BadCredentialsException("Wrong phone number or password");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password, existingUser.getAuthorities()
        );
        //authenticate with java spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtToken.generationToken(existingUser);
    }
}
