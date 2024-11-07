package com.example.demo.Repository;

import com.example.demo.Models.Conversation;
import com.example.demo.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("SELECT c FROM Conversation c WHERE c.senderId = :user OR c.receiverId = :user")
    Optional<Conversation> findByUser(@Param("user") Users user);
}
