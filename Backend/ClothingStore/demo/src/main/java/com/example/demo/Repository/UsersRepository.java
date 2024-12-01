package com.example.demo.Repository;

import com.example.demo.DTO.UsersDTO;
import com.example.demo.Models.Users;
import com.example.demo.Responses.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Users> findByPhoneNumber(String phoneNumber);

    @Query("Select new com.example.demo.Responses.UserResponse(u.fullName, u.phoneNumber, u.active) From Users u JOIN Roles r On u.roles.id = r.id Where r.roleName = 'User'")
    List<UserResponse> getAllUserByRoleUser();
}
