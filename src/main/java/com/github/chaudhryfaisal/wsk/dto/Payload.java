package com.github.chaudhryfaisal.wsk.dto;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/*
  @author Faisal Chaudhry
 */
@Data
@Builder
@AllArgsConstructor
public class Payload {
    private String user;
    private String method;
    private String path;
    private String query;
    private Object body;
    private Map<String, String> params;
    private Map<String, String> env;
    @Singular
    private Map<String, String> headers;
    /*
        __ow_method (type: string): the HTTP method of the request.
        __ow_headers (type: map string to string): the request headers.
        __ow_path (type: string): the unmatched path of the request (matching stops after consuming the action extension).
        __ow_user (type: string): the namespace identifying the OpenWhisk authenticated subject.
        __ow_body (type: string): the request body entity, as a base64 encoded string when content is binary or JSON object/array, or plain string otherwise.
        __ow_query (type: string): the query parameters from the request as an unparsed string.
     */

    public Payload(JsonObject json) {
        env = new HashMap<>();
        headers = new HashMap<>();
        json.entrySet().forEach(e -> {
            String key = e.getKey();
            JsonElement value = e.getValue();
            if ("__ow_method".equals(key)) {
                method = value.getAsString();
            } else if ("__ow_path".equals(key)) {
                path = value.getAsString();
            } else if ("__ow_query".equals(key)) {
                query = value.getAsString();
            } else if ("__ow_body".equals(key)) {
                body = value.getAsString();
            } else if ("__ow_user".equals(key)) {
                user = value.getAsString();
            } else if ("__ow_headers".equals(key)) {
                value.getAsJsonObject().entrySet().forEach(h -> headers.put(h.getKey(), h.getValue().getAsString()));
            } else {
                env.put(key, value.getAsString());
            }
        });
        buildQueryMap();
    }

    public Payload buildQueryMap() {
        params = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                try {
                    if (idx > 0) {
                        params.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
                    }
                } catch (UnsupportedEncodingException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return this;
    }

    public String param(String key) {
        return params.getOrDefault(key, "");
    }

    public String header(String key) {
        return headers.getOrDefault(key, "");
    }
}
