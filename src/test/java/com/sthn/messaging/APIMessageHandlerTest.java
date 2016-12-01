package com.sthn.messaging;

import com.sthn.messaging.impl.APIMessageHandler;
import com.sthn.model.LogMessage;
import com.sthn.model.Waki;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class APIMessageHandlerTest {
    Logger log = Logger.getLogger(this.getClass());
    IMessageHandler apiMessageHandler;
    private Waki wakiReceivedOne = null;
    private Waki wakiReceivedTwo = null;
    private Waki wakiReceivedThree = null;

    @Before
    public void init() {
        apiMessageHandler = new APIMessageHandler();

        apiMessageHandler.subscribe((Waki message) -> {
            log.debug("Hit the receiver ONE on APIMessageHandlerTest");
            wakiReceivedOne = message;
        });

        apiMessageHandler.subscribe((Waki message) -> {
            log.debug("Hit the receiver TWO on APIMessageHandlerTest");
            wakiReceivedTwo = message;
        });

        apiMessageHandler.subscribe((Waki message) -> {
            log.debug("Hit the receiver THREE on APIMessageHandlerTest");
            wakiReceivedThree = message;
        });
    }

    @Test
    public void wakiMessageShouldPassToTheChain() throws Exception {
        Waki waki = new Waki();
        LogMessage logMessage = new LogMessage(1l, "Test API Message");
        waki.setLogMessage(logMessage);

        assertEquals(null, wakiReceivedOne);
        assertEquals(null, wakiReceivedTwo);
        assertEquals(null, wakiReceivedThree);
        apiMessageHandler.handOver(waki);
        //Need to adjust the sleep if you have heavy load on receivers
        Thread.sleep(100);
        assertEquals(waki, wakiReceivedOne);
        assertEquals(waki, wakiReceivedTwo);
        assertEquals(waki, wakiReceivedThree);
    }


    @Test
    public void subscribeShouldIncreaseTheReceiverCount() throws Exception {
        assertEquals(3, apiMessageHandler.receiverCount());
        apiMessageHandler.subscribe((Waki message) -> {
            log.debug("Hit the receiver Fourth on APIMessageHandlerTest");
        });
        assertEquals(4, apiMessageHandler.receiverCount());
    }

    @Test
    public void unSubscribeShouldDecreaseTheReceiverCount() {
        assertEquals(3, apiMessageHandler.receiverCount());
        IMessageReceiver messageReceiver = new IMessageReceiver() {
            @Override
            public void receive(Waki message) {
                log.debug("Hit the receiver Fourth on APIMessageHandlerTest");
            }
        };
        apiMessageHandler.subscribe(messageReceiver);
        assertEquals(4, apiMessageHandler.receiverCount());
        apiMessageHandler.unsubscribe(messageReceiver);
        assertEquals(3, apiMessageHandler.receiverCount());
    }

}
