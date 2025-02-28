package com.rp.sec05DoHooksCallbackTransform;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class TimeOut07 {
    private static Logger log = LoggerFactory.getLogger(TimeOut07.class);

    /*
    in case of multiple timeouts the closest one to the subscriber will take effect
     */
    public static void main(String args[]) throws InterruptedException {
        getProductName().timeout(Duration.ofSeconds(1)).onErrorReturn("fallback").subscribe(Util.subscriber());
        Util.sleepSeconds(3);

        getProductName().timeout(Duration.ofSeconds(1), getProductName1()).onErrorReturn("fallback").subscribe(Util.subscriber());
        Util.sleepSeconds(3);
    }


    private static Mono<String> getProductName() {
        return Mono.fromSupplier(() -> "Service >> " +Util.getFaker().commerce().productName()).delayElement(Duration.ofMillis(1100));
    }

    private static Mono<String> getProductName1() {
        return Mono.fromSupplier(() -> "Fallback >> " +Util.getFaker().commerce().productName()).delayElement(Duration.ofMillis(100));
    }
}
