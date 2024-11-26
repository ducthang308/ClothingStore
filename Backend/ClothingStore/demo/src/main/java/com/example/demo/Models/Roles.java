package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Roles implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    public static String Admin = "Admin";
    public static String User = "User";
}
