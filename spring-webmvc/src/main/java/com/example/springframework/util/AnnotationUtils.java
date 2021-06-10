package com.example.springframework.util;

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

        if (annotationType.isAnnotationPresent(annotationClass)) {
            return (A) annotationType.getAnnotation(annotationClass);
        }
        return null;
    }
}
