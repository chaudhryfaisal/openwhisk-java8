package com.github.chaudhryfaisal.wsk;

import com.github.chaudhryfaisal.wsk.dto.Application;
import com.github.chaudhryfaisal.wsk.dto.Payload;
import com.github.chaudhryfaisal.wsk.dto.Response;
import com.github.chaudhryfaisal.wsk.util.JsonUtil;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;


public class Main {
    public static final String APPLICATION_CLASS = "APPLICATION_CLASS";
    public static final String SAMPLE_REQUEST = "{\"MAIN_CLASS\":\"com.fic.Application\",\"__ow_body\":\"\",\"__ow_headers\":{\"accept\":\"*/*\",\"accept-encoding\":\"gzip\",\"cdn-loop\":\"cloudflare\",\"cf-connecting-ip\":\"127.0.0.1\",\"cf-ipcountry\":\"US\",\"cf-ray\":\"55e08f923f7d9db3-ORD\",\"cf-visitor\":\"{\\\"scheme\\\":\\\"https\\\"}\",\"host\":\"us-south.functions.cloud.ibm.com\",\"user-agent\":\"curl/7.54.0\",\"x-forwarded-for\":\"127.0.0.2, 127.0.0.3\",\"x-forwarded-host\":\"us-south.functions.cloud.ibm.com\",\"x-forwarded-port\":\"443\",\"x-forwarded-proto\":\"https\",\"x-global-k8fdic-transaction-id\":\"36e5f16ff12506479c90ad948a00a9ba\",\"x-real-ip\":\"127.0.0.1\",\"x-request-id\":\"36e5f16ff12506479c90ad948a00a9ba\"},\"__ow_method\":\"get\",\"__ow_path\":\"/api/sub/path\",\"__ow_query\":\"key=val\"}";
    private static Application APP;

    public static void main(String[] args) {
        JsonObject json = JsonUtil.deserialize(SAMPLE_REQUEST, JsonObject.class);
        run(json);
        run(json);
    }

    private static void run(JsonObject json) {
        long start = System.currentTimeMillis();
        JsonObject response = main(json);
        long duration = (System.currentTimeMillis() - start);
        System.out.printf("%sms -> %s\n", duration, JsonUtil.serialize(response));
    }

    public static JsonObject main(JsonObject json) {
        return handle(json);
    }

    @SneakyThrows
    private static JsonObject handle(JsonObject json) {
        Payload payload = new Payload(json);
        if (APP == null) {
            System.out.printf("init -> %s\n", json.toString());
            init(payload);
        }
        Response response = APP.handle(payload);
        APP.postHandle();
        return JsonUtil.deserialize(response, JsonObject.class);
    }

    @SneakyThrows
    private static void init(Payload payload) {
        payload.getEnv().forEach(System::setProperty);
        String mainClass = payload.getEnv().get(APPLICATION_CLASS);
        System.out.println(APPLICATION_CLASS + "=" + mainClass);
        Constructor<?> ctor = Class.forName(mainClass).getConstructor();
        APP = (Application) ctor.newInstance();
        APP.init(payload);
    }
}
