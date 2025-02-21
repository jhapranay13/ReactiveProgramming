package com.rp.sec05;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class IfEmptyAndSwitch06 {
    private static Logger log = LoggerFactory.getLogger(IfEmptyAndSwitch06.class);

    public static void main(String args[]) throws InterruptedException {
        ifEmpty();
        ifEmptySwitch();
    }

    private static void ifEmpty() {
        Mono.empty().defaultIfEmpty("fallback>>>>").subscribe(Util.subscriber());
        Flux.range(1, 10).filter(i -> i > 10).defaultIfEmpty(24).subscribe(Util.subscriber());
    }

    private static void ifEmptySwitch() {
        Flux.range(1, 10).filter(i -> i > 10).switchIfEmpty(fallback()).subscribe(Util.subscriber());
    }

    private static Flux<Integer> fallback() {
        return Flux.range(100,3);
    }
}
