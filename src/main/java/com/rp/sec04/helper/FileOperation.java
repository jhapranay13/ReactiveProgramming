package com.rp.sec04.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;


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

    private BufferedReader openFile(String fileName) throws IOException {
        log.info("Opening File {}", fileName);
        return Files.newBufferedReader(PATH.resolve(fileName));
    }

    private BufferedReader readFile(BufferedReader reader, SynchronousSink<String> sink) {

        try {
            var line = reader.readLine();
            log.info("Line read: {}", line);

            if (Objects.isNull(line)) {
                sink.complete();
            } else {
                sink.next(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reader;
    }

    private void closeFile(BufferedReader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Flux<Void> create(String fileName, String content) {
        return Flux.create((fluxSink) -> {
            writeFile(fileName, content);
            fluxSink.complete();
        });
    }

    public Flux<Void> delete(String fileName) {
        return Flux.create((fluxSink) -> {
            deleteFile(fileName);
            fluxSink.complete();
        });
    }

    public Flux<String> read(String fileName) {
        return Flux.generate(() -> openFile(fileName),
                (reader, sink) -> {
                    return readFile(reader, sink);
                });
    }
}
