package com.rp.sec11repeatAndRetry;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class Retry02 {
    private static Logger log = LoggerFactory.getLogger(Retry02.class);


    public static void main(String args[]) throws InterruptedException {
       // demo1();
       // demo2();
       // demo3();
        demo4();
       Util.sleepSeconds(10);
    }

    private static void demo1() {
        getCountryName().retry(3).subscribe(Util.subscriber()); // if no number in retry it wil; retry again and again
    }

    private static void demo2() {
        getCountryName().retryWhen(Retry.max(3)).subscribe(Util.subscriber()); // if no number in retry it wil; retry again and again
    }

    private static void demo3() {
        getCountryName().retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)).doBeforeRetry((elem) -> log.info("Retrying"))).subscribe(Util.subscriber()); // if no number in retry it wil; retry again and again
    }

    private static void demo4() {
        getCountryName().retryWhen(Retry.
                fixedDelay(3, Duration.ofSeconds(2))
                .filter(ex -> RuntimeException.class.equals(ex.getClass()))
                //                .filter(ex -> IllegalArgumentException.class.equals(ex.getClass()))
                        .onRetryExhaustedThrow((spec, signal) -> signal.failure()))
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getCountryName() {
        var aint = new AtomicInteger(0);
        return Mono.fromSupplier(() -> {

            if (aint.getAndIncrement() < 3) {
                throw new RuntimeException("oops");
            }
            return Util.getFaker().country().name();
        }).doOnError((err) -> log.info("Error {}", err.getMessage()))
                .doOnSubscribe((elem) -> log.info("Subscribing"));
    }
}
