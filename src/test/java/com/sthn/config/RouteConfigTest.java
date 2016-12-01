package com.sthn.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RouteConfigTest {

    @Test
    public void camelRoutesShouldBe() {
        assertEquals(RouteConfig.DIRECT_PUBLISH, "direct:publish");
        assertEquals(RouteConfig.DIRECT_STATUS, "direct:status");
        assertEquals(RouteConfig.ROUTE_NAME_PUBLISH, "publish-route");
        assertEquals(RouteConfig.ROUTE_NAME_STATUS, "status-route");
    }
}
