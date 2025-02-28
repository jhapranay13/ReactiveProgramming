package com.rp.sec12Sinks;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class MulticastBestEffort05 {
    private static Logger log = LoggerFactory.getLogger(MulticastBestEffort05 .class);

    public static void main(String args[]) throws InterruptedException {
        // demo1();
        // demo2();
        demo3();
        Util.sleepSeconds(15);
    }

    private static void demo1() throws InterruptedException {
        // problem is that the buffer size is small
        System.setProperty("reactor.bufferSize.small", "16");
        Sinks.Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer();
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("dan"));

        for (int i = 0; i <= 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("emitted: " + i + ", result: " + result);
        }
    }
    // focuses on the first subscriber which is faster
    private static void demo2() throws InterruptedException {
        // problem is that the buffer size is small
        System.setProperty("reactor.bufferSize.small", "16");
        Sinks.Many<Object> sink = Sinks.many().multicast().directBestEffort();
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("dan"));

        for (int i = 0; i <= 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("emitted: " + i + ", result: " + result);
        }
    }

    // focuses on the first subscriber which is faster
    private static void demo3() throws InterruptedException {
        // problem is that the buffer size is small
        System.setProperty("reactor.bufferSize.small", "16");
        Sinks.Many<Object> sink = Sinks.many().multicast().directBestEffort();
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.onBackpressureBuffer().delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("dan"));

        for (int i = 0; i <= 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("emitted: " + i + ", result: " + result);
        }
    }

}
