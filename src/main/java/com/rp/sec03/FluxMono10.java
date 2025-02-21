package com.rp.sec03;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxMono10 {
    private static Logger log = LoggerFactory.getLogger(com.rp.sec03.FluxMono10.class);

    public static void main(String args[]) throws InterruptedException {
       var mono = getUserName(1);
       save(Flux.from(mono));

       var flux = Flux.range(1, 10);
       flux.next().subscribe(Util.subscriber());

       Mono.from(flux).subscribe(Util.subscriber());
    }

    private static Mono<String> getUserName(int userId) {
        return switch(userId) {
            case 1 -> Mono.just("Sam");
            case 2 -> Mono.empty();
            default -> Mono.error(new IllegalArgumentException("Invalid argument"));
        };
    }

    private static void save(Flux<String> flux) {
        flux.subscribe(Util.subscriber());
    }
}
