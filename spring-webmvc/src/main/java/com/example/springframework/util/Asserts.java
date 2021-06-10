package com.example.springframework.util;

/**
 * @author zhougaojun
 * @since 2021/6/10
 */
public class Asserts {

    public static void check(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void check(final boolean expression, final String message, final Object... args) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, args));
        }
    }

    public static void check(final boolean expression, final String message, final Object arg) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, arg));
        }
    }

    public static void notNull(final Object object, final String name) {
        if (object == null) {
            throw new IllegalStateException(name + " is null");
        }
    }

    public static void notEmpty(final CharSequence s, final String name) {
        if (StringUtil.isEmpty(s)) {
            throw new IllegalStateException(name + " is empty");
        }
    }

    public static void notBlank(final CharSequence s, final String name) {
        if (StringUtil.isBlank(s)) {
            throw new IllegalStateException(name + " is blank");
        }
    }
}
