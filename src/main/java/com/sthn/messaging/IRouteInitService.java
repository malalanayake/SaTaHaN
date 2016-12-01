package com.sthn.messaging;

import org.apache.camel.Exchange;

public interface IRouteInitService {

    Exchange enter(String routeName, Object data);
}
