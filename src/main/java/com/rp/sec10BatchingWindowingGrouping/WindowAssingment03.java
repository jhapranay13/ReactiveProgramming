package com.rp.sec10BatchingWindowingGrouping;

import com.rp.common.Util;
import com.rp.sec10BatchingWindowingGrouping.window.FileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class WindowAssingment03 {
    private static Logger log = LoggerFactory.getLogger(WindowAssingment03.class);

    /*
    In buffer we wait for the buffer and then give the result to subscriber
    In windowing we switch window. i.e. we can change the subscriber
     */
    public static void main(String args[]) throws InterruptedException {
        var counter = new AtomicInteger(0);
        var fileName = "src/main/resources/file%d.txt";

        eventStream()
            .window(Duration.ofMillis(1000))
            .flatMap( flux -> FileWriter.create(flux, Path.of(fileName.formatted(counter.getAndIncrement()))))
            .subscribe();

        Util.sleepSeconds(10);
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(elem -> "event >> " + elem);
    }
}
