package com.young.test;

import com.young.util.jni.generator.NativeClass;
import com.young.util.jni.generator.NativeMethod;

import java.util.Map;

/**
 * Author: young
 * Date:   2014-10-16
 * Time:   下午10:48
 * Life with passion. Code with creativity!
 */
@NativeClass
public class Native {
    public native int hello_native(int a, int b, char[] c, String s);

    @NativeMethod
    public native static void world_native(Map m);

    @NativeMethod("jint c = a + b;\nreturn c;")
    public native int add(int a, int b);

    @NativeMethod({
            "if (a <= 2) return a;",
            "jint b"

    })
    public native int fibo(int a);

}
