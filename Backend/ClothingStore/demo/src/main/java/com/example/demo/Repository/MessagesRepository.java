package com.example.demo.Repository;

import com.example.demo.Models.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {
    @Query("Select m From Messages m Where m.conversation = :conversation")
    List<Messages> getAllByConversationId(@Param("conversation") Long conversationId);

}
