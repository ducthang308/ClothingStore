package com.example.demo.Services;

import com.example.demo.DTO.UsersDTO;
import com.example.demo.Models.Users;

public interface IUsersService {
    Users createUser(UsersDTO usersDTO) throws Exception;
    String login(String phoneNumber, String password) throws Exception;
}
