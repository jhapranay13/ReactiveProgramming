package com.rp.sec10BatchingWindowingGrouping.window;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriter {

    private final Path path;
    private BufferedWriter writer;

    private FileWriter(Path path) {
        this.path = path;
    }

    private void createFile() {
        try {
            this.writer = Files.newBufferedWriter(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeFile() {
        try {
            this.writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(String content) {
        try {
            this.writer.write(content);
            this.writer.newLine();
            this.writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Mono<Void> create(Flux<String> flux, Path path) {
        var writer = new FileWriter(path);
        return flux.doOnNext((elem) -> writer.write(elem))
                .doFirst(() -> writer.createFile())
                .doFinally((signal) -> writer.closeFile()).then();
    }
}
