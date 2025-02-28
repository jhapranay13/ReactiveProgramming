package com.rp.sec03Flux;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class FluxRange04 {
    private static Logger log = LoggerFactory.getLogger(FluxRange04.class);

    public static void main(String args[]) throws InterruptedException {
        // Satrts from and numbers after the start
        Flux.range(3, 10).subscribe(Util.subscriber());
        Flux.range(1, 10).map(i -> Util.getFaker().name().firstName()).subscribe(Util.subscriber());
    }
}
