package com.rp.sec07ThreadingAndScheduler;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ParallelExecution06 {
    private static Logger log = LoggerFactory.getLogger(ParallelExecution06.class);

    public static void main(String args[]) throws InterruptedException {

        Flux.range(1, 10)
                .parallel(3) // If no number then the number of CPU
                .runOn(Schedulers.parallel())
                .map((i) -> timeConsuminOp(i))
                .sequential()
                .subscribe(Util.subscriber());

        Util.sleepSeconds(12);
    }

    private static int timeConsuminOp(int i) {
        log.info("Time consuming op {}", i);

        try {
            Util.sleepSeconds(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i * 2;
    }
}
