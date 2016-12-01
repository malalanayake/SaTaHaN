package com.sthn.service.impl;

import com.sthn.config.RouteConfig;
import com.sthn.messaging.IRouteInitService;
import com.sthn.service.ILogMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogMessageServiceImpl implements ILogMessageService {
    @Autowired
    IRouteInitService iRouteInitService;

    @Override
    public boolean publish(Object data) {
        iRouteInitService.enter(RouteConfig.DIRECT_PUBLISH, data);
        return true;
    }

    @Override
    public boolean status(Object data) {
        iRouteInitService.enter(RouteConfig.DIRECT_STATUS, data);
        return false;
    }
}
