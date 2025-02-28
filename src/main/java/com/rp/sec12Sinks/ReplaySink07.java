package com.rp.sec12Sinks;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class ReplaySink07 {
    private static Logger log = LoggerFactory.getLogger(ReplaySink07 .class);

    public static void main(String args[]) throws InterruptedException {
        //demo1();
        demo2();

        Util.sleepSeconds(15);
    }
    private static void demo1() throws InterruptedException {
        Sinks.Many<Object> sink = Sinks.many().replay().all();
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
        Sinks.Many<Object> sink = Sinks.many().replay().limit(1);
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
}
