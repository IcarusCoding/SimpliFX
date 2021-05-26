package de.intelligence.bachelorarbeit.reflectionutils;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * The {@link ClassReflection} class provides methods to perform reflective operations on {@link Class} level.
 *
 * @author Deniz Groenhoff
 */
public final class ClassReflection extends ReflectableScope<Class<?>> implements Annotatable {

    ClassReflection(Class<?> clazz) {
        super(clazz);
    }

    private static void iterateMethods(Class<?> clazz, @Nullable Identifier<Method> identifier, Callback<Method> callback) {
        Arrays.stream(clazz.getDeclaredMethods()).filter(m -> identifier != null && identifier.check(m))
                .forEach(callback::callback);
        if (clazz.isInterface()) {
            Arrays.stream(clazz.getInterfaces()).forEach(m -> iterateMethods(clazz, identifier, callback));
            return;
        }
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            ClassReflection.iterateMethods(clazz.getSuperclass(), identifier, callback);
        }
    }

    private static void iterateFields(Class<?> clazz, @Nullable Identifier<Field> identifier, Callback<Field> callback) {
        Arrays.stream(clazz.getDeclaredFields()).filter(f -> identifier != null && identifier.check(f))
                .forEach(callback::callback);
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            ClassReflection.iterateFields(clazz.getSuperclass(), identifier, callback);
        }
    }

    /**
     * Finds a constructor with the specified parameter types
     *
     * @param argTypes The constructor parameter types
     * @return A {@link ConstructorReflection} instance representing the new entry point
     */
    public ConstructorReflection findConstructor(Class<?>... argTypes) {
        if (argTypes == null) {
            argTypes = new Class[0];
        }
        for (var constructor : super.reflectable.getDeclaredConstructors()) {
            if (constructor.getParameterCount() != argTypes.length) {
                continue;
            }
            if (matchArguments(argTypes, constructor.getParameterTypes())) {
                return Reflection.reflect(constructor);
            }
        }
        throw new IllegalArgumentException("No suitable constructor found!");
    }

    /**
     * Iterates methods, filters them by an {@link Identifier} and executes a {@link Callback} on each of them
     *
     * @param identifier The method {@link Identifier}
     * @param callback   The method {@link Callback}
     * @return This instance
     */
    public ClassReflection iterateMethods(@Nullable Identifier<Method> identifier, Callback<Method> callback) {
        ClassReflection.iterateMethods(super.reflectable, identifier, callback);
        return this;
    }

    /**
     * Iterates fields, filters them by an {@link Identifier} and executes a {@link Callback} on each of them
     *
     * @param identifier The field {@link Identifier}
     * @param callback   The field {@link Callback}
     * @return This instance
     */
    public ClassReflection iterateFields(@Nullable Identifier<Field> identifier, Callback<Field> callback) {
        ClassReflection.iterateFields(super.reflectable, identifier, callback);
        return this;
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
        return super.reflectable.isAnnotationPresent(annotation);
    }

    @Override
    public <S extends Annotation> S getAnnotation(Class<S> annotation) {
        return super.reflectable.getDeclaredAnnotation(annotation);
    }

    @Override
    public AnnotatedElement getAnnotatableElement() {
        return super.reflectable;
    }

}