package com.rp.sec13context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

public class ContextPropogation03 {
    private static Logger log = LoggerFactory.getLogger(ContextPropogation03.class);

    public static void main(String args[]) throws InterruptedException {
        // goes from bottom to the top

        getWelcomeMessage()
                //.concatWith(producer1())
                //.concatWith(Flux.merge(producer1(), producer2()))
                .concatWith(Flux.merge(producer1(), producer2().contextWrite(ctx -> Context.empty())))

                .contextWrite(Context.of("a", "z"))
                .contextWrite(Context.of("user", "sam").put("c", "d"))
                .contextWrite(Context.of("a", "b"))
                .subscribe(str -> log.info("{}", str));

    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx -> {
            log.info("Context: {}", ctx);

            if (ctx.hasKey("user")) {
                return Mono.just("Welcome " + ctx.get("user"));
            }
            return Mono.error(new RuntimeException("No user found"));
        });
    }

    private static Mono<String> producer1() {
        return Mono.<String>deferContextual(ctx -> {
            log.info("producer1: {}", ctx);
            return Mono.empty();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private static Mono<String> producer2() {
        return Mono.<String>deferContextual(ctx -> {
            log.info("producer2: {}", ctx);
            return Mono.empty();
        }).subscribeOn(Schedulers.parallel());
    }
}
