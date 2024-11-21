package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.Date;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDTO {
    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone is required")
    private String phoneNumber;

    @JsonProperty("address")
    private String address;

    @JsonProperty("password")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("retype_pass")
    private String retypePass;

    @JsonProperty("new_pass")
    private String newPass;

    @JsonProperty("date_of_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId = 0;

    @JsonProperty("google_account_id")
    private int googleAccountId = 0;

    @JsonProperty("role_id")
    private Long roleId;
}