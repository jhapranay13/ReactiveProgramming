package com.rp.sec02Mono;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class MonoFromCallable05 {
    private static Logger log = LoggerFactory.getLogger(MonoFromCallable05.class);

    public static void main(String args[]) {
        var list = List.of(1, 2, 3);
        // Supplier does not have exception. Callable has Exception
        Mono.fromCallable(() -> sum(list)).subscribe(Util.subscriber());
    }

    private static int sum(List<Integer> list) throws Exception {
        log.info("finding sum of {}", list);
        return list.stream().mapToInt(a -> a).sum();
    }
}
