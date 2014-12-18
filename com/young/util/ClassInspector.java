package com.young.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Tool that use reflect to dynamically inspect the structure of a class.
 * <p/>
 * Author: taylorcyang
 * Date:   2014-11-27
 * Time:   10:33
 * Life with passion. Code with creativity!
 */
public class ClassInspector {
    public static void inspect(Class<?> clazz) {
        inspectHierarchy(clazz);
        inspectFields(clazz);
        inspectConstructors(clazz);
        inspectMethods(clazz);
        inspectInnerClasses(clazz);
    }

    private static void inspectInnerClasses(Class<?> clazz) {
        Class<?>[] clazzes = clazz.getDeclaredClasses();
        for (Class<?> c : clazzes) {
            inspect(c);
        }
    }

    private static void inspectHierarchy(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        Class<?>[] interfaces = clazz.getInterfaces();
        getClassName(clazz, sb);
        clazz = clazz.getSuperclass();
        while (clazz != null /*&& !clazz.equals(Object.class)*/) {
            sb.append(" -> ");
            getClassName(clazz, sb);
            clazz = clazz.getSuperclass();
        }

        if (interfaces.length > 0) {
            sb.append(" : ");
            for (Class<?> i : interfaces) {
                getClassName(i, sb);
                sb.append(" ,");
            }
            //delete the last comma
            sb.deleteCharAt(sb.length() - 1);
        }
        System.out.println(sb.toString());
    }

    private static void getClassName(Class<?> clazz, StringBuilder sb) {
        if (Modifier.isInterface(clazz.getModifiers())) {
            sb.append("<").append(clazz.getName()).append(">");
        } else {
            sb.append(clazz.getName());
        }
    }

    private static void inspectConstructors(Class<?> clazz) {
        for (Constructor c : clazz.getDeclaredConstructors()) {
            System.out.print("[C]: " +
                            getModifier(c.getModifiers()) +
                            c.getName()
            );
            printParams(c.getParameterTypes());
            printExceptions(c.getExceptionTypes());
            System.out.println();
        }
    }

    private static void inspectMethods(Class<?> clazz) {
        for (Method m : clazz.getDeclaredMethods()) {
            System.out.print("[M]: " +
                            getModifier(m.getModifiers()) +
                            m.getReturnType().getCanonicalName() + " " +
                            m.getName()
            );
            printParams(m.getParameterTypes());
            printExceptions(m.getExceptionTypes());
            System.out.println();
        }
    }

    private static void printParams(Class<?>[] params) {
        if (params.length == 0) {
            System.out.print("()");
            return;
        }
        for (int i = 0; i < params.length; i++) {
            if (i == 0) {
                System.out.print("(");
            } else {
                System.out.print(", ");
            }
            System.out.print(params[i].getCanonicalName());
            if (i == params.length - 1) {
                System.out.print(")");
            }
        }
    }

    private static void printExceptions(Class<?>[] exceps) {
        for (int i = 0; i < exceps.length; i++) {
            if (i == 0) {
                System.out.print(" throws ");
            } else {
                System.out.print(", ");
            }
            System.out.print(exceps[i].getCanonicalName());
            if (i == exceps.length - 1) {
                System.out.print(';');
            }

        }
    }

    private static void inspectFields(Class<?> clazz) {
        for (Field f : clazz.getDeclaredFields()) {
            System.out.println("[F]: " +
                            getModifier(f.getModifiers()) +
                            f.getType().getName() + " " +
                            f.getName() +
                            extractValue(f)
            );
        }
    }

    private static String extractValue(Field f) {
        if (Modifier.isStatic(f.getModifiers())) {
            f.setAccessible(true);
            try {
                Object val = f.get(null);
                return " = " + val.toString() + "";
            } catch (IllegalAccessException e) {

            }
        }
        return "";
    }

    private static String getModifier(int m) {
        StringBuilder sb = new StringBuilder();
        if (Modifier.isPublic(m)) sb.append("public ");
        if (Modifier.isPrivate(m)) sb.append("private ");
        if (Modifier.isProtected(m)) sb.append("protected ");
        if (Modifier.isNative(m)) sb.append("native");
        if (Modifier.isAbstract(m)) sb.append("abstract ");
        if (Modifier.isSynchronized(m)) sb.append("synchronized ");
        if (Modifier.isStatic(m)) sb.append("static ");
        if (Modifier.isFinal(m)) sb.append("final ");
        if (Modifier.isVolatile(m)) sb.append("volatile ");

        return sb.toString();
    }
}
