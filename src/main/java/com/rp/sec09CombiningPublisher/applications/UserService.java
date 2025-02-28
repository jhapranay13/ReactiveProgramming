package com.rp.sec09CombiningPublisher.applications;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Imagine User service as an application has 2 endpoint
 * This is a client class which represents to call those 2 endpoints (10 requests)
 *
 */

public class UserService {

    private static final Map<String, Integer> userTable = Map.of(
            "sam", 1,
            "mike", 2,
            "jake", 3
    );

    public static Flux<User> getAllUser() {
        return Flux.fromIterable(userTable.entrySet())
                .map(elem -> new User(elem.getValue(), elem.getKey()));
    }

    public static Mono<Integer> getUserId(String userName) {
        return Mono.fromSupplier(() -> userTable.get(userName));
    }
}
