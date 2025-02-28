package com.rp.sec05DoHooksCallbackTransform;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ErrorHandling05 {
    private static Logger log = LoggerFactory.getLogger(ErrorHandling05.class);

    public static void main(String args[]) throws InterruptedException {
        onErrorReturn();
        onErrorResume();
        onErrorComplete();
        onErrorContinue();
    }

    private static void onErrorReturn() {
        Flux.range(1, 10).
                map(i -> i ==5 ? i / 0 : i).    // intentional Error
                onErrorReturn(-1).
                subscribe(Util.subscriber());
        // Same Error Handling with Mono
        Flux.range(1, 10).
                map(i -> i ==5 ? i / 0 : i).    // intentional Error
                onErrorReturn(IllegalArgumentException.class, -1).
                onErrorReturn(ArithmeticException.class, -5).
                subscribe(Util.subscriber());
    }

    private static void onErrorResume() {
        Mono.just(5).
            map( i -> i == 5 ? i / 0 : i).
            onErrorResume(ex -> fallback()).  // also like onErrorReturn for specific and default exception
            subscribe(Util.subscriber());
    }

    private static void onErrorComplete() {
        Mono.error(() -> new RuntimeException("Error")).onErrorComplete().subscribe(Util.subscriber());
    }

    private static void onErrorContinue() {
        Flux.range(1, 10).
                map(i -> i ==5 ? i / 0 : i).    // intentional Error
                onErrorContinue((throwable, o) -> log.error(">>> error {}", o, throwable)).
                subscribe(Util.subscriber());
    }

    private static Mono<Integer> fallback() {
        return Mono.fromSupplier(() -> {
            return Util.getFaker().random().nextInt(10, 100);
        });
    }
}
