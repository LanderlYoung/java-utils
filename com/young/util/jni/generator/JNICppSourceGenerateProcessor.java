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
    private Elements mElementsUtils;
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mTypeUtils = processingEnv.getTypeUtils();
        mElementsUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.errorRaised() || roundEnv.processingOver()) return true;

        //classify annotations by class
        Set<? extends Element> classes =
                roundEnv.getElementsAnnotatedWith(NativeClass.class);

        if (classes.isEmpty()) return true;

        final Environment env = new Environment(mMessager,
                mTypeUtils, mElementsUtils, mFiler, roundEnv);
        for (Element ec : classes) {
            if (ec instanceof TypeElement) {
                CppCodeGenerator codeGen = new CppCodeGenerator(env, (TypeElement) ec);
                codeGen.doGenerate();
            }
        }
        return true;
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
}
