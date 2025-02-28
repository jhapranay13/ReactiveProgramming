package com.rp.sec12Sinks;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class SinkUnicast02 {
    private static Logger log = LoggerFactory.getLogger(SinkUnicast02.class);

    public static void main(String args[]) throws InterruptedException {
        demo1();
        demo2();
        Util.sleepSeconds(10);
    }

    private static void demo1() {
        Sinks.Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<Object> flux = sink.asFlux();
        sink.tryEmitNext("hi");
        sink.tryEmitNext("How are you");
        sink.tryEmitNext("?");
        flux.subscribe(Util.subscriber("One"));
    }

    private static void demo2() {
        Sinks.Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<Object> flux = sink.asFlux();
        sink.tryEmitNext("hi");
        sink.tryEmitNext("How are you");
        sink.tryEmitNext("?");
        flux.subscribe(Util.subscriber("Two"));
        flux.subscribe(Util.subscriber("Three"));
    }
}
