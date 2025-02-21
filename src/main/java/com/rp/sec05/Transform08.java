package com.rp.sec05;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Transform08 {

    private static Logger log = LoggerFactory.getLogger(TimeOut07.class);

    record Customer(int id, String name){}
    record Product(String name, int price, int qty){}

    public static void main(String args[]) throws InterruptedException {
       /* getCustomer()
                .doOnNext(i -> log.info("Recieved >> {}", i))
                .doOnComplete(() -> log.info("Complete >>>"))
                .doOnError((err) -> log.error("Error", err))
                .subscribe();

        getProduct()
                .doOnNext(i -> log.info("Recieved >> {}", i))
                .doOnComplete(() -> log.info("Complete >>>"))
                .doOnError((err) -> log.error("Error", err))
                .subscribe();*/

        var isEnabled = true;
        getCustomer()
                .transform(isEnabled ? getFlux() : Function.identity())  // it means do whatever you were doing
                .subscribe();

        getProduct()
                .transform(getFlux())
                .subscribe();
    }

    private static Flux<Customer> getCustomer() {
        return Flux.range(1, 3).
                map(i -> new Customer(i, Util.getFaker().name().firstName()));
    }

    private static Flux<Product> getProduct() {
        return Flux.range(1, 3).
                map(i -> new Product(Util.getFaker().commerce().productName(), i * 10, i));
    }

    private static <T> UnaryOperator<Flux<T>> getFlux() {
        return flux -> flux
                .doOnNext(i -> log.info("Recieved >> {}", i))
                .doOnComplete(() -> log.info("Complete >>>"))
                .doOnError((err) -> log.error("Error", err));
    }
}
