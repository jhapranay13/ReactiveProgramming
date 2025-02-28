package com.rp.sec09CombiningPublisher;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

public class StartWith01 {
    private static Logger log = LoggerFactory.getLogger(StartWith01.class);
    // Order is bottom to top or upstream
    public static void main(String args[]) throws InterruptedException {

        demo1();
        demo2();
        demo3();
        demo4();

        Util.sleepSeconds(10);
    }

    private static void demo1() {
        producer1()
                .startWith(-1, 0) // Starts with this producer and then go with other producer
                .take(3)
                .subscribe(Util.subscriber("sub1"));
    }

    private static void demo2() {
        producer1()
                .startWith(List.of(0, -1)) // Starts with this producer and then go with other producer
                .take(3)
                .subscribe(Util.subscriber("sub2"));
    }

    private static void demo3() {
        producer1()
                .startWith(producer2()) // Starts with this producer and then go with other producer
                .take(3)
                .subscribe(Util.subscriber("sub3"));
    }

    private static void demo4() {
        producer1()
                .startWith(producer2()) // Starts with this producer and then go with other producer
                .startWith(1000)
                .take(3)
                .subscribe(Util.subscriber("sub4"));
    }

    private static Flux<Integer> producer1() {

        return Flux.just(1, 2, 3).doOnSubscribe(s -> log.info("Subscribing to producer1"))
        .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer2() {

        return Flux.just(51, 52, 53).doOnSubscribe(s -> log.info("Subscribing to producer2"))
                .delayElements(Duration.ofMillis(10));
    }
}
