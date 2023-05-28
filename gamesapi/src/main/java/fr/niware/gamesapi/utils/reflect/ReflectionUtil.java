package fr.niware.gamesapi.utils.reflect;

import sun.misc.Unsafe;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtil {
    private static MethodHandles.Lookup LOOKUP;
    private static Unsafe UNSAFE;

    private ReflectionUtil() throws IllegalAccessException {
        throw new IllegalAccessException("This class cannot be instantiated");
    }

    static {
        try {
            ReflectionUtil.LOOKUP = MethodHandles.lookup();
            ReflectionUtil.UNSAFE = (Unsafe) ReflectionUtil.getField(Unsafe.class, "theUnsafe").get(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        if (clazz == null) {
            return null;
        }

        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            return field;
        } catch (Exception ex) {
            return null;
        }
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... params) {
        if (clazz == null) {
            return null;
        }

        try {
            Method method = clazz.getDeclaredMethod(methodName, params);
            method.setAccessible(true);

            return method;
        } catch (Exception ex) {
            return null;
        }
    }

    public static MethodHandle getMethodHandle(Class<?> clazz, String methodName, Class<?>... params) {
        if (clazz == null) {
            return null;
        }

        try {
            return ReflectionUtil.LOOKUP.unreflect(ReflectionUtil.getMethod(clazz, methodName, params));
        } catch (Exception ex) {
            return null;
        }
    }

    public static FieldAccessor getFieldAccessor(Class<?> clazz, String fieldName) {
        Field field = ReflectionUtil.getField(clazz, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Field not found for " + clazz.getCanonicalName() + "#" + fieldName);
        }

        return new FieldAccessor(clazz, field, ReflectionUtil.UNSAFE);
    }
}

