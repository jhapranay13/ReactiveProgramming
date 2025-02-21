package com.rp.sec05;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class HandleAssingment02 {
    private static Logger log = LoggerFactory.getLogger(HandleAssingment02.class);

    // it's kind of filter and map
    public static void main(String args[]) throws InterruptedException {
        Flux<String> flux = Flux.generate(synchronousSink -> {
            synchronousSink.next(Util.getFaker().country().name());
        });

        flux.handle((str, integerSynchronousSink) -> {
            integerSynchronousSink.next(str);

            if (str.equalsIgnoreCase("canada")) {
                log.info("<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>");
                integerSynchronousSink.complete();
            }
        }).subscribe(Util.subscriber());
    }
}
