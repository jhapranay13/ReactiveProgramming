package com.rp.sec03;

import com.rp.common.Util;
import com.rp.sec01.subscriber.SubscriberImpl;
import com.rp.sec03.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxInterval07 {
    private static Logger log = LoggerFactory.getLogger(FluxInterval07.class);

    public static void main(String args[]) throws InterruptedException {
        Flux.interval(Duration.ofMillis(500)).subscribe(Util.subscriber());
        Util.sleepSeconds(3);
    }
}
