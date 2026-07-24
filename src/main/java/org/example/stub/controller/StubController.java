package org.example.stub.controller;

import org.example.stub.dto.LoginRequest;
import org.example.stub.dto.LoginResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
public class StubController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private void delay() {
        try {
            long delay = ThreadLocalRandom.current().nextLong(1000,2001);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @GetMapping("/login")
    public String getLogin() {

        delay();

        return """ 
                {"login": "login1","status":"ok"}
                """;
    }

    @PostMapping("/login")
    public LoginResponse postLogin(@RequestBody LoginRequest request) {

        delay();

        return new LoginResponse(
                request.getLogin(),
                request.getPassword(),
                LocalDateTime.now().format(FORMATTER)
        );
    }
}
