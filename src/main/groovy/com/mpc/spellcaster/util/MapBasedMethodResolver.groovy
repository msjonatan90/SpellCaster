package com.mpc.spellcaster.util

import org.springframework.core.convert.TypeDescriptor
import org.springframework.expression.EvaluationContext
import org.springframework.expression.MethodExecutor
import org.springframework.expression.MethodResolver
import org.springframework.expression.TypedValue
import org.springframework.expression.spel.SpelMessage
import org.springframework.expression.spel.SpelParseException
import org.springframework.expression.spel.SpelParserConfiguration
import org.springframework.expression.spel.ast.MethodReference

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.rmi.AccessException

class MapBasedMethodResolver implements MethodResolver {

    @Override
    MethodExecutor resolve(EvaluationContext context, Object targetObject, String name, List<TypeDescriptor> argumentTypes) throws AccessException {
        if (targetObject instanceof Map) {
            Map targetMap = (Map) targetObject
            Object method = targetMap.get(name)
            if (method instanceof Closure) {
                return new MapClosureMethodExecutor(targetMap, (Closure) method)
            } else if (method instanceof Method) {
                return new MapMethodExecutor(targetMap, (Method) method)
            }
        }
        return null
    }

    private static class MapClosureMethodExecutor implements MethodExecutor {
        private final Map targetMap
        private final Closure closure

        MapClosureMethodExecutor(Map targetMap, Closure closure) {
            this.targetMap = targetMap
            this.closure = closure
        }

        @Override
        TypedValue execute(EvaluationContext context, Object target, Object... arguments) throws AccessException {
            Binding binding = new Binding(targetMap)
            closure.setDelegate(new Expando(binding))
            Object result = closure.call(arguments)
            return new TypedValue(result)
        }
    }

    private static class MapMethodExecutor implements MethodExecutor {
        private final Map targetMap
        private final Method method

        MapMethodExecutor(Map targetMap, Method method) {
            this.targetMap = targetMap
            this.method = method
        }

        @Override
        TypedValue execute(EvaluationContext context, Object target, Object... arguments) throws AccessException {
            Object result
            try {
                result = method.invoke(targetMap, arguments)
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new AccessException("Error invoking method", e)
            }
            return new TypedValue(result)
        }
    }
}