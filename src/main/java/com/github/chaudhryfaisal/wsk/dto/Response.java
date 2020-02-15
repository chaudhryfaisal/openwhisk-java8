package com.github.chaudhryfaisal.wsk.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Map;

/*
  @author Faisal Chaudhry
 */
@Builder
@Data
public class Response {
    @Builder.Default
    int statusCode = 200; //a valid HTTP status code (default is 200 OK if body is not empty otherwise 204 No Content).
    Object body; //a string which is either plain text, JSON object or array, or a base64 encoded string for binary data (default is empty response)
    @Singular
    Map<String, String> headers; // a JSON object where the keys are header-names and the values are string, number, or boolean values for those headers (default is no headers). To send multiple values for a single header, the header's value should be a JSON array of values.
    ResponseError error;

    public static Response body(Object body) {
        return Response.builder().body(body).build();
    }

    @Builder
    @Data
    public static class ResponseError {
        @Builder.Default
        int statusCode = 400;
        String error;
    }
}
