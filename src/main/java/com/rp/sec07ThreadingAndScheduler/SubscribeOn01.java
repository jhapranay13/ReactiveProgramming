package com.rp.sec07ThreadingAndScheduler;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SubscribeOn01 {
    private static Logger log = LoggerFactory.getLogger(SubscribeOn01.class);

    public static void main(String args[]) throws InterruptedException {
        var flux = Flux.create(sink -> {
            for (int i = 0; i < 3; i++) {
                sink.next(i);
            }
            sink.complete();
        }).doOnNext((item) -> log.info("Value {}", item));

        flux
                .doFirst(() -> log.info("first One"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("Second One"))
                .subscribe(Util.subscriber());

        Runnable runnable1 = () -> flux
                .doFirst(() -> log.info("first One"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("Second One"))
                .subscribe(Util.subscriber());
        Runnable runnable2 = () -> flux
                .doFirst(() -> log.info("first One"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("Second One"))
                .subscribe(Util.subscriber());
        Thread.ofPlatform().start(runnable1);
        Thread.ofPlatform().start(runnable2);
        Util.sleepSeconds(10);
    }
}
