package com.rp.sec12Sinks;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class SinkMultiCast04 {
    private static Logger log = LoggerFactory.getLogger(SinkMultiCast04 .class);

    public static void main(String args[]) throws InterruptedException {
        //demo1();
        demo2();
        Util.sleepSeconds(15);
    }
    /* sinks are not thread safe */
    private static void demo1() throws InterruptedException {
        // bounded queue max value 256. or override the property
        Sinks.Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer();
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("dan"));
        sink.tryEmitNext("hi");
        sink.tryEmitNext("How are you");
        sink.tryEmitNext("?");
        Util.sleepSeconds(2);

        flux.subscribe(Util.subscriber("jake"));
        sink.tryEmitNext("Silly");
    }

    private static void demo2() throws InterruptedException {
        // bounded queue max value 256. or override the property
        Sinks.Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer();
        Flux<Object> flux = sink.asFlux();


        sink.tryEmitNext("hi");
        sink.tryEmitNext("How are you");
        sink.tryEmitNext("?");
        Util.sleepSeconds(2);
        // in this once the message is consumed by one subscriber only remaining will be available for all the other
        flux.subscribe(Util.subscriber("tam"));
        flux.subscribe(Util.subscriber("ram"));
        flux.subscribe(Util.subscriber("bam"));
        sink.tryEmitNext("Silly");
    }
}
