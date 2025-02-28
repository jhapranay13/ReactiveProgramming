package com.rp.sec02Mono;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class MonoEmptyError03 {
    private static Logger log = LoggerFactory.getLogger(MonoEmptyError03.class);

    public static void main(String args[]) {
        getUserName(1).subscribe(Util.subscriber());
        getUserName(2).subscribe(Util.subscriber());
        getUserName(3).subscribe(Util.subscriber());
    }

    private static Mono<String> getUserName(int userId) {
        return switch(userId) {
            case 1 -> Mono.just("Sam");
            case 2 -> Mono.empty();
            default -> Mono.error(new IllegalArgumentException("Invalid argument"));
        };
    }
}
