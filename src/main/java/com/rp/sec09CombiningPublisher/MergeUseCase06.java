package com.rp.sec09CombiningPublisher;

import com.rp.common.Util;
import com.rp.sec09CombiningPublisher.helper.Kayak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

// It's like kayak merging results and showing best flights
public class MergeUseCase06 {
    private static Logger log = LoggerFactory.getLogger(MergeUseCase06.class);

    public static void main(String args[]) throws InterruptedException {

        Kayak.getFlights().subscribe(Util.subscriber());

        Util.sleepSeconds(16);
    }



}
