package com.rp.sec09CombiningPublisher;

import com.rp.common.Util;
import com.rp.sec09CombiningPublisher.applications.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class Assingment12 {
    private static Logger log = LoggerFactory.getLogger(Assingment12.class);

    /*
        Get all user and build 1 object UserInformation
     */
    private record UserInformation(Integer userId, String userName, Integer balance, List<Order> list) {};

    public static void main(String args[]) throws InterruptedException {
        UserService.getAllUser()
                .flatMap(user -> getUserInformation(user))
                .subscribe(Util.subscriber());
        Util.sleepSeconds(10);
    }

    private static Mono<UserInformation> getUserInformation(User user) {
        return Mono.zip(
                PaymentService.getUserBalance(user.userId()),
                OrderService.getUserOrder(user.userId()).collectList()
        ).
                map(elem -> new UserInformation(user.userId(), user.name(), elem.getT1(), elem.getT2()));
    }
}
