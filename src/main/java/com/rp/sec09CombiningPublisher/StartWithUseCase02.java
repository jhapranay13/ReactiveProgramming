package com.rp.sec09CombiningPublisher;

import com.rp.common.Util;
import com.rp.sec09CombiningPublisher.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartWithUseCase02 {
    private static Logger log = LoggerFactory.getLogger(StartWithUseCase02.class);
    // Order is bottom to top or upstream
    public static void main(String args[]) throws InterruptedException {
        NameGenerator nameGenerator = new NameGenerator();
        nameGenerator.generate().take(2).subscribe(Util.subscriber("sam"));
        nameGenerator.generate().take(2).subscribe(Util.subscriber("mike"));
        nameGenerator.generate().take(3).subscribe(Util.subscriber("Jane"));

        Util.sleepSeconds(10);
    }


}
