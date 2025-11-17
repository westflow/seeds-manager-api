package com.westflow.seeds_manager_api.infrastructure.web.util;

import java.util.regex.Pattern;

public class HtmlSanitizers {

    private static final Pattern HTML_ENTITY_PATTERN = Pattern.compile("&[a-zA-Z0-9#]+;");

    private HtmlSanitizers() {}

    public static boolean probablyEncoded(String s) {
        if (s == null) return false;
        return HTML_ENTITY_PATTERN.matcher(s).find();
    }
}
