package com.rp.sec08BackPressureOverflowStrategy;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class FluxCreate04 {
    private static Logger log = LoggerFactory.getLogger(FluxCreate04.class);

    public static void main(String args[]) throws InterruptedException {
        System.setProperty("reactor.bufferSize.small", "16");  // to control the buffer size
        var producer = Flux.create(
                        (sink) -> {

                            for (int i = 1; i <= 500 && !sink.isCancelled(); i++) {
                                log.info("generating {}", i);
                                sink.next(i);
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            sink.complete();
                        }
                ).cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        producer//.limitRate(1)  //will worsen the problem
                .publishOn(Schedulers.boundedElastic())
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
