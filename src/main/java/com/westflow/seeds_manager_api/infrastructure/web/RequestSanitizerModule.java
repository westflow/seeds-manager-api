package com.westflow.seeds_manager_api.infrastructure.web;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class RequestSanitizerModule {

    private RequestSanitizerModule() {}

    public static SimpleModule module() {
        SimpleModule module = new SimpleModule("RequestSanitizerModule");
        module.addDeserializer(String.class, new RequestSanitizer());
        return module;
    }
}
