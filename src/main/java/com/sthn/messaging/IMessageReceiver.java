package com.sthn.messaging;

import com.sthn.model.Waki;

public interface IMessageReceiver {

    void receive(Waki message);
}
