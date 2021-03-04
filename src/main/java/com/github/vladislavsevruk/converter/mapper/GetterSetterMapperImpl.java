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
import com.github.vladislavsevruk.converter.converter.simple.ClassTypeConverter;
import com.github.vladislavsevruk.converter.mapper.method.MappedMethod;
import com.github.vladislavsevruk.converter.mapper.method.MethodMeta;
import com.github.vladislavsevruk.converter.mapper.method.MethodMetaConverterPair;
import com.github.vladislavsevruk.converter.util.ClassUtil;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import com.github.vladislavsevruk.resolver.util.PrimitiveWrapperUtil;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of <code>GetterSetterMapper</code>.
 *
 * @see GetterSetterMapper
 */
@Log4j2
public final class GetterSetterMapperImpl implements GetterSetterMapper {

    private static final String GETTER_PREFIX = "get";
    private static final String SETTER_PREFIX = "set";

    private final ConversionContext conversionContext;

    public GetterSetterMapperImpl(ConversionContext conversionContext) {
        this.conversionContext = conversionContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MappedMethod mapMatchingSetter(Method donorMethod, Object donorMethodValue, TypeMeta<?> acceptorMeta,
            List<Method> acceptorMethods) {
        log.debug("Trying to pick matching setter for '{}' donor method.", donorMethod.getName());
        MappedMethod mappedMethod = doMapMatchingSetter(donorMethod, donorMethodValue, acceptorMeta, acceptorMethods);
        if (Objects.nonNull(mappedMethod)) {
            log.debug(() -> {
                String messagePart = mappedMethod.getTypeConverter() == null ? ""
                        : " with type converter " + mappedMethod.getTypeConverter().getName();
                return String
                        .format("Mapped setter - '%s'%s.", mappedMethod.getAcceptorMethod().getName(), messagePart);
            });
        }
        return mappedMethod;
    }

    private MappedMethod doMapMatchingSetter(Method donorMethod, Object donorMethodValue, TypeMeta<?> acceptorMeta,
            List<Method> acceptorMethods) {
        MappedMethod mappedMethod = conversionContext.getCustomGetterSetterMappingStorage()
                .getMapping(donorMethod, donorMethodValue, acceptorMeta);
        if (Objects.nonNull(mappedMethod)) {
            log.debug("Found custom mapping for '{}' donor method and '{}' acceptor.", donorMethod.getName(),
                    acceptorMeta);
            return mappedMethod;
        }
        return mapMatchingSetterFromAcceptorMethods(donorMethod, donorMethodValue, acceptorMeta, acceptorMethods);
    }

    private MappedMethod getMappedPairForMatchingType(Object donorMethodValue, Method acceptorMethod,
            TypeMeta<?> acceptorParameterTypeMeta) {
        if (!PrimitiveWrapperUtil.wrap(acceptorParameterTypeMeta.getType())
                .isAssignableFrom(PrimitiveWrapperUtil.wrap(donorMethodValue.getClass()))) {
            return null;
        }
        if (ClassUtil.nonParameterized(acceptorParameterTypeMeta)) {
            return new MappedMethod(acceptorMethod);
        }
        ClassTypeConverter<?> converter = conversionContext.getTypeConverterPicker()
                .pickConverter(donorMethodValue.getClass(), acceptorParameterTypeMeta);
        if (Objects.nonNull(converter)) {
            return new MappedMethod(acceptorMethod, converter);
        }
        return null;
    }

    private MappedMethod getMappedPairForSameType(Object donorMethodValue, Method acceptorMethod,
            TypeMeta<?> acceptorParameterTypeMeta) {
        if (!acceptorParameterTypeMeta.getType().equals(donorMethodValue.getClass())) {
            return null;
        }
        if (ClassUtil.nonParameterized(acceptorParameterTypeMeta)) {
            return new MappedMethod(acceptorMethod);
        }
        ClassTypeConverter<?> converter = conversionContext.getTypeConverterPicker()
                .pickConverter(donorMethodValue.getClass(), acceptorParameterTypeMeta);
        if (Objects.nonNull(converter)) {
            return new MappedMethod(acceptorMethod, converter);
        }
        return null;
    }

    private TypeMeta<?> getParameterTypeMeta(TypeMeta<?> typeMeta, Method method) {
        return conversionContext.getExecutableTypeResolver().getParameterTypes(typeMeta, method).get(0);
    }

    private boolean isNamesMatch(String donorMethodName, String acceptorMethodName) {
        if (donorMethodName.equals(acceptorMethodName)) {
            return true;
        }
        String donorMethodNameWithoutPrefix = removePrefix(donorMethodName, GETTER_PREFIX);
        String acceptorMethodNameWithoutPrefix = removePrefix(acceptorMethodName, SETTER_PREFIX);
        return donorMethodNameWithoutPrefix.equals(acceptorMethodName) || donorMethodName
                .equals(acceptorMethodNameWithoutPrefix) || donorMethodNameWithoutPrefix
                .equals(acceptorMethodNameWithoutPrefix);
    }

    private MappedMethod mapMatchingSetterFromAcceptorMethods(Method donorMethod, Object donorMethodValue,
            TypeMeta<?> acceptorMeta, List<Method> acceptorMethods) {
        List<MethodMeta> candidates = new ArrayList<>();
        MappedMethod matchingTypeMethodPair = null;
        for (Method acceptorMethod : acceptorMethods) {
            if (!isNamesMatch(donorMethod.getName(), acceptorMethod.getName())) {
                continue;
            }
            TypeMeta<?> acceptorParameterTypeMeta = getParameterTypeMeta(acceptorMeta, acceptorMethod);
            MappedMethod mappedMethodPair = getMappedPairForSameType(donorMethodValue, acceptorMethod,
                    acceptorParameterTypeMeta);
            // if types are the same return method pair straight away
            if (Objects.nonNull(mappedMethodPair)) {
                return mappedMethodPair;
            }
            if (Objects.isNull(matchingTypeMethodPair)) {
                mappedMethodPair = getMappedPairForMatchingType(donorMethodValue, acceptorMethod,
                        acceptorParameterTypeMeta);
                // otherwise pick first found pair with matching types
                if (Objects.nonNull(mappedMethodPair)) {
                    matchingTypeMethodPair = mappedMethodPair;
                }
                candidates.add(new MethodMeta(acceptorMethod, acceptorParameterTypeMeta, null));
            }
        }
        return matchingTypeMethodPair != null ? matchingTypeMethodPair
                : pickFromCandidates(candidates, donorMethodValue);
    }

    private MappedMethod pickFromCandidates(List<MethodMeta> candidates, Object donorMethodValue) {
        MethodMetaConverterPair<?> metaConverterPair = conversionContext.getTypeConverterPicker()
                .pickConverter(donorMethodValue.getClass(), candidates);
        if (metaConverterPair == null) {
            return null;
        }
        Method pickedMethod = metaConverterPair.getMethodMeta().getMethod();
        return new MappedMethod(pickedMethod, metaConverterPair.getConverter());
    }

    private String removePrefix(String methodName, String prefix) {
        if (!methodName.startsWith(prefix)) {
            return methodName;
        }
        int prefixLength = prefix.length();
        return methodName.substring(prefixLength, prefixLength + 1).toLowerCase() + methodName
                .substring(prefixLength + 1);
    }
}
