package com.rp.sec03Flux;

import com.rp.common.Util;
import com.rp.sec01mono.subscriber.SubscriberImpl;
import com.rp.sec03Flux.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FluxVsList06 {
    private static Logger log = LoggerFactory.getLogger(FluxVsList06.class);

    public static void main(String args[]) throws InterruptedException {
        var list = NameGenerator.getNameList(5);
        log.info("List {}", list);

        NameGenerator.getNameFlux(5).subscribe(Util.subscriber());

        // In this approach we can cancel if the record is good for us to use
        SubscriberImpl subscriber = new SubscriberImpl();
        NameGenerator.getNameFlux(5).subscribe(subscriber);

        subscriber.getSubscription().request(3);
        subscriber.getSubscription().cancel();
    }
}
