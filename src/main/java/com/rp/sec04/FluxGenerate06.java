package com.rp.sec04;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class FluxGenerate06 {
    private static Logger log = LoggerFactory.getLogger(FluxGenerate06.class);

    /*
    Stop when one value is invoked.
    stop when cancel, error or complete is envoked
     */
    public static void main(String args[]) throws InterruptedException {
        // synchronousSink is supposed to emit only one value
        Flux.generate(synchronousSink -> {
            synchronousSink.next(1);
            synchronousSink.complete(); // if not completed it will keep on generating
        }).subscribe(Util.subscriber());

        Flux.generate(synchronousSink -> {
            synchronousSink.next(1);
        }).log("take").take(3).log("subscribe").subscribe(Util.subscriber());
    }
}
