package com.github.chaudhryfaisal.wsk.dto;

public interface Application {
    void init(Payload payload);

    void postHandle();

    Response handle(Payload payload);
}
