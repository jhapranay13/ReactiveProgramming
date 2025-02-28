package com.rp.sec02Mono;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class MonoFromRunnable06 {
    private static Logger log = LoggerFactory.getLogger(MonoFromRunnable06.class);

    public static void main(String args[]) {
        // Deals with Empty
        getProductName(1).subscribe(Util.subscriber());
        getProductName(3).subscribe(Util.subscriber());

    }

    private static Mono<String> getProductName(int productId) {
        log.info("getProductName {}", productId);

        if (productId == 1) {
            return Mono.fromSupplier(() -> Util.getFaker().commerce().productName());
        }
        // Instead of returning empty
        return Mono.fromRunnable(() -> notify(productId));
    }

    private static void notify(int productId) {
        log.info("Notify {}", productId);
    }
}
