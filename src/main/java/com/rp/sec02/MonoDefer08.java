package com.rp.sec02;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MonoDefer08 {
    private static Logger log = LoggerFactory.getLogger(MonoDefer08.class);

    public static void main(String args[]) throws InterruptedException {
       getPublisher();

       Mono.defer(() -> {
           try {
               return getPublisher();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           return null;
       }).subscribe(Util.subscriber());
    }

    private static Mono<Integer> getPublisher() throws InterruptedException {
        log.info("Creating Publisher");
        var list = List.of(1, 2, 3);
        Util.sleepSeconds(3);
        return Mono.fromSupplier(() -> sum(list));
    }

    private static int sum(List<Integer> list){
        log.info("finding sum of {}", list);
        return list.stream().mapToInt(a -> a).sum();
    }
}
