package com.rp.sec12Sinks.assingment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class SlackRoom {
    private static Logger log = LoggerFactory.getLogger(SlackRoom.class);
    private String name;
    private Sinks.Many<SlackMessage> sink;
    private final Flux<SlackMessage> flux;

    public SlackRoom(String name) {
        this.name = name;
        this.sink = Sinks.many().replay().all();
        this.flux = sink.asFlux();
    }

    public void addMemeber(SlackMemeber member) {
        log.info("{} joined the room {}", member.getName(), this.name);
        this.subscribeToRoomMessages(member);
        member.setMessageConsumer(message -> postMessage(member.getName(), message));
    }

    public void subscribeToRoomMessages(SlackMemeber member) {
        flux
                .filter(sm -> !sm.sender().equals(member.getName()))
                .map(sm -> sm.formatMessage(member.getName()))
                .subscribe(SlackMemeber::recieve);
    }

    private void postMessage(String sender, String message) {
        this.sink.tryEmitNext(new SlackMessage(sender, message));
    }
}
