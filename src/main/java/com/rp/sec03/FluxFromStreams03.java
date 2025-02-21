package com.rp.sec03;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;

public class FluxFromStreams03 {
    private static Logger log = LoggerFactory.getLogger(FluxFromStreams03.class);

    public static void main(String args[]) throws InterruptedException {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8);

        // Stram can be executed only once so we have to use supplier for multiple subscriber
        Flux<Integer> flux = Flux.fromStream(() -> list.stream());
        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));
        flux.filter(i -> i % 2 == 0).subscribe(Util.subscriber("sub3"));
    }
}
