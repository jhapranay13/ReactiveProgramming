package com.rp.sec02;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class MonoFromSupplier04 {
    private static Logger log = LoggerFactory.getLogger(MonoFromSupplier04.class);

    public static void main(String args[]) {
        var list = List.of(1, 2, 3);
        Mono.just(sum(list)).subscribe(Util.subscriber());
        // Operation is happening but is not subscribe. So if you want to delay the execution
        Mono.just(sum(list));
        // and only execute if it is subscribed to.
        Mono.fromSupplier(() -> sum(list));
        Mono.fromSupplier(() -> sum(list)).subscribe(Util.subscriber());
    }

    private static int sum(List<Integer> list){
        log.info("finding sum of {}", list);
        return list.stream().mapToInt(a -> a).sum();
    }
}
