package org.example.stub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;

    private LocalDate registrationDate;

}