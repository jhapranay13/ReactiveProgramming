package com.rp.sec04;

import com.rp.common.Util;
import com.rp.sec04.helper.FileOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class FileReaderService10 {
    private static Logger log = LoggerFactory.getLogger(FileReaderService10.class);

    public static void main(String args[]) throws InterruptedException {
        FileOperation fileOperation = new FileOperation();
        fileOperation.create("FluxFile.txt", "There are two line \n This is the other one").subscribe(Util.subscriber());
        fileOperation.read("FluxFile.txt").subscribe(Util.subscriber());
        fileOperation.delete("FluxFile.txt").subscribe(Util.subscriber());
    }
}
