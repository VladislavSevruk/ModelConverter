/*
 * MIT License
 *
 * Copyright (c) 2020 Uladzislau Seuruk
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
package com.github.vladislavsevruk.converter.converter.parameterized.map;

import com.github.vladislavsevruk.converter.context.ConversionContext;
import com.github.vladislavsevruk.converter.converter.parameterized.AbstractParameterizedTypeConverter;
import com.github.vladislavsevruk.converter.exception.TypeConversionException;
import com.github.vladislavsevruk.converter.util.ClassUtil;
import com.github.vladislavsevruk.converter.util.InstanceCreationUtil;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import lombok.Value;
import lombok.extern.log4j.Log4j2;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Converts map to another map with component type casting if necessary.
 */
@Log4j2
public final class MapConverter extends AbstractParameterizedTypeConverter<Map<?, ?>> {

    public MapConverter(ConversionContext conversionContext) {
        super(conversionContext);
    }

    @Override
    protected boolean checkToType(Class<?> toType) {
        return getToType().isAssignableFrom(toType) || toType.isAssignableFrom(getToType());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map<?, ?> convertNonNullObject(Object from, TypeMeta<? extends Map<?, ?>> toMeta) {
        Map<?, ?> fromValue = (Map<?, ?>) from;
        Class<?> fromKeyType = ClassUtil.getCommonClass(fromValue.keySet());
        Class<?> fromValueType = ClassUtil.getCommonClass(fromValue.values());
        TypeMeta<?> toKeyTypeMeta = getKeyType(toMeta);
        TypeMeta<?> toValueTypeMeta = getValueType(toMeta);
        if (!getEngine().canConvert(fromKeyType, toKeyTypeMeta)) {
            log.warn(() -> String
                    .format("Cannot convert map key type '%s' to '%s'. Returning null.", fromKeyType.getName(),
                            toKeyTypeMeta.getType().getName()));
            return null;
        }
        if (!getEngine().canConvert(fromValueType, toValueTypeMeta)) {
            log.warn(() -> String
                    .format("Cannot convert map value type '%s' to '%s'. Returning null.", fromValueType.getName(),
                            toValueTypeMeta.getType().getName()));
            return null;
        }
        return fromValue.entrySet().stream().map(entry -> convertKeyValue(entry, toKeyTypeMeta, toValueTypeMeta))
                .collect(Collectors.toMap(KeyValuePair::getKey, KeyValuePair::getValue, throwingMerger(),
                        getTypeSupplier(toMeta.getType())));
    }

    @Override
    protected Class<?> getFromType() {
        return Map.class;
    }

    @Override
    protected Class<?> getToType() {
        return Map.class;
    }

    @Override
    protected void validateInput(Object from, TypeMeta<?> toMeta) {
        super.validateInput(from, toMeta);
        int innerTypesNumber = toMeta.getGenericTypes().length;
        if (innerTypesNumber != 0 && innerTypesNumber != 2) {
            String message = String
                    .format("Expected type meta size for target type: 0 or 2 but was: %d.", innerTypesNumber);
            throw new TypeConversionException(message);
        }
    }

    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); };
    }

    private KeyValuePair convertKeyValue(Entry<?, ?> entry, TypeMeta<?> toKeyTypeMeta, TypeMeta<?> toValueTypeMeta) {
        Object key = getEngine().convert(entry.getKey(), toKeyTypeMeta);
        Object value = getEngine().convert(entry.getValue(), toValueTypeMeta);
        return new KeyValuePair(key, value);
    }

    private TypeMeta<?> getKeyType(TypeMeta<?> typeMeta) {
        return typeMeta.getGenericTypes().length == 0 ? TypeMeta.OBJECT_META : typeMeta.getGenericTypes()[0];
    }

    private Supplier<Map> getTypeSupplier(Class<?> type) {
        if (type.isAssignableFrom(AbstractMap.class)) {
            return HashMap::new;
        }
        return () -> (Map) InstanceCreationUtil.createItem(type);
    }

    private TypeMeta<?> getValueType(TypeMeta<?> typeMeta) {
        return typeMeta.getGenericTypes().length == 0 ? TypeMeta.OBJECT_META : typeMeta.getGenericTypes()[1];
    }

    @Value
    private static class KeyValuePair {

        Object key;
        Object value;
    }
}
