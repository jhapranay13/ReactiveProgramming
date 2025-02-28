package com.rp.sec06HotAndColdPublisher;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotPublisher02 {

    private static Logger log = LoggerFactory.getLogger(HotPublisher02.class);

    public static void main(String args[]) throws InterruptedException {
        Flux<String> movieStream = movieStream().share(); // without share this becomes cold publisher
        // share is equivalent to movieStream().publish().refCount(1); which means at least one subscriber
        // required to start streaming. it will stop if subscriber is less than ref count
        Util.sleepSeconds(2);

        movieStream.subscribe(Util.subscriber("sub1"));
        Util.sleepSeconds(3);
        movieStream.subscribe(Util.subscriber("sub3"));

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
