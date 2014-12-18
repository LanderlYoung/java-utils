package com.young.test;

import com.young.util.ClassInspector;
import com.young.util.TypeSignature;
import com.young.util.jni.JNIHelper;

import java.lang.reflect.Method;

/**
 * Author: young
 * Date:   2014-10-11
 * Time:   下午10:57
 * Life with passion. Code with creativity!
 */
public class Main {
    private static final int AA = 100;
    private static final Object lock = new Object();

    public static void main(String[] args) throws Exception {
        Class<?> c = null;

        System.out.println(JNIHelper.getJNIClassName(Native.class));
        System.out.println(JNIHelper.toJNIType(int.class));
        System.out.println(JNIHelper.toJNIType(void.class));
        c = Main.class;
        for (Method m : c.getDeclaredMethods()) {
            System.out.print(m.getName() + " : ");
            System.out.println(TypeSignature.getMethodSignature(m));

        }

        System.out.println();
        ClassInspector.inspect(Main.class);

        System.out.println();
        ClassInspector.inspect(ClassInspector.class);
    }
}
