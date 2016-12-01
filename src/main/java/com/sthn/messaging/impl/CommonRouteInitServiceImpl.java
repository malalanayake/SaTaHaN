package com.sthn.messaging.impl;

import com.sthn.messaging.IRouteInitService;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonRouteInitServiceImpl implements IRouteInitService {

    @Autowired
    ProducerTemplate template;
    @Autowired
    Exchange exchange;
    @Autowired
    Message message;

    @Override
    public Exchange enter(String routeName, Object data) {
        message.setBody(data);
        exchange.setIn(message);
        Exchange exAfter = template.send(routeName, exchange);
        return exAfter;
    }
}
