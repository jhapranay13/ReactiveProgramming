package com.rp.sec04;

import com.rp.common.Util;
import com.rp.sec04.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class FluxSinkThreadSafety03 {
    private static Logger log = LoggerFactory.getLogger(FluxSinkThreadSafety03.class);

    public static void main(String args[]) throws InterruptedException {
        // Thread Unsafe
        demo1();
        // Thread safe
        demo2();
    }

    private static void demo1() {
        List<Integer> list = new ArrayList<>();

        Runnable runnable = () -> {

            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable);
        }
        try {
            Util.sleepSeconds(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("list size {}", list.size());
    }

    private static void demo2() {
        List<String> list = new ArrayList<>();
        var generator = new NameGenerator();
        var flux = Flux.create(generator);
        flux.subscribe((i) -> list.add(i));

        Runnable runnable = () -> {

            for (int i = 0; i < 1000; i++) {
               generator.generate();
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable);
        }
        try {
            Util.sleepSeconds(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("list size {}", list.size());
    }
}
