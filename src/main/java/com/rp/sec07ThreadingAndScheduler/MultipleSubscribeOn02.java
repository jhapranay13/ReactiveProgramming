package com.rp.sec07ThreadingAndScheduler;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class MultipleSubscribeOn02 {
    private static Logger log = LoggerFactory.getLogger(MultipleSubscribeOn02.class);

    public static void main(String args[]) throws InterruptedException {
        var flux = Flux.create(sink -> {
            for (int i = 0; i < 3; i++) {
                sink.next(i);
            }
            sink.complete();
        })
                .subscribeOn(Schedulers.newParallel("Some parallel")) // Schedulars.immediate() to use the current thread only
                .doFirst(() -> log.info("first One"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("Second One"))
                .doOnNext((item) -> log.info("Value {}", item));
        // if multiple subscribe on closest one to the producer will take the priority

       // flux.subscribe(Util.subscriber());

        Runnable runnable1 = () -> flux.subscribe(Util.subscriber());
        Thread.ofPlatform().start(runnable1);
        Util.sleepSeconds(10);
    }
}
