package com.young.util;

/**
 * Author: young
 * Date:   2014-10-16
 * Time:   下午10:35
 * Life with passion. Code with creativity!
 */
public class ToJNIType {
    public static String toJNIType(Class<?> c) {
        if (c == null) {
            return "";
        } else if (c.isPrimitive()) {
            if (c != void.class) {
                return "j" + c.getName();
            } else {
                return "void";
            }
        } else if (c.isArray()) {
            if (c.getComponentType().isPrimitive()) {
                return "j" + c.getComponentType().getName() + "Array";
            } else {
                return "jobjectArray";
            }
        } else if (c == String.class) {
            return "jstring";
        } else if (c == Throwable.class) {
            return "jthrowable";
        } else if (c == Class.class) {
            return "jclass";
        } else {
            return "jobject";
        }
    }
}
