package com.kjh.ollama.langserve.util;

public class MessageUtils {
    public static String toErrorMessageFormat(Exception e) {
        return String.format(
                "\nErrorMessage : %s\n StackTraceElement : %s", e.getMessage(), getStackTraceMsg(e.getStackTrace())
        );
    }

    public static String getStackTraceMsg(StackTraceElement[] e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
