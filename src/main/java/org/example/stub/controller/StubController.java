package org.example.stub.controller;

import jakarta.validation.Valid;
import org.example.stub.dto.LoginResponse;
import org.example.stub.dto.User;
import org.example.stub.util.DelayManager;
import org.example.stub.util.MemoryLeakManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.stub.db.DataBaseWorker;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api")
public class StubController {

    private final DataBaseWorker dataBaseWorker;
    private final MemoryLeakManager memoryLeakManager;

    public StubController(DataBaseWorker dataBaseWorker,
                          MemoryLeakManager memoryLeakManager) {
        this.dataBaseWorker = dataBaseWorker;
        this.memoryLeakManager = memoryLeakManager;
    }


    @GetMapping("/login")
    public CompletableFuture<ResponseEntity<User>> getLogin(
            @RequestParam String login) {

        return DelayManager.delayAsync()
                .thenApply(v -> {

                    User user = dataBaseWorker.getUserByLogin(login);

                    return ResponseEntity.ok(user);
                });
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<LoginResponse>> postLogin(
            @RequestBody @Valid User newUser) {

        return DelayManager.delayAsync()
                .thenApply(v -> {

                    dataBaseWorker.insertUser(newUser);

                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new LoginResponse(
                                    newUser.getLogin(),
                                    newUser.getPassword(),
                                    newUser.getRegistrationDate()
                            ));
                });
    }

    @GetMapping("/memory-leak")
    public ResponseEntity<String> memoryLeak(
            @RequestParam(defaultValue = "1") int sizeMb) {

        memoryLeakManager.leak(sizeMb);

        return ResponseEntity.ok(
                "Allocated and retained " + sizeMb + " MB"
        );
    }

    @PostMapping("/memory-leak/clear")
    public ResponseEntity<String> clearMemoryLeak() {

        memoryLeakManager.clear();

        return ResponseEntity.ok("Memory leak storage cleared");
    }
}
