package com.example.demo.Repository;

import com.example.demo.DTO.MessageDTO;
import com.example.demo.DTO.MessageResponseDTO;
import com.example.demo.Models.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {
    @Query("Select m From Messages m Where m.conversation = :conversation")
    List<Messages> getAllByConversationId(@Param("conversation") Long conversationId);

    @Query("Select m From Messages m Where m.conversation = :conversation")
    List<Messages> getAllBySenderId(@Param("conversation") Long conversationId);

    List<Messages> findByConversationId(Long conversationId);

//    @Query("SELECT new com.example.demo.DTO.MessageDTO(m.content, m.receiverId.id, u.fullName, m.createAt) " +
//            "FROM Messages m " +
//            "JOIN Users u ON m.receiverId.id = u.id " +
//            "WHERE m.receiverId.id = :receiverId " +
//            "ORDER BY m.createAt DESC")
//    List<Messages> getAllSenderId(@Param("receiverId") Long receiverId);

    @Query("SELECT new com.example.demo.DTO.MessageDTO(m.content, m.receiverId.id, m.createAt, s.fullName) " +
            "FROM Messages m " +
            "JOIN Users u ON m.receiverId.id = u.id " +
            "JOIN Users s ON m.senderId.id = s.id " +
            "WHERE m.receiverId.id = :receiverId " +
            "AND m.createAt = (SELECT MAX(m2.createAt) " +
            "                 FROM Messages m2 " +
            "                 WHERE m2.senderId.id = m.senderId.id AND m2.receiverId.id = :receiverId) " +
            "ORDER BY m.createAt DESC")
    List<MessageDTO> getAllSenderId(@Param("receiverId") Long receiverId);






    @Query("SELECT m FROM Messages m WHERE m.receiverId.id = :receiverId ORDER BY m.createAt DESC")
    List<Messages> findByReceiverId(@Param("receiverId") Long receiverId);


}
