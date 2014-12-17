package com.young.util.jni.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: taylorcyang
 * Date:   2014-12-17
 * Time:   9:59
 * Life with passion. Code with creativity!
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface NativeClass {
}
