package com.westflow.seeds_manager_api.infrastructure.web;

import com.westflow.seeds_manager_api.infrastructure.web.util.HtmlSanitizers;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.util.*;

@ControllerAdvice
public class ResponseSanitizerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if (body == null) return null;
        if (selectedContentType != null && !selectedContentType.includes(MediaType.APPLICATION_JSON)) {
            return body;
        }

        try {
            return sanitize(body);
        } catch (Exception e) {
            return body;
        }
    }

    @SuppressWarnings("unchecked")
    private Object sanitize(Object input) throws Exception {
        if (input == null) return null;
        Class<?> clazz = input.getClass();

        if (String.class.isAssignableFrom(clazz)) {
            String s = (String) input;
            if (s.isEmpty()) return s;
            if (HtmlSanitizers.probablyEncoded(s)) {
                return s;
            }
            return StringEscapeUtils.escapeHtml4(s);
        }

        if (Collection.class.isAssignableFrom(clazz)) {
            Collection<Object> col = (Collection<Object>) input;
            Collection<Object> out = createCollectionInstance(col);
            for (Object o : col) out.add(sanitize(o));
            return out;
        }

        if (Map.class.isAssignableFrom(clazz)) {
            Map<Object, Object> map = (Map<Object, Object>) input;
            Map<Object, Object> out = new LinkedHashMap<>();
            for (Map.Entry<Object, Object> e : map.entrySet()) {
                out.put(sanitize(e.getKey()), sanitize(e.getValue()));
            }
            return out;
        }

        if (clazz.isArray()) {
            Object[] arr = (Object[]) input;
            Object[] out = new Object[arr.length];
            for (int i = 0; i < arr.length; i++) out[i] = sanitize(arr[i]);
            return out;
        }

        if (clazz.isPrimitive() || clazz.getPackageName().startsWith("java.")) {
            return input;
        }

        Object sanitized = clazz.getDeclaredConstructor().newInstance();
        for (Field f : getAllFields(clazz)) {
            f.setAccessible(true);
            Object value = f.get(input);
            if (value == null) continue;
            Object sVal = sanitize(value);
            f.set(sanitized, sVal);
        }
        return sanitized;
    }

    private Collection<Object> createCollectionInstance(Collection<?> c) {
        try {
            return c.getClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null && !clazz.equals(Object.class)) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
