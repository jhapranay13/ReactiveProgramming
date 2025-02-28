package com.rp.sec01mono.publisher;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class PublisherImpl implements Publisher<String> {
    @Override
    public void subscribe(Subscriber<? super String> subscriber) {
        Subscription subscription = new SubscriptionImpl(subscriber);
        subscriber.onSubscribe(subscription);
    }
}
