package com.rp.sec04;

import com.rp.common.Util;
import com.rp.sec01.subscriber.SubscriberImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class FluxCreateDefaultBehaviour04 {
    private static Logger log = LoggerFactory.getLogger(FluxCreateDefaultBehaviour04.class);

    public static void main(String args[]) throws InterruptedException {
        // Default Behaviour is itjhis will execute even if no subscription is there
        defaultBehaviour();
        onDemand();
    }

    private static void onDemand() {
        var subscriber = new SubscriberImpl();
        var flux = Flux.<String>create((fluxSink) -> {
            // is designed to be used with single subscriber
            fluxSink.onRequest( (request) -> {

                for (int i = 0; i < request && !fluxSink.isCancelled(); i++) {
                    var name = Util.getFaker().name().firstName();
                    log.info("Generated {} : ", name);
                    fluxSink.next(name);
                }
            });
        });
        log.info("Subscribing");
        flux.subscribe(subscriber);

        subscriber.getSubscription().request(2);
        try {
            Util.sleepSeconds(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        subscriber.getSubscription().request(2);
        try {
            Util.sleepSeconds(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        subscriber.getSubscription().cancel();
    }

    private static void defaultBehaviour() {
        var subscriber = new SubscriberImpl();
        var flux = Flux.<String>create((fluxSink) -> {

            for (int i = 0; i < 10; i++) {
                var name = Util.getFaker().name().firstName();
                log.info("Generated {} : ", name);
                fluxSink.next(name);
            }
            fluxSink.complete();
        });
        log.info("Subscribing");
        flux.subscribe(subscriber);

        subscriber.getSubscription().request(2);
        try {
            Util.sleepSeconds(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        subscriber.getSubscription().request(2);
        try {
            Util.sleepSeconds(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        subscriber.getSubscription().cancel();
    }

}
