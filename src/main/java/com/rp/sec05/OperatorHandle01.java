package com.rp.sec05;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class OperatorHandle01 {
    private static Logger log = LoggerFactory.getLogger(OperatorHandle01.class);

    // it's kind of filter and map
    public static void main(String args[]) throws InterruptedException {
        Flux<Integer> flux = Flux.range(1,10);
        Flux<Integer> flux1 = flux.handle((integer, integerSynchronousSink) -> {
            integerSynchronousSink.error(new RuntimeException("oops"));
        });
        flux1.subscribe(Util.subscriber());

        flux.handle((integer, integerSynchronousSink) -> {
            switch (integer) {
                case 1 -> integerSynchronousSink.next(-2);
                case 4 -> {}
                case 7 -> integerSynchronousSink.error(new RuntimeException("oops"));
                default -> integerSynchronousSink.next(integer);
            }
        }).subscribe(Util.subscriber());
    }
}
