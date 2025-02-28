package com.rp.sec06HotAndColdPublisher;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class AutoConnect03 {
    private static Logger log = LoggerFactory.getLogger(AutoConnect03.class);

    public static void main(String args[]) throws InterruptedException {
        Flux<String> movieStream = movieStream().publish().autoConnect(0); // Streams even if no subscriber
        Util.sleepSeconds(2);

        movieStream.take(2).subscribe(Util.subscriber("sub1"));
        Util.sleepSeconds(3);
        movieStream.take(2).subscribe(Util.subscriber("sub3"));

        Util.sleepSeconds(3);
        movieStream.take(2).subscribe(Util.subscriber("sub4"));

        Util.sleepSeconds(12);

    }

    private static Flux<String> movieStream() {
        return Flux.generate(
                () -> {
                    log.info("recieved the request");
                    return 1;
                },
                (state, sink) -> {
                    var scene = "movie scene " + state;
                    log.info("Playing >> {} ", scene);
                    sink.next(scene);
                    return ++state;
                }
        ).take(10).delayElements(Duration.ofMillis(1000)).cast(String.class);
    }
}
