package org.example.stub.controller;

import org.example.stub.dto.LoginRequest;
import org.example.stub.dto.LoginResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class StubController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/login")
    public String getLogin() {
        return """ 
                {"login": "login1","status":"ok"}
                """;
    }

    @PostMapping("/login")
    public LoginResponse postLogin(@RequestBody LoginRequest request) {

        return new LoginResponse(
                request.getLogin(),
                request.getPassword(),
                LocalDateTime.now().format(FORMATTER)
        );
    }
}
