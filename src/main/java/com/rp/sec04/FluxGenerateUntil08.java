package com.rp.sec04;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class FluxGenerateUntil08 {
    private static Logger log = LoggerFactory.getLogger(FluxGenerate06.class);

    /*
    Stop when one value is invoked.
    stop when cancel, error or complete is envoked
     */
    public static void main(String args[]) throws InterruptedException {

        demo1();
        demo2();
    }

    private static void demo1() {
        // The method will be executed again and again so variables that are used across execution such as counter must be declared outside
        Flux.generate(synchronousSink -> {
            var country = Util.getFaker().country().name();
            synchronousSink.next(country);

            if (country.equalsIgnoreCase("canada")) {
                synchronousSink.complete();
            }
        }).subscribe(Util.subscriber());
    }

    private static void demo2() {
        // The method will be executed again and again so variables that are used across execution such as counter must be declared outside
        Flux.<String>generate(synchronousSink -> {
            var country = Util.getFaker().country().name();
            synchronousSink.next(country);

        }).takeUntil((str) -> str.equalsIgnoreCase("canada")).subscribe(Util.subscriber());
    }
}
