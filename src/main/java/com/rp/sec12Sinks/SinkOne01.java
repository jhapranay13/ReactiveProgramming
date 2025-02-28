package com.rp.sec12Sinks;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class SinkOne01 {
    private static Logger log = LoggerFactory.getLogger(SinkOne01.class);

    public static void main(String args[]) throws InterruptedException {
        demo1();
        demo2();
        demo3();
        Util.sleepSeconds(10);
    }

    private static void demo1() {
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();
        mono.subscribe(Util.subscriber("One"));
        sink.tryEmitValue("Hi");
    }

    private static void demo2() {
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();
        mono.subscribe(Util.subscriber("Two"));
        mono.subscribe(Util.subscriber("Three"));

        sink.tryEmitValue("Hi");
    }

    private static void demo3() {
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();
        mono.subscribe(Util.subscriber("Four"));
        sink.emitValue("Hi", ( signalType, emitResult) -> {
            log.info("Signal Type: {}", signalType.name());
            log.info("Emit Result: {}", emitResult.name());
            return false;
        });
        // Error handler. Boolean value return type is retry flag.
        // if true retry else don't
        // Since previous emit was successful this won't be successful
        // coz it's Mono Type so it is not supposed to emit anything else
        sink.emitValue("Hello", ( signalType, emitResult) -> {
            log.info("Signal Type: {}", signalType.name());
            log.info("Emit Result: {}", emitResult.name());
            return false;
        });
    }
}
