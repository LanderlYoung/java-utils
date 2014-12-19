package com.young.util.jni.generator;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Stack;

/**
 * Author: taylorcyang
 * Date:   2014-12-17
 * Time:   20:19
 * Life with passion. Code with creativity!
 */
public class HandyHelper {

    public static String toJNIType(TypeMirror t, Types typeUtils) {
        if (t == null) return "";
        final String c = t.toString();
        final String throwable = "java.lang.Throwable";
        final String jthrowable = "jthrowable";

        //check if t is a subclass of java.lang.Throwable
        if (throwable.equals(c)) {
            return jthrowable;
        } else {
            Element base = typeUtils.asElement(t);
            while (base instanceof TypeElement) {
                TypeMirror sup = ((TypeElement) base).getSuperclass();
                if (sup instanceof NoType) break;
                if (throwable.equals(sup.toString())) {
                    return jthrowable;
                }
                base = typeUtils.asElement(sup);
            }
        }

        //for android, use java language level 6, can not use string case!!
        if ("void".equals(c)) {
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
            //multi dimension array
            return "jobjectArray";
        } else if (c.endsWith("[]")) {
            //java.lang.Object[]
            return "jobjectArray";
        } else if (c.equals("java.lang.String")) {
            return "jstring";
        } else if (c.equals("java.lang.Class")) {
            return "jclass";
        }
       /*
        else if ("java.lang.Throwable".equals(c)) {
            return "jthrowable";
        }
        */
        else {
            return "jobject";
        }
    }

    //same as java
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

    /**
     * @return like com.example_package.SomeClass$InnerClass
     */
    public static String getClassName(Element clazz, Elements elementUtils) {
        Stack<String> className = new Stack<String>();
        StringBuilder sb = new StringBuilder();
        Element e = clazz;
        while (e != null && ElementKind.CLASS.equals(e.getKind())) {
            className.push(e.getSimpleName().toString());
            e = e.getEnclosingElement();
        }

        PackageElement pkg = elementUtils.getPackageOf(clazz);
        if (pkg != null) {
            sb.append(pkg.getQualifiedName().toString());
            sb.append('.');
        }

        while (!className.empty()) {
            sb.append(className.pop());
            sb.append('$');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * @param v
     * @return
     */
    public static String getJNIHeaderConstantValue(Object v, int arch) {
        if (v == null) {
            return "";
        }
        Class<?> clazz = v.getClass();
        if (Boolean.class == clazz) {
            return (Boolean) v ? "1L" : "0L";
        } else if (clazz == Byte.class ||
                clazz == Short.class ||
                clazz == Integer.class) {
            return v + "L";
        } else if (clazz == Character.class) {
            return (int) (Character) v + "L";
        } else if (clazz == Long.class) {
            return v + "LL";
        } else if (clazz == Float.class) {
            return v + "f";
        } else if (clazz == Double.class) {
            return v + "";
        } else if (clazz == String.class) {
            return "\"" + v + "\"";
        }
        return "";
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

    public static String getMethodSignatureForNative4Code(ExecutableElement e) {
        //FIXME change to fix inner class...
        return getMethodSignature(e);
    }
}
