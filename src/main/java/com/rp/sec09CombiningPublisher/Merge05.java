package com.rp.sec09CombiningPublisher;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Merge05 {
    private static Logger log = LoggerFactory.getLogger(Merge05.class);

    public static void main(String args[]) throws InterruptedException {

        demo1();
        demo2();

        Util.sleepSeconds(16);
    }

    private static void demo1() {
        Flux.merge(producer1(), producer2(), producer3())
                .take(2)  // in this case will take only 2
                .subscribe(Util.subscriber("sub 1"));
    }

    private static void demo2() {
        producer1().mergeWith(producer2()).mergeWith(producer3())
                .take(7)  // in this case will take only 2
                .subscribe(Util.subscriber("sub 2"));
    }



    private static Flux<Integer> producer1() {

        return Flux.just(1, 2, 3).transform(Util.fluxLogger("producer1"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer2() {

        return Flux.just(51, 52, 53).transform(Util.fluxLogger("producer2"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer3() {
        return Flux.just(11, 12, 13).transform(Util.fluxLogger("producer3"))
                .delayElements(Duration.ofMillis(10));
    }
}
