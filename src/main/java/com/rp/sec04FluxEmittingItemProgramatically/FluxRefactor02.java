package com.rp.sec04FluxEmittingItemProgramatically;

import com.rp.common.Util;
import com.rp.sec04FluxEmittingItemProgramatically.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class FluxRefactor02 {
    private static Logger log = LoggerFactory.getLogger(FluxRefactor02.class);

    public static void main(String args[]) throws InterruptedException {
        // accept method of consumer gets called
        var generator = new NameGenerator();
        var flux = Flux.create(generator);
        flux.subscribe(Util.subscriber());

        for (int i = 0; i < 10; i++) {
            generator.generate();
        }
    }
}
