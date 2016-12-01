package com.sthn.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RouteConfig extends RouteBuilder {

    public static final String DIRECT_PUBLISH = "direct:publish";
    public static final String DIRECT_STATUS = "direct:status";

    public static final String ROUTE_NAME_PUBLISH = "publish-route";
    public static final String ROUTE_NAME_STATUS = "status-route";

    @Override
    public void configure() throws Exception {
        from(DIRECT_PUBLISH).to("publishMessageExtractor").routeId(ROUTE_NAME_PUBLISH);
        from(DIRECT_STATUS).to("statusMessageExtractor").routeId(ROUTE_NAME_STATUS);
    }
}
