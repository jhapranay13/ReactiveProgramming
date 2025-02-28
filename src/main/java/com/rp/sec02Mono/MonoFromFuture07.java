package com.rp.sec02Mono;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class MonoFromFuture07 {
    private static Logger log = LoggerFactory.getLogger(MonoFromFuture07.class);

    public static void main(String args[]) throws InterruptedException {
        // This is not lazy so getName gets executed regardless of subscribe
        Mono.fromFuture(getName()).subscribe(Util.subscriber());

        Util.sleepSeconds(3);

        // Using supplier for above solution
        Mono.fromFuture(() -> getName()).subscribe(Util.subscriber());

        Util.sleepSeconds(3);
    }

    private static CompletableFuture<String> getName() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Generating name");
            return Util.getFaker().name().firstName();
        });
    }
}
