package com.young.test;

import com.young.util.jni.generator.NativeClass;
import com.young.util.jni.generator.NativeSource;

/**

 * Author: young
 * Date:   2014-10-16
 * Time:   下午10:48
 * Life with passion. Code with creativity!
 */
@NativeClass()
public class Native {
    static {
        System.loadLibrary("native");
    }

    @NativeSource("jint c = a + b;\nreturn c;")
    public native int add(int a, int b);

    public static void main(String[] args) {
        Native n = new Native();
        System.out.println(n.add(1, 2));
    }
}
