package com.rp.sec12Sinks;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class SinkThreadSafety03 {
    private static Logger log = LoggerFactory.getLogger(SinkThreadSafety03 .class);

    public static void main(String args[]) throws InterruptedException {
        demo1();
        demo2();
        Util.sleepSeconds(10);
    }
    /* sinks are not thread safe */
    private static void demo1() throws InterruptedException {
        Sinks.Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<Object> flux = sink.asFlux();
        // ArrayList is not thread safe
        var list = new ArrayList<>();
        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {
            var j = i;
            CompletableFuture.runAsync(() -> {
                sink.tryEmitNext(j);
            });
        }
        Util.sleepSeconds(3);
        log.info("List size {}", list.size());
    }
    // Solution to above problem
    private static void demo2() throws InterruptedException {
        // unbouded queue
        Sinks.Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<Object> flux = sink.asFlux();
        // ArrayList is not thread safe
        var list = new ArrayList<>();
        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {
            var j = i;
            CompletableFuture.runAsync(() -> {
                sink.emitNext(j, (signalType, emitResult) -> {
                    return Sinks.EmitResult.FAIL_NON_SERIALIZED.equals(emitResult);
                });
            });
        }
        Util.sleepSeconds(3);
        log.info("List size {}", list.size());
    }
}
