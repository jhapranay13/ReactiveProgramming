package com.rp.sec02Mono;

import com.rp.sec01mono.subscriber.SubscriberImpl;
import reactor.core.publisher.Mono;

public class MonoJust01 {

    public static void main(String args[]) {
        Mono<String> mono = Mono.just("Pranay"); // Creates a Publisher
        SubscriberImpl subscriber = new SubscriberImpl();
        mono.subscribe(subscriber);

        subscriber.getSubscription().request(10);
    }
}
