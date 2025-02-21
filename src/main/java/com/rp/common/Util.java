package com.rp.common;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Util {
    private static final Faker faker = Faker.instance();

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
}
