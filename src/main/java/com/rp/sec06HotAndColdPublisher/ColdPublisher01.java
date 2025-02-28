package com.rp.sec06HotAndColdPublisher;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * Cold publisher:- Data is streamed to each subscriber independently
 * hot subscriber. Data is shared between subscribers. it's like tv channel brodcasting program, if subscriber is late
 * it misses the earlier dat
 *
 * sink is for single subscriber
 */

public class ColdPublisher01 {
    private static Logger log = LoggerFactory.getLogger(ColdPublisher01.class);



    public static void main(String args[]) throws InterruptedException {
        Flux<Integer> flux = Flux.create(sink -> {
            for (int i = 0; i < 3; i++) {
                sink.next(i);
            }
            sink.complete();
        });

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));

        AtomicInteger aint = new AtomicInteger(0);
        Flux<Integer> flux1 = Flux.create(sink -> {
            for (int i = 0; i < 3; i++) {
                sink.next(aint.getAndIncrement());
            }
            sink.complete();
        });

        flux1.subscribe(Util.subscriber("sub3"));
        flux1.subscribe(Util.subscriber("sub4"));
    }

}
