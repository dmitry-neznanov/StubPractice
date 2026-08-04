package org.example.stub.controller;

import jakarta.validation.Valid;
import org.example.stub.dto.LoginRequest;
import org.example.stub.dto.LoginResponse;
import org.example.stub.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.stub.db.DataBaseWorker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import static org.example.stub.util.DelayManager.delay;

@RestController
@RequestMapping("/api")
public class StubController {

    private final DataBaseWorker dataBaseWorker;
    //private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StubController(DataBaseWorker dataBaseWorker) {
        this.dataBaseWorker = dataBaseWorker;
    }


    //параметр GET /api/login?login=new_user
    @GetMapping("/login")
    public ResponseEntity<User> getLogin(@RequestParam String login) {

        delay();

        User userByLogin = dataBaseWorker.getUserByLogin(login);

        return ResponseEntity.ok(userByLogin);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> postLogin(@RequestBody @Valid LoginRequest request) {

        delay();

        User newUser = new User(
                request.getLogin(),
                request.getPassword(),
                LocalDate.now(),
                request.getEmail()
        );

        int rows = dataBaseWorker.insertUser(newUser);

        if (rows != 2) {
            throw new RuntimeException("User was not created");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new LoginResponse(
                        newUser.getLogin(),
                        newUser.getPassword(),
                        newUser.getRegistrationDate()
                ));
    }
}
