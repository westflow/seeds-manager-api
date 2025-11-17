package com.westflow.seeds_manager_api.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.owasp.encoder.Encode;

import java.util.HashMap;
import java.util.Map;

public class XssRequestWrapper extends HttpServletRequestWrapper {

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String val = super.getParameter(name);
        return sanitize(val);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] vals = super.getParameterValues(name);
        if (vals == null) return null;
        String[] out = new String[vals.length];
        for (int i = 0; i < vals.length; i++) out[i] = sanitize(vals[i]);
        return out;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> original = super.getParameterMap();
        Map<String, String[]> sanitized = new HashMap<>();
        original.forEach((k, v) -> {
            if (v == null) sanitized.put(k, null);
            else {
                String[] a = new String[v.length];
                for (int i = 0; i < v.length; i++) a[i] = sanitize(v[i]);
                sanitized.put(k, a);
            }
        });
        return sanitized;
    }

    @Override
    public String getHeader(String name) {
        String val = super.getHeader(name);
        return sanitize(val);
    }

    private String sanitize(String input) {
        if (input == null) return null;
        return Encode.forHtml(input);
    }
}
