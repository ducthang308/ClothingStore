package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity @NoArgsConstructor
@AllArgsConstructor
@Table(name = "Conversation")
public class Conversation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_at")
    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Users senderId;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Users receiverId;

    @OneToMany(mappedBy = "conversation")
    @JsonBackReference
    private List<Messages> messages = new ArrayList<>();
}