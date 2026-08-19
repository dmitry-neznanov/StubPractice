package org.example.stub.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.stub.dto.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class FileWorker {

    private static final Path USER_FILE = Path.of("users.txt");
    private static final Path RANDOM_FILE = Path.of("randomData.txt");

    private final ObjectMapper objectMapper;

    public FileWorker(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public void writeUser(User user) {

        try {
            Files.writeString(
                    USER_FILE,
                    objectMapper.writeValueAsString(user) + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readRandomLine() {

        try {

            List<String> lines = Files.readAllLines(RANDOM_FILE);

            if (lines.isEmpty()) {
                throw new RuntimeException("Файл пуст");
            }

            int index = ThreadLocalRandom.current().nextInt(lines.size());

            return lines.get(index);

        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла", e);
        }
    }
}
