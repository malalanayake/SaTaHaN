package com.sthn.messaging;

import com.sthn.model.Waki;

public interface IMessageHandler extends Runnable {

    boolean handOver(Waki message);

    void subscribe(IMessageReceiver messageReceiver);

    void unsubscribe(IMessageReceiver messageReceiver);

    int receiverCount();
}
