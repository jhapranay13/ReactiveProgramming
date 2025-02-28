package com.rp.sec03Flux;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class FluxJust01 {
    private static Logger log = LoggerFactory.getLogger(FluxJust01.class);

    public static void main(String args[]) throws InterruptedException {
        Flux.just(1, 2, 3, "Sam").subscribe(Util.subscriber());

        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6, 7);
        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));
        flux.filter(i -> i % 2 == 0).subscribe(Util.subscriber("sub3"));
    }
}
