package com.rp.sec04FluxEmittingItemProgramatically;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class FluxTakeOperator05 {
    private static Logger log = LoggerFactory.getLogger(FluxTakeOperator05.class);

    public static void main(String args[]) throws InterruptedException {
        takeOp();
        takeWhile();
        takeUntil();
    }

    private static void takeOp() {
        Flux.range(1, 5).log("take").take(3).log("subscribe").subscribe(Util.subscriber());
    }

    private static void takeWhile() {
        Flux.range(1, 5).log("take").takeWhile((i) -> i < 6).log("subscribe").subscribe(Util.subscriber());
    }

    private static void takeUntil() {
        Flux.range(1, 5).log("take").takeUntil((i) -> i < 6).log("subscribe").subscribe(Util.subscriber());
    }
}
