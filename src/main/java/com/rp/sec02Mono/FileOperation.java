package com.rp.sec02Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileOperation {
    private static Logger log = LoggerFactory.getLogger(FileOperation.class);
    private static  Path PATH = Path.of("src/main/resources");

    private void writeFile(String file, String content) {
        try {

            if (!Files.exists(PATH.resolve(file))) {
                log.info("File {} does not exist", PATH.resolve(file));

                Files.createFile(PATH.resolve(file));
            }
            Files.writeString(PATH.resolve(file), content);
            log.info("Writing file {}", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFile(String file) {
        log.info("deleting file {}", file);
        try {
            Files.delete(PATH.resolve(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Mono<Void> write(String fileName, String content) {
        return Mono.fromRunnable(() -> this.writeFile(fileName, content));
    }

    public Mono<Void> delete(String fileName) {
        return Mono.fromRunnable(() -> this.deleteFile(fileName));
    }

    public Mono<String> read(String fileName) {
        return Mono.fromCallable(() -> Files.readString(PATH.resolve(fileName)));
    }
}
