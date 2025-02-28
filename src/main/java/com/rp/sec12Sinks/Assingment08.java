package com.rp.sec12Sinks;

import com.rp.common.Util;
import com.rp.sec12Sinks.assingment.SlackMemeber;
import com.rp.sec12Sinks.assingment.SlackRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*

Slack Room
The person joining Late should still see all the prev message
 */
public class Assingment08 {
    private static Logger log = LoggerFactory.getLogger(Assingment08 .class);

    public static void main(String args[]) throws InterruptedException {
        var room = new SlackRoom("New Room");
        var sam = new SlackMemeber("sam");
        var jake = new SlackMemeber("jake");
        var mike = new SlackMemeber("mike");

        room.addMemeber(sam);
        room.addMemeber(jake);

        sam.say("Hi all");
        Util.sleepSeconds(3);
        jake.say("Hello");
        sam.say("How are you");
        Util.sleepSeconds(4);
        room.addMemeber(mike);
        mike.say("Hello everyone");
        Util.sleepSeconds(4);
    }

}
