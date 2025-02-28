package com.rp.sec13context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class ContextAppendUpdateDelete02 {
    private static Logger log = LoggerFactory.getLogger(ContextAppendUpdateDelete02.class);

    public static void main(String args[]) throws InterruptedException {
        // goes from bottom to the top

        getWelcomeMessage()
                //.contextWrite(ctx -> Context.empty())
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
}
