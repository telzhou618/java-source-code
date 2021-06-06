package com.example.springframework.util;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * @author zhougaojun
 */
public class AnnotationUtils {


    public static boolean isAnyAnnotationPresent(Class[] annotationClass, Class annotationType) {
        if (annotationClass != null && annotationClass.length > 0) {
            for (Class aClass : annotationClass) {
                if (findAnnotation(aClass, annotationType) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isAnnotationPresent(Class annotationClass, Class annotationType) {
        return findAnnotation(annotationClass, annotationType) != null;
    }

    public static <A> A findAnnotation(Class annotationClass, Class annotationType) {
        // Do NOT store result in the findAnnotationCache since doing so could break
        // findAnnotation(Class, Class) and findAnnotation(Method, Class).
        if (annotationType.isAnnotationPresent(annotationClass)) {
            return (A) annotationType.getAnnotation(annotationClass);
        }
        Annotation[] annotations = annotationType.getAnnotations();
        if (annotations.length == 0) {
            return null;
        }
        for (Annotation a : annotations) {
            if (Objects.equals(a.annotationType().getName(), annotationClass.getName())) {
                return (A) a;
            }
        }
        return null;
    }
}
