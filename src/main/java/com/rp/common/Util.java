package com.rp.common;

import com.github.javafaker.Faker;
import com.rp.sec09CombiningPublisher.Merge05;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.UnaryOperator;

public class Util {
    private static final Faker faker = Faker.instance();
    private static Logger log = LoggerFactory.getLogger(Util.class);


    public static <T> Subscriber<T> subscriber() {
        return new DefaultSubscriber<>("");
    }

    public static <T> Subscriber<T> subscriber(String name) {
        return new DefaultSubscriber<>(name);
    }


    public static void main(String args[]) {
        Mono<Integer> mono = Mono.just(1);

        mono.subscribe(subscriber());
        mono.subscribe(subscriber("sub 2"));
    }

    public static Faker getFaker() {
        return faker;
    }

    public static void sleepSeconds(int t) throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(t));
    }

    public static <T>UnaryOperator<Flux<T>> fluxLogger(String name) {
        return flux -> flux.doOnSubscribe((s) -> log.info("Subscribed to >> {}", name))
                .doOnCancel(() -> log.info("Canceled >> {}", name))
                .doOnComplete(() -> log.info("{} complete", name));
    }
}
