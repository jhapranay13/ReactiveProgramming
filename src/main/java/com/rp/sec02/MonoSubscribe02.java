package com.rp.sec02;

import com.rp.sec01.subscriber.SubscriberImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class MonoSubscribe02 {
    private static Logger log = LoggerFactory.getLogger(MonoSubscribe02.class);

    public static void main(String args[]) {
        Mono<String> mono = Mono.just("Pranay"); // Creates a Publisher
        mono.subscribe((i) -> log.info("Subscribe from Consmer -> {}", i),
                (error) -> log.error("Error {} ", error),  // Optional OnError
                () -> log.info("On Complete"),  // optional on Complete
                subscription -> subscription.request(1) // Optional
        );
    }
}
