package com.rp.sec09CombiningPublisher.helper;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class Kayak {
    public static Flux<Flight> getFlights() {
        return Flux.merge(British.getFlights(), Emirates.getFlights(), Qatar.getFlights())
                .take(Duration.ofSeconds(2));
    }
}
