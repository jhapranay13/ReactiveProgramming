package com.rp.sec06HotAndColdPublisher;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ReplayCache04 {
    private static Logger log = LoggerFactory.getLogger(ReplayCache04.class);

    public static void main(String args[]) throws InterruptedException {
        // replay caches data and if no number mentioned shares all the previous data with new subscriber
        Flux<Integer> stockStream = stockStream().replay(2).autoConnect(0); // Streams even if no subscriber
        Util.sleepSeconds(2);

        stockStream.subscribe(Util.subscriber("sub1"));
        Util.sleepSeconds(3);
        stockStream.subscribe(Util.subscriber("sub3"));

        Util.sleepSeconds(3);
        stockStream.take(2).subscribe(Util.subscriber("sub4"));

        Util.sleepSeconds(12);

    }

    private static Flux<Integer> stockStream() {
        return Flux.generate(
                (sink) -> {
                    sink.next(Util.getFaker().random().nextInt(10, 100));
                }
        ).doOnNext((price) -> log.info("Recieved {}", price))
        .delayElements(Duration.ofMillis(500)).cast(Integer.class);
    }
}
