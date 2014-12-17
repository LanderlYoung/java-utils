package com.young.util.jni.generator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Author: taylorcyang
 * Date:   2014-12-16
 * Time:   19:42
 * Life with passion. Code with creativity!
 */

@SupportedAnnotationTypes({"com.young.util.jni.generator.NativeMethod",
        "com.young.util.jni.generator.NativeClass"})
public class JNICppSourceGenerateProcessor extends AbstractProcessor {
    private Messager mMessager;
    private Types mTypeUtils;
    private Elements mEmelentsUtils;
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mTypeUtils = processingEnv.getTypeUtils();
        mEmelentsUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //classify annotations by class
        Set<? extends Element> classes =
                roundEnv.getElementsAnnotatedWith(NativeClass.class);
        Set<? extends Element> methods =
                roundEnv.getElementsAnnotatedWith(NativeMethod.class);

        if (classes.isEmpty() && methods.isEmpty()) return true;

        List<ElementClazz> clazzs = split(classes, methods);
        for (ElementClazz ec : clazzs) {
            if (ec.methods == null) {
                warn("Class " + ec.clazz.getSimpleName() + (" has not native method" +
                        "marked by NativeMethod annotation"));
            } else {
                processNativeClass(ec);
            }
        }
        return true;
    }

    private List<ElementClazz> split(Set<? extends Element> classes, Set<? extends Element> methods) {
        LinkedList<ElementClazz> l = new LinkedList<ElementClazz>();
        for (Element e : classes) {
            ElementClazz ec = new ElementClazz();
            ec.clazz = e;
            l.add(ec);
        }

        //FIXME fake implementation
        if(l.isEmpty()) return null;

        ElementClazz ec = l.getFirst();
        ec.methods = new LinkedList<Element>();
        for (Element e : methods) {
            ec.methods.add(e);
            //log(e.getSimpleName().toString());
        }

        return l;
    }

    private void warn(String msg) {
        mMessager.printMessage(Diagnostic.Kind.WARNING, msg);
    }

    private void log(String msg) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private static class ElementClazz {
        public Element clazz;
        public List<Element> methods;
    }

    private void processNativeClass(ElementClazz clazz) {
        for (Element method : clazz.methods) {
            log(method.getSimpleName().toString());
        }
        log(clazz.clazz.getSimpleName().toString());
    }
}
