package com.github.chaudhryfaisal.wsk.util;

import com.github.chaudhryfaisal.wsk.dto.Payload;
import com.github.chaudhryfaisal.wsk.dto.Response;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.AttachmentKey;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;

public class UndertowUtil {
    public static void reply(HttpServerExchange exchange, Response response) {
        if (response == null) {
            exchange.setStatusCode(404);
        } else {
            if (!response.getHeaders().isEmpty()) {
                response.getHeaders().forEach((k, v) -> exchange.getResponseHeaders().add(HttpString.tryFromString(k), v));
            }
            respond(exchange, response.getBody(), exchange.getStatusCode(), 404);
        }
        exchange.endExchange();
    }

    public static Payload payload(HttpServerExchange exchange, AttachmentKey<Object> requestBody) {
        Payload.PayloadBuilder b = Payload.builder();
        exchange.getResponseHeaders().forEach(h -> b.header(h.getHeaderName().toString(), h.getFirst()));
        return b
                .path(exchange.getRequestPath())
                .query(exchange.getQueryString())
                .body(exchange.getAttachment(requestBody))
                .build().buildQueryMap();
    }

    public static void respond(HttpServerExchange exchange, Object o, int successCode, int failureCode) {
        if (o == null) {
            exchange.setStatusCode(failureCode);
        } else if (o instanceof String) {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            exchange.setStatusCode(successCode);
            exchange.getResponseSender().send((String) o);
        } else {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
            exchange.setStatusCode(successCode);
            exchange.getResponseSender().send(JsonUtil.serialize(o));
        }
        exchange.endExchange();

    }
}
