package com.sthn.messaging.impl;

import com.sthn.messaging.IMessageReceiver;
import com.sthn.model.Waki;
import com.sthn.service.ILogMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogPublishMessageReceiver implements IMessageReceiver {
    @Autowired
    ILogMessageService iLogMessageService;

    @Override
    public void receive(Waki message) {
        iLogMessageService.publish(message);
    }
}
