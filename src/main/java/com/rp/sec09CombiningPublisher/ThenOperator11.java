package com.rp.sec09CombiningPublisher;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class ThenOperator11 {
    private static Logger log = LoggerFactory.getLogger(ThenOperator11.class);

    /*
        Then could be helpful when we are not interested in the result of publisher
        we need to have sequntial execution of asyn task
     */
    public static void main(String args[]) throws InterruptedException {
        var list = List.of("a", "b", "c");
        saveRecords(list)
                .then(sendNotification(list))
                .subscribe(Util.subscriber());
        Util.sleepSeconds(15);

    }

    private static Flux<String> saveRecords(List<String> list) {
        return Flux.fromIterable(list)
                .map(elem -> "Saved >> " + elem)
                .delayElements(Duration.ofMillis(500));
    }

    private static Mono<Void> sendNotification(List<String> list) {
        return Mono.fromRunnable(() -> log.info("All these {} records save successfully", list));
    }
}
