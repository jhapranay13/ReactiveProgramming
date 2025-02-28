package com.rp.sec09CombiningPublisher;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 *
 * Similar to Merge. It gets all the parts and give one object.
 * It only works if all producers will give the data. Merge is like any producer gives data or not does not matter
 *
 */
// Mono also has this
public class Zip07 {
    private static Logger log = LoggerFactory.getLogger(Zip07.class);
    private record Car(String body, String engine, String tire){};

    public static void main(String args[]) throws InterruptedException {

        Flux.zip(getBody(), getEngine(), getTire())
                .map(elem -> new Car( elem.getT1(), elem.getT2(), elem.getT3()))
                .subscribe(Util.subscriber());
        Util.sleepSeconds(5);
    }

    private static Flux<String> getBody() {
        return Flux.range(1, 5)
                .map(elem -> "Body >> " + elem)
                .delayElements(Duration.ofMillis(100));
    }

    private static Flux<String> getEngine() {
        return Flux.range(1, 3)
                .map(elem -> "Engine >> " + elem)
                .delayElements(Duration.ofMillis(200));
    }

    private static Flux<String> getTire() {
        return Flux.range(1, 10)
                .map(elem -> "Tire >> " + elem)
                .delayElements(Duration.ofMillis(75));
    }
}
