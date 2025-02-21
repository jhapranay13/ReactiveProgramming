package com.rp.common;

import com.github.javafaker.Faker;
import com.rp.sec01.subscriber.SubscriberImpl;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSubscriber<T> implements Subscriber<T> {
    private static Logger log = LoggerFactory.getLogger(SubscriberImpl.class);
    private Subscription subscription;
    private String name;

    public DefaultSubscriber(String name) {
        this.name = name;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T s) {
        log.info("{} Recieved: {}", this.name, s);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("{} On Error {}", this.name, throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("{} Completed", this.name);
    }
}

