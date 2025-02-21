package com.rp.sec01.subscriber;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SubscriberImpl implements Subscriber<String> {
    private static Logger log = LoggerFactory.getLogger(SubscriberImpl.class);
    private Subscription subscription;

    public Subscription getSubscription() {
        return subscription;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(String s) {
        log.info("Recieved: {}", s);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("On Error {}", throwable);
    }

    @Override
    public void onComplete() {
        log.info("Completed");
    }
}
