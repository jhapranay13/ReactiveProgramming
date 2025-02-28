package com.rp.sec10BatchingWindowingGrouping.groupby;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class OrderProcessingService {

    private static final Map<String, UnaryOperator<Flux<PurchaseOrder>>> PROCESSOR_MAP = Map.of(
            "Kids", kidsProcessing(),
            "Automotive", autoProcessing()
    );
    private static UnaryOperator<Flux<PurchaseOrder>> autoProcessing() {
        return flux -> flux
                .map(po -> new PurchaseOrder(po.item(), po.category(), po.price() + 100));
    }

    private static UnaryOperator<Flux<PurchaseOrder>> kidsProcessing() {
        return flux -> flux
                .flatMap(po -> getFreeKidsOrder(po).flux().startWith(po));
    }

    private static Mono<PurchaseOrder> getFreeKidsOrder(PurchaseOrder order) {
        return Mono.fromSupplier(
                () -> {
                    return new PurchaseOrder(
                            order.item() + "-FREE",
                            order.category(),
                            0
                    );
                }
        );
    }

    public static Predicate<PurchaseOrder> canProcess() {
        return po -> PROCESSOR_MAP.containsKey(po.category());
    }

    public static UnaryOperator<Flux<PurchaseOrder>> getProcessor(String category) {
        return PROCESSOR_MAP.get(category);
    }
}
