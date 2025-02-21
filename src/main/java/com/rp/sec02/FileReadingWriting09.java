package com.rp.sec02;

import com.rp.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class FileReadingWriting09 {
    private static Logger log = LoggerFactory.getLogger(FileReadingWriting09.class);

    public static void main(String args[]) throws InterruptedException {
        FileOperation fileOper = new FileOperation();
        fileOper.write("exp.txt", "Some Text").subscribe(Util.subscriber());

        fileOper.read("exp.txt").subscribe(Util.subscriber());

        fileOper.delete("exp.txt").subscribe(Util.subscriber());
    }
}
