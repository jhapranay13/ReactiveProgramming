package com.rp.sec09CombiningPublisher.helper;

import com.rp.common.Util;
import com.rp.sec09CombiningPublisher.StartWith01;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NameGenerator  {
    private static Logger log = LoggerFactory.getLogger(NameGenerator.class);
    List<String> cache = new ArrayList<>();

    public Flux<String> generate() {

        return Flux.generate(sink -> {
            log.info("Generating Name");

            try {
                Util.sleepSeconds(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            var name = Util.getFaker().name().firstName();
            cache.add(name);
            sink.next(name);
        }).startWith(cache).cast(String.class);
    }
}
