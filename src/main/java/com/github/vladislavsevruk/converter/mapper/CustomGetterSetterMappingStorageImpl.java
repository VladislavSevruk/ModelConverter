/*
 * MIT License
 *
 * Copyright (c) 2021 Uladzislau Seuruk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.vladislavsevruk.converter.mapper;

import com.github.vladislavsevruk.converter.context.ConversionContext;
import com.github.vladislavsevruk.converter.mapper.method.MappedMethod;
import com.github.vladislavsevruk.converter.mapper.method.MethodMeta;
import com.github.vladislavsevruk.converter.mapper.method.MethodMetaConverterPair;
import com.github.vladislavsevruk.converter.util.ClassUtil;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import com.github.vladislavsevruk.resolver.type.TypeProvider;
import com.github.vladislavsevruk.resolver.util.TypeMetaUtil;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementation of <code>CustomGetterSetterMappingStorage</code>.
 *
 * @see CustomGetterSetterMappingStorage
 */
@Log4j2
public final class CustomGetterSetterMappingStorageImpl implements CustomGetterSetterMappingStorage {

    private final ConversionContext conversionContext;
    private List<MappingNode<?>> mappingNodes = new ArrayList<>();
    private final ReadWriteLock mappingsLock = new ReentrantReadWriteLock();

    public CustomGetterSetterMappingStorageImpl(ConversionContext conversionContext) {
        this.conversionContext = conversionContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addGetterSetterMapping(Method donorMethod, Method acceptorMethod) {
        addGetterSetterMapping(donorMethod, acceptorMethod, acceptorMethod.getDeclaringClass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addGetterSetterMapping(Method donorMethod, Method acceptorMethod, Class<?> acceptorClass) {
        addGetterSetterMapping(donorMethod, acceptorMethod, new TypeMeta<>(acceptorClass));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addGetterSetterMapping(Method donorMethod, Method acceptorMethod, TypeMeta<?> acceptorMeta) {
        Objects.requireNonNull(donorMethod, "Donor method shouldn't be null.");
        Objects.requireNonNull(acceptorMethod, "Acceptor method shouldn't be null.");
        Objects.requireNonNull(acceptorMeta, "Acceptor type meta shouldn't be null.");
        validateMethods(donorMethod, acceptorMethod, acceptorMeta);
        mappingsLock.writeLock().lock();
        getOrCreateNode(acceptorMeta).mappings.put(donorMethod, acceptorMethod);
        mappingsLock.writeLock().unlock();
        log.info("Added custom getter setter mapping for {}: {} -> {}.", acceptorMeta, donorMethod, acceptorMethod);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addGetterSetterMapping(Method donorMethod, Method acceptorMethod,
            TypeProvider<?> acceptorTypeProvider) {
        addGetterSetterMapping(donorMethod, acceptorMethod, acceptorTypeProvider.getTypeMeta());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MappedMethod getMapping(Method donorMethod, Object donorMethodValue, TypeMeta<?> acceptorMeta) {
        mappingsLock.readLock().lock();
        Method customMapping = getCustomMapping(donorMethod, acceptorMeta);
        MappedMethod mappedMethod = getMappedMethod(donorMethodValue, acceptorMeta, customMapping);
        mappingsLock.readLock().unlock();
        return mappedMethod;
    }

    private <T> MappingNode<T> addNewNode(TypeMeta<T> typeMeta) {
        MappingNode<T> newNode = new MappingNode<>(typeMeta);
        int index = mappingNodes.size();
        for (int i = 0; i < mappingNodes.size(); ++i) {
            MappingNode<?> hookNode = mappingNodes.get(i);
            if (TypeMetaUtil.isTypesMatch(hookNode.typeMeta, typeMeta)) {
                // find first assignable is enough as items sorted in the way that superclasses have greater indices than subclasses
                index = i;
                break;
            }
            if (TypeMetaUtil.isTypesMatch(typeMeta, hookNode.typeMeta)) {
                // use greatest index of found subclasses as superclasses should have greater indices than subclasses
                index = i + 1;
            }
        }
        mappingNodes.add(index, newNode);
        return newNode;
    }

    private Method getCustomMapping(Method donorMethod, TypeMeta<?> acceptorMeta) {
        for (MappingNode<?> mappingNode : mappingNodes) {
            if (TypeMetaUtil.isTypesMatch(mappingNode.typeMeta, acceptorMeta)) {
                Method mappedMethod = mappingNode.mappings.get(donorMethod);
                if (Objects.nonNull(mappedMethod)) {
                    return mappedMethod;
                }
            }
        }
        return null;
    }

    private MappedMethod getMappedMethod(Object donorMethodValue, TypeMeta<?> acceptorMeta, Method acceptorMethod) {
        if (Objects.isNull(acceptorMethod)) {
            return null;
        }
        TypeMeta<?> acceptorParameterTypeMeta = getParameterTypeMeta(acceptorMeta, acceptorMethod);
        if (acceptorParameterTypeMeta.getType().isAssignableFrom(donorMethodValue.getClass()) && ClassUtil
                .nonParameterized(acceptorParameterTypeMeta)) {
            return new MappedMethod(acceptorMethod);
        }
        MethodMeta acceptorMethodMeta = new MethodMeta(acceptorMethod, acceptorParameterTypeMeta, null);
        MethodMetaConverterPair<?> metaConverterPair = conversionContext.getTypeConverterPicker()
                .pickConverter(donorMethodValue.getClass(), Collections.singleton(acceptorMethodMeta));
        if (Objects.nonNull(metaConverterPair)) {
            return new MappedMethod(acceptorMethod, metaConverterPair.getConverter());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> MappingNode<T> getNode(TypeMeta<T> typeMeta) {
        for (MappingNode<?> mappingNode : mappingNodes) {
            if (TypeMetaUtil.isSameTypes(mappingNode.typeMeta, typeMeta)) {
                return (MappingNode<T>) mappingNode;
            }
        }
        return null;
    }

    private <T> MappingNode<T> getOrCreateNode(TypeMeta<T> typeMeta) {
        return Optional.ofNullable(getNode(typeMeta)).orElseGet(() -> addNewNode(typeMeta));
    }

    private TypeMeta<?> getParameterTypeMeta(TypeMeta<?> typeMeta, Method method) {
        return conversionContext.getExecutableTypeResolver().getParameterTypes(typeMeta, method).get(0);
    }

    private void validateMethods(Method donorMethod, Method acceptorMethod, TypeMeta<?> acceptorMeta) {
        if (donorMethod.getReturnType().equals(void.class)) {
            throw new IllegalArgumentException("Donor method should have non void return type.");
        }
        if (donorMethod.getParameters().length != 0) {
            throw new IllegalArgumentException("Donor method should have no method arguments.");
        }
        if (acceptorMethod.getParameters().length != 1) {
            throw new IllegalArgumentException("Acceptor method should have single method argument.");
        }
        if (!acceptorMethod.getDeclaringClass().isAssignableFrom(acceptorMeta.getType())) {
            throw new IllegalArgumentException(
                    String.format("Acceptor method should be declared at class from '%s' hierarchy.",
                            acceptorMeta.getType()));
        }
    }

    private static class MappingNode<T> {

        private final Map<Method, Method> mappings = new HashMap<>();
        private final TypeMeta<T> typeMeta;

        public MappingNode(TypeMeta<T> typeMeta) {
            this.typeMeta = typeMeta;
        }
    }
}
