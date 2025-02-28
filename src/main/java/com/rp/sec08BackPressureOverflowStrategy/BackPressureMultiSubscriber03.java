package com.rp.sec08BackPressureOverflowStrategy;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class BackPressureMultiSubscriber03 {
    private static Logger log = LoggerFactory.getLogger(BackPressureMultiSubscriber03.class);

    public static void main(String args[]) throws InterruptedException {
        //System.setProperty("reactor.bufferSize.small", "16");  // to control the buffer size
        var producer = Flux.generate(
                        () -> 1,
                        (state, sink) -> {
                            log.info("Generating {}", state);
                            sink.next(state);
                            return ++state;
                        }
                ).cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        producer.limitRate(5)  // Tell the producer to tell the producer to generate this much data only
                .publishOn(Schedulers.boundedElastic())
                .map((item) -> timeConsuminOp(item))
                .subscribe(Util.subscriber("sub1"));

        producer.take(100)  // Tell the producer to tell the producer to generate this much data only
                .publishOn(Schedulers.boundedElastic())
                //.map((item) -> timeConsuminOp(item))
                .subscribe(Util.subscriber("sub2"));

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
