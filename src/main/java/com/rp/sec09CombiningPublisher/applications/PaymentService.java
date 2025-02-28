package com.rp.sec09CombiningPublisher.applications;

import reactor.core.publisher.Mono;

import java.util.Map;

public class PaymentService {

    private static final Map<Integer, Integer> userBalaneTable = Map.of(
            1, 100,
            2, 200,
            3, 300
    );

    public static Mono<Integer> getUserBalance(Integer userId) {
        return Mono.fromSupplier(() -> userBalaneTable.get(userId));
    }
}
