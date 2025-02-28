package com.rp.sec03Flux.helper;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class FluxEmptyError08 {
    private static Logger log = LoggerFactory.getLogger(FluxEmptyError08.class);

    public static void main(String args[]) throws InterruptedException {
        // Similar to mono
        Flux.empty().subscribe(Util.subscriber());
        Flux.error(new RuntimeException("Some error")).subscribe(Util.subscriber());
    }
}
