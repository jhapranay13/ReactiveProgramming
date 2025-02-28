package com.rp.sec05DoHooksCallbackTransform;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxDelayAndSubscribe04 {
    private static Logger log = LoggerFactory.getLogger(FluxDelayAndSubscribe04.class);

    // it's kind of filter and map
    public static void main(String args[]) throws InterruptedException {
        Flux.range(1, 10).delayElements(Duration.ofSeconds(1)).subscribe(Util.subscriber());
        Util.sleepSeconds(12);
        // Callbacks are doing all the work for subscribe
        Flux.range(1, 10).
                doOnNext(i -> log.info("Recieved >> {}", i)).
                doOnComplete(() -> log.info("Completed >>")).
                subscribe();
        Util.sleepSeconds(12);
    }
}
