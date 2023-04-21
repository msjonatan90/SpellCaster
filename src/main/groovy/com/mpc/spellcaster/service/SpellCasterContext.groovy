package com.mpc.spellcaster.service

import org.springframework.expression.BeanResolver
import org.springframework.expression.ConstructorResolver
import org.springframework.expression.EvaluationContext
import org.springframework.expression.MethodResolver
import org.springframework.expression.OperatorOverloader
import org.springframework.expression.PropertyAccessor
import org.springframework.expression.TypeComparator
import org.springframework.expression.TypeConverter
import org.springframework.expression.TypeLocator
import org.springframework.expression.TypedValue
import org.springframework.context.expression.MapAccessor
import org.springframework.expression.spel.support.StandardEvaluationContext


class SpellCasterContext implements EvaluationContext {

    private final MapAccessor mapAccessor = new MapAccessor()
    private final StandardEvaluationContext delegate = new StandardEvaluationContext()

    SpellCasterContext(Object object) {
        delegate.addPropertyAccessor(mapAccessor)
        delegate.setRootObject(object)
    }

    @Override
    TypedValue getRootObject() {
        return delegate.getRootObject()
    }

    @Override
    List<ConstructorResolver> getConstructorResolvers() {
        return delegate.getConstructorResolvers()
    }

    @Override
    List<MethodResolver> getMethodResolvers() {
        return delegate.getMethodResolvers()
    }

    @Override
    List<PropertyAccessor> getPropertyAccessors() {
        return delegate.getPropertyAccessors()
    }

    @Override
    TypeLocator getTypeLocator() {
        return delegate.getTypeLocator()
    }

    @Override
    TypeConverter getTypeConverter() {
        return delegate.getTypeConverter()
    }

    @Override
    TypeComparator getTypeComparator() {
        return delegate.getTypeComparator()
    }

    @Override
    OperatorOverloader getOperatorOverloader() {
        return delegate.getOperatorOverloader()
    }

    @Override
    BeanResolver getBeanResolver() {
        return delegate.getBeanResolver()
    }

    @Override
    void setVariable(String name, Object value) {
        delegate.setVariable(name, value)
    }

    @Override
    Object lookupVariable(String name) {
        return delegate.lookupVariable(name)
    }
}
