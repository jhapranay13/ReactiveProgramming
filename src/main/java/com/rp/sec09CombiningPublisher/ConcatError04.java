package com.rp.sec09CombiningPublisher;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class ConcatError04 {
    private static Logger log = LoggerFactory.getLogger(ConcatError04.class);

    public static void main(String args[]) throws InterruptedException {

        demo1();
        demo2();

        Util.sleepSeconds(16);
    }

    private static void demo1() {
        producer1()
                .concatWith(producer3())
                .concatWith(producer2())
                .subscribe(Util.subscriber("sub 1"));
    }

    private static void demo2() {
        Flux.concatDelayError(producer1(), producer3(), producer2()).subscribe(Util.subscriber("sub 2"));
    }


    private static Flux<Integer> producer1() {

        return Flux.just(1, 2, 3).doOnSubscribe(s -> log.info("Subscribing to producer1"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer2() {

        return Flux.just(51, 52, 53).doOnSubscribe(s -> log.info("Subscribing to producer2"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer3() {
        return Flux.error(new RuntimeException("Concat Error"));
    }
}
