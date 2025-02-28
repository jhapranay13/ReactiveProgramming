package com.rp.sec04FluxEmittingItemProgramatically.helper;

import com.rp.common.Util;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class NameGenerator implements Consumer<FluxSink<String>> {
    FluxSink<String> sink;

    @Override
    public void accept(FluxSink<String> stringFluxSink) {
        this.sink = stringFluxSink;
    }

    public void generate() {
        sink.next(Util.getFaker().name().firstName());
    }
}
