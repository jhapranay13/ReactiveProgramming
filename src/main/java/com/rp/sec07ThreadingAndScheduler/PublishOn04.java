package com.rp.sec07ThreadingAndScheduler;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class PublishOn04 {
    private static Logger log = LoggerFactory.getLogger(PublishOn04.class);

    public static void main(String args[]) throws InterruptedException {

        var flux = Flux.create(sink -> {
                    for (int i = 0; i < 3; i++) {
                        sink.next(i);
                    }
                    sink.complete();
                })  // incase of multiple publish on the closest one to subscriber gets priority
                //publish on is downstream subscribe on is upstream
                .publishOn(Schedulers.parallel())
                .doFirst(() -> log.info("first is virtual thread >> {}", Thread.currentThread().isVirtual()))
                .publishOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("Second One"))
                .doOnNext((item) -> log.info("Value {}", item));


        Runnable runnable1 = () -> flux.subscribe(Util.subscriber());
        Thread.ofPlatform().start(runnable1);
        Util.sleepSeconds(10);
    }
}
