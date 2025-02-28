package com.rp.sec08BackPressureOverflowStrategy;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class BackPressureHandling01 {
    private static Logger log = LoggerFactory.getLogger(BackPressureHandling01.class);

    /**
     *
     * Producer produces 256 and waits for consumption and after 75% consumption starts again
     */
    public static void main(String args[]) throws InterruptedException {
        System.setProperty("reactor.bufferSize.small", "16");  // to control the buffer size
        var producer = Flux.generate(
                () -> 1,
                (state, sink) -> {
                    log.info("Generating {}", state);
                    sink.next(state);
                    return ++state;
                }
        ).cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        producer.publishOn(Schedulers.boundedElastic())
                .map((item) -> timeConsuminOp(item))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }

    private static int timeConsuminOp(int i) {
        try {
            Util.sleepSeconds(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }
}
