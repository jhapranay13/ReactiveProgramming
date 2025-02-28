package com.rp.sec12Sinks.assingment;

public record SlackMessage(String sender, String message) {

    private static final String MESSAGE_FORMAT = "%s says {%s} -> %s";

    public String formatMessage(String reciever) {
        return String.format(MESSAGE_FORMAT, sender, message, reciever);
    }
}
