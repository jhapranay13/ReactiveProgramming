package com.rp.sec10BatchingWindowingGrouping;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Windowing02 {
    private static Logger log = LoggerFactory.getLogger(Windowing02.class);

    /*
    In buffer we wait for the buffer and then give the result to subscriber
    In windowing we switch window. i.e. we can change the subscriber
     */
    public static void main(String args[]) throws InterruptedException {
        eventStream()
                .window(5)
                .flatMap((elem) -> processEvent(elem))
                .subscribe();

        Util.sleepSeconds(10);
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(elem -> "event >> " + elem);
    }

    private static Mono<Void> processEvent(Flux<String> flux) {
        return flux.doOnNext(elem -> System.out.print("*"))
                .doOnComplete(() -> System.out.println())
                .then();
    }
}
