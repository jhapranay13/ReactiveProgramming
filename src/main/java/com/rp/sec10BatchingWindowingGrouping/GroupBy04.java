package com.rp.sec10BatchingWindowingGrouping;

import com.rp.common.Util;
import com.rp.sec10BatchingWindowingGrouping.window.FileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class GroupBy04 {
    private static Logger log = LoggerFactory.getLogger(GroupBy04.class);

    /*
    Creating Fluxes for each group so each can be processed separately with separate operator
     */
    public static void main(String args[]) throws InterruptedException {
       Flux.range(1, 30)
               .delayElements(Duration.ofSeconds(1))
               .groupBy(i -> i % 2)
               .flatMap(grpFlux -> processEvent(grpFlux))
               .subscribe();
       Util.sleepSeconds(60);
    }

    private static Mono<Void> processEvent(GroupedFlux<Integer, Integer> grpFlux) {
        log.info("Recieved Flux for {}", grpFlux.key());
        return grpFlux.doOnNext((elem) -> log.info("Key: {} item: {}", grpFlux.key(), elem))
                .then();
    }
}
