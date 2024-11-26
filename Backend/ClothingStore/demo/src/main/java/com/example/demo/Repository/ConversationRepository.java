package com.example.demo.Repository;

import com.example.demo.Models.Conversation;
import com.example.demo.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("SELECT c FROM Conversation c WHERE (c.senderId = :user AND c.receiverId = :receiver) OR (c.senderId = :receiver AND c.receiverId = :user)")
    Optional<Conversation> findBySenderAndReceiver(@Param("user") Users user, @Param("receiver") Users receiver);

    @Query("SELECT DISTINCT c.id FROM Conversation c " +
            "WHERE c.senderId.id = :userId ORDER BY c.createAt DESC")
    Long findConversationIdByUserId(@Param("userId") Long userId);
}


