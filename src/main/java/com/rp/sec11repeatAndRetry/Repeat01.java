package com.rp.sec11repeatAndRetry;

import com.rp.common.Util;
import com.rp.sec10BatchingWindowingGrouping.GroupBy04;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class Repeat01 {
    private static Logger log = LoggerFactory.getLogger(Repeat01.class);

    /*
    Creating Fluxes for each group so each can be processed separately with separate operator
     */
    public static void main(String args[]) throws InterruptedException {
        //demo1();
        //demo2();
        demo3();
        demo4();
        Util.sleepSeconds(10);
    }

    private static void demo1() {
        var mono = Mono.fromSupplier(() -> {
            return Util.getFaker().country().name();
        });
        var subscriber = Util.subscriber();
        mono.repeat(3).subscribe(subscriber);
    }

    private static void demo2() {
        var mono = Mono.fromSupplier(() -> {
            return Util.getFaker().country().name();
        });
        var subscriber = Util.subscriber();
        mono.repeat().takeUntil(str -> str.equalsIgnoreCase("canada")).subscribe(subscriber);
    }

    private static void demo3() {
        var mono = Mono.fromSupplier(() -> {
            return Util.getFaker().country().name();
        });
        // Predicate can also be used
        AtomicInteger aint = new AtomicInteger(0);
        var subscriber = Util.subscriber();
        mono.repeat(() -> aint.getAndIncrement() < 3).takeUntil(str -> str.equalsIgnoreCase("canada")).subscribe(subscriber);
    }

    private static void demo4() {
        var mono = Mono.fromSupplier(() -> {
            return Util.getFaker().country().name();
        });
        // Predicate can also be used
        AtomicInteger aint = new AtomicInteger(0);
        var subscriber = Util.subscriber();
        mono.repeatWhen((flux) -> flux.delayElements(Duration.ofSeconds(2))).
                take(2).subscribe(subscriber);
    }

    private static Mono<String> getCountryName() {
        return Mono.fromSupplier(() -> {
            return Util.getFaker().country().name();
        });
    }
}
