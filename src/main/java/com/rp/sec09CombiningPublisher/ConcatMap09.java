package com.rp.sec09CombiningPublisher;

import com.rp.common.Util;
import com.rp.sec09CombiningPublisher.applications.User;
import com.rp.sec09CombiningPublisher.applications.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class ConcatMap09 {
    private static Logger log = LoggerFactory.getLogger(ConcatMap09.class);

    // Concat map finshes one after the other unlike flatmap. It is sequential
    public static void main(String args[]) throws InterruptedException {
        UserService.getAllUser()
            .concatMap(user -> downstreamProcess(user)).subscribe(Util.subscriber());

        Util.sleepSeconds(3);

    }

    public static Mono<User> downstreamProcess(User user) {
        return Mono.just(user)
                .map((usr) -> {
                    log.info("Downstream Process");
                    return usr;
                }).delayElement(Duration.ofMillis(300));
    }
}
