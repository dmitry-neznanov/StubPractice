package org.example.stub.controller;

import jakarta.validation.Valid;
import org.example.stub.db.FileWorker;
import org.example.stub.dto.LoginRequest;
import org.example.stub.dto.LoginResponse;
import org.example.stub.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.stub.db.DataBaseWorker;

import java.time.LocalDate;


import static org.example.stub.util.DelayManager.delay;

@RestController
@RequestMapping("/api")
public class StubController {

    private final DataBaseWorker dataBaseWorker;
    private final FileWorker fileWorker;


    public StubController(DataBaseWorker dataBaseWorker, FileWorker fileWorker) {
        this.dataBaseWorker = dataBaseWorker;
        this.fileWorker = fileWorker;
    }


    //параметр GET /api/login?login=new_user
    @GetMapping("/login")
    public ResponseEntity<User> getLogin(@RequestParam String login) {

        delay();

        User userByLogin = dataBaseWorker.getUserByLogin(login);

        fileWorker.writeUser(userByLogin);

        return ResponseEntity.ok(userByLogin);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> postLogin(@RequestBody @Valid User newUser) {

        delay();

        dataBaseWorker.insertUser(newUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new LoginResponse(
                        newUser.getLogin(),
                        newUser.getPassword(),
                        newUser.getRegistrationDate()
                ));
    }

    @GetMapping("/random")
    public ResponseEntity<String> getRandom() {

        delay();

        return ResponseEntity.ok(fileWorker.readRandomLine());
    }
}
