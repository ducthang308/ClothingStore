package com.example.demo.Repository;

import com.example.demo.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    //boolean existsByPhone(String phone);
    //Optional<Users> findByPhone(String phone);
}
