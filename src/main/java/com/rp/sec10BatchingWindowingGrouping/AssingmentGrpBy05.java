package com.rp.sec10BatchingWindowingGrouping;

import com.rp.common.Util;
import com.rp.sec10BatchingWindowingGrouping.groupby.OrderProcessingService;
import com.rp.sec10BatchingWindowingGrouping.groupby.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class AssingmentGrpBy05 {
    private static Logger log = LoggerFactory.getLogger(AssingmentGrpBy05.class);

    /*

     */
    public static void main(String args[]) throws InterruptedException {
        orderStream().filter(OrderProcessingService.canProcess())
                        .groupBy(PurchaseOrder::category)
                        .flatMap(gf -> gf.transform(OrderProcessingService.getProcessor(gf.key())))
                                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }

    private static Flux<PurchaseOrder> orderStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(elem -> PurchaseOrder.create());
    }
}
