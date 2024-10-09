package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name = "phone_number", length = 10, nullable = false)
    private String phoneNumber;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "email", length = 200)
    private String email;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "active")
    private String active;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "facebook_account_id")
    private Integer facebookAccountId;

    @Column(name = "google_account_id")
    private Integer googleAccountId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles roles;
}
