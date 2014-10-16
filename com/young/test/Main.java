package com.young.test;

import com.young.util.GenerateJNIHeader;
import com.young.util.ToJNIType;
import com.young.util.TypeSignature;

import java.lang.reflect.Method;

/**
 * Author: young
 * Date:   2014-10-11
 * Time:   下午10:57
 * Life with passion. Code with creativity!
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Class<?> c = null;

        Class.forName("");

        GenerateJNIHeader.generate(Native.class);

        System.out.println(ToJNIType.toJNIType(int.class));
        System.out.println(ToJNIType.toJNIType(void.class));
        c = Class.forName("java.util.Map");
        for(Method m: c.getDeclaredMethods()) {
            System.out.print(m.getName() + " ");
            System.out.println(TypeSignature.getMethodSignature(m));

        }
    }
}
