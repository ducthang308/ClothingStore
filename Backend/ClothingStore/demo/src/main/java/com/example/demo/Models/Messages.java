package com.example.demo.Models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    @JsonBackReference
    private Users senderId;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    @JsonBackReference
    private Users receiverId;

    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "create_at", nullable = false)
    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    @JsonManagedReference
    private Conversation conversation;
}
