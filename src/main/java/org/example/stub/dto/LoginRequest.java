package org.example.stub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Login must not be empty")
    private String login;

    @NotBlank(message = "Password must not be empty")
    private String password;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email is not correct")
    private String email;
}
