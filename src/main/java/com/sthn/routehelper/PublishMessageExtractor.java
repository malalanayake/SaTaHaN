package com.sthn.routehelper;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component(value = "publishMessageExtractor")
public class PublishMessageExtractor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("PUBLISH:" + exchange.getIn().getBody().toString());
    }
}
