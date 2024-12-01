package com.example.demo.Services;

import com.example.demo.DTO.LoginResponseDTO;
import com.example.demo.DTO.UpdatePassDTO;
import com.example.demo.DTO.UsersDTO;
import com.example.demo.Models.Users;
import com.example.demo.Responses.UserResponse;

import java.util.List;

public interface IUsersService {
    Users createUser(UsersDTO usersDTO) throws Exception;
    LoginResponseDTO login(String phoneNumber, String password) throws Exception;
    Users updatePass(UpdatePassDTO updatePassDTO, Long id) throws Exception;
    List<UserResponse> getAllUser();
}