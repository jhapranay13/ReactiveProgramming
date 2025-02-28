package com.rp.sec01mono.publisher;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionImpl implements Subscription {
    private static Logger log = LoggerFactory.getLogger(SubscriptionImpl.class);
    private Subscriber<? super String> subscriber;
    private boolean isCancelled;
    private final Faker faker;
    private int count = 0;

    public SubscriptionImpl(Subscriber<? super String> subscriber) {
        this.subscriber = subscriber;
        this.faker = Faker.instance();
    }

    @Override
    public void request(long l) {

        if (isCancelled) {
            return;
        }
        log.info("Subscriber has requsted {} items", l);

        for (int i = 0; i < l && count < 10; i++) {
            this.subscriber.onNext(this.faker.internet().emailAddress());
            count++;
        }

        if (count >= 10) {
            log.info("No More data to produce");
            this.subscriber.onComplete();
            this.isCancelled = true;
        }
    }

    @Override
    public void cancel() {
        log.info("Subscriber has cancelled");
        this.isCancelled = true;
    }
}
