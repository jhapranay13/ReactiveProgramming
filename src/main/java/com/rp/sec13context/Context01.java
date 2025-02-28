package com.rp.sec13context;

import com.rp.common.Util;
import com.rp.sec12Sinks.assingment.SlackMemeber;
import com.rp.sec12Sinks.assingment.SlackRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class Context01 {
    private static Logger log = LoggerFactory.getLogger(Context01.class);

    public static void main(String args[]) throws InterruptedException {
       getWelcomeMessage()
               .contextWrite(Context.of("a", "b"))
               .subscribe(str -> log.info("{}", str));

        getWelcomeMessage()
                .contextWrite(Context.of("user", "sam"))
                .subscribe(str -> log.info("{}", str));

    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx -> {

            if (ctx.hasKey("user")) {
                return Mono.just("Welcome " + ctx.get("user"));
            }
            return Mono.error(new RuntimeException("No user found"));
        });
    }
}
