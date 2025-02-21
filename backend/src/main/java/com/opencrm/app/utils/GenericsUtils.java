package com.opencrm.app.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@SuppressWarnings("unchecked")
public class GenericsUtils {
    private GenericsUtils() {
    }

    public static <T> Class<T> getGenericClass(Class<?> clazz) {
        return getGenericClass(clazz, 0, null);
    }

    public static <T> Class<T> getGenericClass(Class<?> clazz, int index) {
        return getGenericClass(clazz, index, null);
    }

    public static <T> Class<T> getGenericClass(Class<?> clazz, int index, Class<?> parentClazz) {
        Type genType = clazz.getGenericSuperclass();

        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

            if (params != null && params.length > index) {
                if (parentClazz != null) {
                    Class<T> preClazz = (Class<T>) params[index];
                    if (isSub(preClazz, parentClazz)) {
                        return preClazz;
                    } else {
                        index++;
                        return getGenericClass(clazz, index, parentClazz);
                    }
                } else if (params[index] instanceof Class) {
                    return (Class<T>) params[index];
                }
            }
        } else if (!clazz.equals(Object.class)) {
            return getGenericClass(clazz.getSuperclass(), index, parentClazz);
        }

        return null;
    }

    private static boolean isSub(Class<?> subClass, Class<?> supClass) {
        do {
            if (subClass.isAssignableFrom(supClass)) {
                return true;
            }

            Class<?>[] interfaces = subClass.getInterfaces();
            if (interfaces != null) {
                for (int i = 0; i < interfaces.length; i++) {
                    if (isSub(interfaces[i], supClass)) {
                        return true;
                    }
                }
                subClass = subClass.getSuperclass();
            }
        } while (subClass != null && (!subClass.getSuperclass().equals(Object.class)));

        return false;
    }
}
