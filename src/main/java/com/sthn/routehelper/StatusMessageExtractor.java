package com.sthn.routehelper;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component(value = "statusMessageExtractor")
public class StatusMessageExtractor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("STATUS:" + exchange.getIn().getBody().toString());
    }
}
