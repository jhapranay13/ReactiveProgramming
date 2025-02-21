package com.rp.sec04;

import com.rp.common.Util;

import com.rp.sec04.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;


public class FluxCreate01 {
    private static Logger log = LoggerFactory.getLogger(FluxCreate01.class);

    public static void main(String args[]) throws InterruptedException {
        // accept method of consumer gets called
        var generator = new NameGenerator();
        Flux.create((fluxSink) -> {
            String country;

            do {
                country = Util.getFaker().country().name();
                fluxSink.next(country);
            } while (!country.equalsIgnoreCase("canada"));
            fluxSink.complete();
        }).subscribe(Util.subscriber());
    }
}
