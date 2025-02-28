package com.rp.sec12Sinks;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class SinkManyMulticastDirectOrNothing06 {
    private static Logger log = LoggerFactory.getLogger(SinkManyMulticastDirectOrNothing06 .class);

    public static void main(String args[]) throws InterruptedException {
        demo1();
        //demo2();

        Util.sleepSeconds(15);
    }
    private static void demo1() throws InterruptedException {
        // problem is that the buffer size is small
        System.setProperty("reactor.bufferSize.small", "16");
        Sinks.Many<Object> sink = Sinks.many().multicast().directAllOrNothing();
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("dan"));

        for (int i = 0; i <= 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("emitted: " + i + ", result: " + result);
        }
    }
}
