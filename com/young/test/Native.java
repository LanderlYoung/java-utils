package com.young.test;

import java.util.Map;

/**
 * Author: young
 * Date:   2014-10-16
 * Time:   下午10:48
 * Life with passion. Code with creativity!
 */
public class Native {
    public native int hello_native(int a, int b, char[] c, String s);

    public native static  void world_native(Map m);

}
