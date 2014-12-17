package com.young.util.jni.generator;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Author: taylorcyang
 * Date:   2014-12-17
 * Time:   20:19
 * Life with passion. Code with creativity!
 */
public class Helper {
    public static String toJNIType(String c) {
        if (c == null) {
            return "";
        } else if ("void".equals(c)) {
            return "void";
        } else if ("char".equals(c) ||
                "boolean".equals(c) ||
                "byte".equals(c) ||
                "short".equals(c) ||
                "int".equals(c) ||
                "long".equals(c) ||
                "float".equals(c) ||
                "double".equals(c)) {
            return 'j' + c;
        } else if ("char[]".equals(c) ||
                "boolean[]".equals(c) ||
                "byte[]".equals(c) ||
                "short[]".equals(c) ||
                "int[]".equals(c) ||
                "long[]".equals(c) ||
                "float[]".equals(c) ||
                "double[]".equals(c)) {
            return 'j' + c.substring(0, c.length() - 2) + "Array";
        } else if (c.endsWith("[][]")) {
            //mulity dimention array
            return "jobjectArray";
        } else if (c.endsWith("[]")) {
            //java.lang.Object[]
            return "jobjectArray";
        } else if (c.equals("java.lang.String")) {
            return "jstring";
        } else if (c.equals("java.lang.Class")) {
            return "jclass";
        } else if ("java.lang.Throwable".equals(c)) {
            //FIXME all subclass of Throwable should be jthorwable
            return "jthrowable";
        } else {
            return "jobject";
        }
    }

    public static String getMethodSignature(ExecutableElement method) {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        for (VariableElement param : method.getParameters()) {
            getSignatureClassName(sb, param.asType());
        }
        sb.append(')');
        getSignatureClassName(sb, method.getReturnType());
        return sb.toString();
    }

    private static void getSignatureClassName(StringBuilder sb, TypeMirror typeMirror) {
        String type = typeMirror.toString();
        while (type.endsWith("[]")) {
            sb.append('[');
            type = type.substring(0, type.length() - 2);
        }
        getObjectSignatureClassName(sb, type);
    }

    private static void getObjectSignatureClassName(StringBuilder sb, String type) {
        if ("char".equals(type)) {
            sb.append('C');
        } else if ("byte".equals(type)) {
            sb.append('B');
        } else if ("short".equals(type)) {
            sb.append('S');
        } else if ("int".equals(type)) {
            sb.append('I');
        } else if ("long".equals(type)) {
            sb.append('J');
        } else if ("float".equals(type)) {
            sb.append('F');
        } else if ("double".equals(type)) {
            sb.append('D');
        } else if ("boolean".equals(type)) {
            sb.append('Z');
        } else if ("void".equals(type)) {
            sb.append('V');
        } else {
            sb.append('L').append(type.replace('.', '/')).append(';');
        }
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
