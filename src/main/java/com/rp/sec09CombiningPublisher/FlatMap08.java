package com.rp.sec09CombiningPublisher;

import com.rp.common.Util;
import com.rp.sec09CombiningPublisher.applications.OrderService;
import com.rp.sec09CombiningPublisher.applications.PaymentService;
import com.rp.sec09CombiningPublisher.applications.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sequential non blocking IO calls
 * flatmap is used to flatten the inner publisher/ to subscribe to the inner publisher
 *
 */
public class FlatMap08 {
    private static Logger log = LoggerFactory.getLogger(FlatMap08.class);

    public static void main(String args[]) throws InterruptedException {
        /* We will get Mono of Mono if we do this
        UserService.getUserId("sam")
                .map(userid -> PaymentService.getUserBalance(userid)).subscribe(Util.subscriber());

         */
        UserService.getUserId("sam")
                .flatMap(userid -> PaymentService.getUserBalance(userid)).subscribe(Util.subscriber("sub1"));

        /*

        This will return Mono<Flux<Order>> so for these we use flatMapMany
        UserService.getUserId("sam")
                .map(userid -> OrderService.getUserOrder(userid)).subscribe(Util.subscriber());

         */
        UserService.getUserId("sam")
                .flatMapMany(userid -> OrderService.getUserOrder(userid))
                .subscribe(Util.subscriber("sub2"));

        UserService.getAllUser()
                .map(userFlux -> userFlux.userId())
                .flatMap(userId -> OrderService.getUserOrder(userId), 1)// 1 is concurrency
                .subscribe(Util.subscriber("sub3"));

        Util.sleepSeconds(13);

    }
}
