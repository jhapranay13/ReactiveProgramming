package com.rp.sec03Flux;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class FluxLog05 {
    private static Logger log = LoggerFactory.getLogger(FluxLog05.class);

    public static void main(String args[]) throws InterruptedException {
        Flux.range(3, 10)
                .log("start")
                .map(i -> Util.getFaker().name().firstName())
                .log("After-map")
                .subscribe(Util.subscriber());
    }
}
