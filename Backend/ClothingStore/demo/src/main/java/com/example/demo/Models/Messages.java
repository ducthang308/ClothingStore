package com.example.demo.Models;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "messages")
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id")
    private int senderId;
    @Column(name = "receiver_id")
    private int receiverId;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "createAt", nullable = false)
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;
}
