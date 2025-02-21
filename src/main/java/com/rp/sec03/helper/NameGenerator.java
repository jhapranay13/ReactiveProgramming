package com.rp.sec03.helper;

import com.rp.common.Util;
import com.rp.sec03.FluxFromIterableOrArray02;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.IntStream;

public class NameGenerator {
    private static Logger log = LoggerFactory.getLogger(NameGenerator.class);

    public static List<String> getNameList(int count) {
        return IntStream.rangeClosed(1, count).mapToObj( i -> generateName()).toList();
    }

    public static Flux<String> getNameFlux(int count) {
        return Flux.range(1, count).map(i -> generateName());
    }

    private static String generateName() {
        try {
            Util.sleepSeconds(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Util.getFaker().name().firstName();
    }
}
