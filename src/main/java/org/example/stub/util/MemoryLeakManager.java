package org.example.stub.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemoryLeakManager {

    private final List<byte[]> memory = new ArrayList<>();

    public void leak(int sizeMb) {

        if (sizeMb < 1 || sizeMb > 50) {
            throw new IllegalArgumentException(
                    "sizeMb must be between 1 and 50"
            );
        }

        byte[] data = new byte[sizeMb * 1024 * 1024];

        memory.add(data);
    }

    public void clear() {
        memory.clear();
    }
}
