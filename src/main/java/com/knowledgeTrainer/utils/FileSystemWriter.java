package com.knowledgeTrainer.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileSystemWriter {

    public void write(Path path, String content) {
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            Files.writeString(
                    path,
                    content,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to write file: " + path,
                    e
            );
        }
    }
}

