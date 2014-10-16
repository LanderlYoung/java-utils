package com.young.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Author: landerlyoung
 * Date:   2014-10-15
 * Time:   17:54
 * Life with passion. Code with creativity!
 */
public class TypeSignature {
    public static String getMethodSignature(Method m) {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        for (Class<?> param : m.getParameterTypes()) {
            getSignatureClassName(sb, param);
        }
        sb.append(')');
        getSignatureClassName(sb, m.getReturnType());
        return sb.toString();
    }

    public static String getFieldSignature(Field f) {
        return getTypeSignature(f.getType());
    }

    public static String getTypeSignature(Class<?> c) {
        return getSignatureClassName(new StringBuilder(), c).toString();
    }

    private static StringBuilder getSignatureClassName(StringBuilder sb, Class<?> c) {
        while (c.isArray()) {
            sb.append('[');
            c = c.getComponentType();
        }
        return getObjectSignatureClassName(sb, c);
    }

    private static StringBuilder getObjectSignatureClassName(StringBuilder sb, Class<?> c) {
        if (c == char.class) {
            sb.append('C');
        } else if (c == byte.class) {
            sb.append('B');
        } else if (c == short.class) {
            sb.append('S');
        } else if (c == int.class) {
            sb.append('I');
        } else if (c == long.class) {
            sb.append('J');
        } else if (c == float.class) {
            sb.append('F');
        } else if (c == double.class) {
            sb.append('D');
        } else if (c == boolean.class) {
            sb.append('Z');
        } else if (c == void.class) {
            sb.append('V');
        } else {
            sb.append('L').append(c.getName().replace('.', '/')).append(';');
        }
        return sb;
    }
}
