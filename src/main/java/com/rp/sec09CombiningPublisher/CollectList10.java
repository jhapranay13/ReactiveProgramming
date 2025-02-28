package com.rp.sec09CombiningPublisher;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public  class CollectList10 {
    private static Logger log = LoggerFactory.getLogger(CollectList10.class);

    // Concat map finshes one after the other unlike flatmap. It is sequential
    public static void main(String args[]) throws InterruptedException {
        Flux.range(1, 10)
                .collectList() // without this it will be like Recieved 1 Recieved 2 etc
                .subscribe(Util.subscriber());

    }
}