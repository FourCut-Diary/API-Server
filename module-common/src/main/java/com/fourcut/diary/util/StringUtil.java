package com.fourcut.diary.util;

import com.fourcut.diary.constant.SensitiveKeyword;

public class StringUtil {

    public static String maskSensitiveFields(String json) {
        for (String key : SensitiveKeyword.SENSITIVE_KEYWORDS) {
            json = json.replaceAll("(\"" + key + "\"\\s*:\\s*\")[^\"]*\"", "$1****\"");
        }
        return json;
    }
}
