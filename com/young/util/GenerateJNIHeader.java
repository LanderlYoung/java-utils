package com.young.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Author: young
 * Date:   2014-10-16
 * Time:   下午10:50
 * Life with passion. Code with creativity!
 */
public class GenerateJNIHeader {
    public static void generate(Class<?> c) {
        for (Method m : c.getDeclaredMethods()) {
            System.out.println(generateNativeMethod(m));
        }
    }

    public static String generateNativeMethod(Method m) {
        String func = "static " +
                ToJNIType.toJNIType(m.getReturnType()) + " "
                + m.getName() + "(JNIEnv * env";
        func += Modifier.isStatic(m.getModifiers()) ? ", jclass clazz" : ", jobject thiz";
        for (Class<?> c : m.getParameterTypes()) {
            func += ", " + ToJNIType.toJNIType(c);
        }
        func += ");";

        return func;
    }
}
