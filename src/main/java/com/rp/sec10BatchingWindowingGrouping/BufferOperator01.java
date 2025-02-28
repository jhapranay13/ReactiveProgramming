package com.rp.sec10BatchingWindowingGrouping;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class BufferOperator01 {
    private static Logger log = LoggerFactory.getLogger(BufferOperator01.class);

    public static void main(String args[]) throws InterruptedException {
        demo1();
        demo2();
        demo3();
        Util.sleepSeconds(10);
    }

    private static void demo1() {
        eventStream()
                // buffer size optional
                .buffer(3)  // if last item does not arrive it will not complete. Solution demo3
                .subscribe(Util.subscriber("sub1"));
    }

    private static void demo2() {
        eventStream()
                .buffer(Duration.ofMillis(500))
                .subscribe(Util.subscriber("sub2"));
    }

    private static void demo3() {
        eventStream()
                .bufferTimeout(3, Duration.ofSeconds(1))
                .subscribe(Util.subscriber("sub3"));
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(200))
                .take(10)
                .concatWith(Flux.never())
                .map(elem -> "event >> " + elem);
    }
}
