package com.rp.sec03;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;

public class FluxFromIterableOrArray02 {
    private static Logger log = LoggerFactory.getLogger(FluxFromIterableOrArray02.class);

    public static void main(String args[]) throws InterruptedException {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8};

        Flux<Integer> flux = Flux.fromIterable(list);
        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));
        flux.filter(i -> i % 2 == 0).subscribe(Util.subscriber("sub3"));

        Flux<Integer> flux1 = Flux.fromArray(arr);
        flux1.subscribe(Util.subscriber("sub4"));
        flux1.subscribe(Util.subscriber("sub5"));
        flux1.filter(i -> i % 2 == 0).subscribe(Util.subscriber("sub6"));
    }
}
