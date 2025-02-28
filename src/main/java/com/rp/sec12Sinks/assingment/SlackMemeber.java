package com.rp.sec12Sinks.assingment;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class SlackMemeber {
    private static Logger log = LoggerFactory.getLogger(SlackMemeber.class);
    private final String name;
    private Consumer<String> messageConsumer;

    public SlackMemeber(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMessageConsumer(Consumer<String> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public void say(String message) {
        messageConsumer.accept(name + " says: " + message);
    }

    public static void recieve(String message) {
        log.info( " received: " + message);
    }
}
