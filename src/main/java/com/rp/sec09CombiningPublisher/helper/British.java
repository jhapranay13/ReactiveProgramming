package com.rp.sec09CombiningPublisher.helper;

import com.rp.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class British {
    private static final String AIRLINE = "British Aairways";

    public static Flux<Flight> getFlights() {
        return Flux.range(1, 4)
                .delayElements(Duration.ofMillis(Util.getFaker().random().nextInt(1, 1000)))
                .map(elem -> new Flight(AIRLINE, Util.getFaker().random().nextInt(300, 1000)))
                .transform(Util.fluxLogger(AIRLINE));
    }
}
