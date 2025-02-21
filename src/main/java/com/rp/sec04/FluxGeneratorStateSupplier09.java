package com.rp.sec04;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class FluxGeneratorStateSupplier09 {
    private static Logger log = LoggerFactory.getLogger(FluxGeneratorStateSupplier09.class);

    /*
    Stop when one value is invoked.
    stop when cancel, error or complete is envoked
     */
    public static void main(String args[]) throws InterruptedException {

        Flux.generate(
                () -> 0,  // Initial Value
                (counter, sink) -> {
                    var country = Util.getFaker().country().name();
                    sink.next(country);
                    counter++;

                    if (counter == 10 || country.equalsIgnoreCase("canada")) {
                        sink.complete();
                    }
                    return counter;
                }
        ).subscribe(Util.subscriber());
    }
}
