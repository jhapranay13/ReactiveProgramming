package com.rp.sec07ThreadingAndScheduler;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class VirtualThreads03 {
    private static Logger log = LoggerFactory.getLogger(VirtualThreads03.class);

    public static void main(String args[]) throws InterruptedException {
        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true"); // required to use virtual threads
        var flux = Flux.create(sink -> {
                    for (int i = 0; i < 3; i++) {
                        sink.next(i);
                    }
                    sink.complete();
                })
                .doFirst(() -> log.info("first is virtual thread >> {}", Thread.currentThread().isVirtual()))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("Second One"))
                .doOnNext((item) -> log.info("Value {}", item));


        Runnable runnable1 = () -> flux.subscribe(Util.subscriber());
        Thread.ofPlatform().start(runnable1);
        Util.sleepSeconds(10);
    }
}
