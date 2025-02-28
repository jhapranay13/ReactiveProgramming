package com.rp.sec03Flux;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;

public class FluxDefer09 {
    private static Logger log = LoggerFactory.getLogger(FluxDefer09.class);

    public static void main(String args[]) throws InterruptedException {
        // used supplier to defer
        Flux.defer(() -> Flux.fromIterable(List.of(1, 2, 3, 4, 5))).subscribe(Util.subscriber());
    }
}
