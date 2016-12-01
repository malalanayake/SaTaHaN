package com.sthn.service;

public interface ILogMessageService {

    boolean publish(Object data);

    boolean status(Object data);
}
