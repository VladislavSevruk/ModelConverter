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
package com.github.vladislavsevruk.converter.converter.storage;

import com.github.vladislavsevruk.converter.context.ConversionContext;
import com.github.vladislavsevruk.converter.converter.TypeConverter;
import com.github.vladislavsevruk.converter.converter.parameterized.ParameterizedTypeConverter;
import com.github.vladislavsevruk.converter.converter.parameterized.array.ArrayToArrayConverter;
import com.github.vladislavsevruk.converter.converter.parameterized.array.ArrayToListConverter;
import com.github.vladislavsevruk.converter.converter.parameterized.array.ArrayToSetConverter;
import com.github.vladislavsevruk.converter.converter.parameterized.iterable.IterableToArrayConverter;
import com.github.vladislavsevruk.converter.converter.parameterized.iterable.IterableToListConverter;
import com.github.vladislavsevruk.converter.converter.parameterized.iterable.IterableToSetConverter;
import com.github.vladislavsevruk.converter.converter.parameterized.map.MapConverter;
import com.github.vladislavsevruk.converter.converter.simple.ClassTypeConverter;
import com.github.vladislavsevruk.converter.converter.simple.date.DateToInstantConverter;
import com.github.vladislavsevruk.converter.converter.simple.date.number.DateToByteConverter;
import com.github.vladislavsevruk.converter.converter.simple.date.number.DateToDoubleConverter;
import com.github.vladislavsevruk.converter.converter.simple.date.number.DateToFloatConverter;
import com.github.vladislavsevruk.converter.converter.simple.date.number.DateToIntegerConverter;
import com.github.vladislavsevruk.converter.converter.simple.date.number.DateToLongConverter;
import com.github.vladislavsevruk.converter.converter.simple.date.number.DateToShortConverter;
import com.github.vladislavsevruk.converter.converter.simple.instant.InstantToDateConverter;
import com.github.vladislavsevruk.converter.converter.simple.instant.number.InstantToByteConverter;
import com.github.vladislavsevruk.converter.converter.simple.instant.number.InstantToDoubleConverter;
import com.github.vladislavsevruk.converter.converter.simple.instant.number.InstantToFloatConverter;
import com.github.vladislavsevruk.converter.converter.simple.instant.number.InstantToIntegerConverter;
import com.github.vladislavsevruk.converter.converter.simple.instant.number.InstantToLongConverter;
import com.github.vladislavsevruk.converter.converter.simple.instant.number.InstantToShortConverter;
import com.github.vladislavsevruk.converter.converter.simple.number.NumberToBooleanConverter;
import com.github.vladislavsevruk.converter.converter.simple.number.NumberToByteConverter;
import com.github.vladislavsevruk.converter.converter.simple.number.NumberToCharacterConverter;
import com.github.vladislavsevruk.converter.converter.simple.number.NumberToDateConverter;
import com.github.vladislavsevruk.converter.converter.simple.number.NumberToDoubleConverter;
import com.github.vladislavsevruk.converter.converter.simple.number.NumberToFloatConverter;
import com.github.vladislavsevruk.converter.converter.simple.number.NumberToInstantConverter;
import com.github.vladislavsevruk.converter.converter.simple.number.NumberToIntegerConverter;
import com.github.vladislavsevruk.converter.converter.simple.number.NumberToLongConverter;
import com.github.vladislavsevruk.converter.converter.simple.number.NumberToShortConverter;
import com.github.vladislavsevruk.converter.converter.simple.string.CharSequenceToBooleanConverter;
import com.github.vladislavsevruk.converter.converter.simple.string.CharSequenceToCharacterConverter;
import com.github.vladislavsevruk.converter.converter.simple.string.ObjectToStringConverter;
import com.github.vladislavsevruk.converter.converter.simple.string.number.CharSequenceToByteConverter;
import com.github.vladislavsevruk.converter.converter.simple.string.number.CharSequenceToDoubleConverter;
import com.github.vladislavsevruk.converter.converter.simple.string.number.CharSequenceToFloatConverter;
import com.github.vladislavsevruk.converter.converter.simple.string.number.CharSequenceToIntegerConverter;
import com.github.vladislavsevruk.converter.converter.simple.string.number.CharSequenceToLongConverter;
import com.github.vladislavsevruk.converter.converter.simple.string.number.CharSequenceToShortConverter;
import com.github.vladislavsevruk.converter.util.ClassUtil;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementation of <code>TypeConverterStorage</code>.
 *
 * @see TypeConverter
 * @see TypeConverterStorage
 */
@Log4j2
public final class TypeConverterStorageImpl implements TypeConverterStorage {

    private static final ReadWriteLock CONVERTERS_LOCK = new ReentrantReadWriteLock();
    private List<TypeConverter> converters = new LinkedList<>();
    private Set<TypeConverter> defaultConverters = new LinkedHashSet<>();

    /**
     * Sets up list of active converters and unmodifiable list of default converters.
     *
     * @param conversionContext <code>ConversionContext</code> to use.
     */
    public TypeConverterStorageImpl(ConversionContext conversionContext) {
        addDefaultConverters(conversionContext);
        converters.addAll(defaultConverters);
        defaultConverters = Collections.unmodifiableSet(defaultConverters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(ClassTypeConverter customConverter) {
        CONVERTERS_LOCK.writeLock().lock();
        add((TypeConverter) customConverter);
        CONVERTERS_LOCK.writeLock().unlock();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(ParameterizedTypeConverter customConverter) {
        CONVERTERS_LOCK.writeLock().lock();
        add((TypeConverter) customConverter);
        CONVERTERS_LOCK.writeLock().unlock();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAfter(ClassTypeConverter<?> customConverter, Class<? extends TypeConverter> targetType) {
        addAfter((TypeConverter) customConverter, targetType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAfter(ParameterizedTypeConverter<?> customConverter, Class<? extends TypeConverter> targetType) {
        addAfter((TypeConverter) customConverter, targetType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBefore(ClassTypeConverter<?> customConverter, Class<? extends TypeConverter> targetType) {
        addBefore((TypeConverter) customConverter, targetType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBefore(ParameterizedTypeConverter<?> customConverter, Class<? extends TypeConverter> targetType) {
        addBefore((TypeConverter) customConverter, targetType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TypeConverter> getAll() {
        CONVERTERS_LOCK.readLock().lock();
        List<TypeConverter> convertersList = converters.isEmpty() ? Collections.emptyList()
                : new ArrayList<>(converters);
        CONVERTERS_LOCK.readLock().unlock();
        return convertersList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDefaultConverter(TypeConverter typeConverter) {
        return defaultConverters.contains(typeConverter);
    }

    private void add(TypeConverter customConverter) {
        add(converters.size(), customConverter);
    }

    private void add(int index, TypeConverter customConverter) {
        if (customConverter == null) {
            log.info("Received converter is null so it will not be added.");
            return;
        }
        if (ClassUtil.getIndexOfType(converters, customConverter.getClass()) != -1) {
            log.info("Received converter is already present at list so it's copy will not be added.");
            return;
        }
        converters.add(index, customConverter);
    }

    private void addAfter(TypeConverter customConverter, Class<? extends TypeConverter> targetType) {
        CONVERTERS_LOCK.writeLock().lock();
        int targetTypeIndex = ClassUtil.getIndexOfType(converters, targetType);
        if (targetTypeIndex == -1) {
            log.info("Target type is not present at list, converter will be added to list end.");
            add(customConverter);
        } else {
            add(targetTypeIndex + 1, customConverter);
        }
        CONVERTERS_LOCK.writeLock().unlock();
    }

    private void addArrayConverters(ConversionContext conversionContext) {
        defaultConverters.add(new ArrayToListConverter(conversionContext));
        defaultConverters.add(new ArrayToSetConverter(conversionContext));
        defaultConverters.add(new ArrayToArrayConverter(conversionContext));
    }

    private void addBefore(TypeConverter customConverter, Class<? extends TypeConverter> targetType) {
        CONVERTERS_LOCK.writeLock().lock();
        int targetTypeIndex = ClassUtil.getIndexOfType(converters, targetType);
        if (targetTypeIndex == -1) {
            log.info("Target type is not present at list, converter will be added to list end.");
            add(customConverter);
        } else {
            add(targetTypeIndex, customConverter);
        }
        CONVERTERS_LOCK.writeLock().unlock();
    }

    private void addCharSequenceConverters() {
        defaultConverters.add(new ObjectToStringConverter());
        defaultConverters.add(new CharSequenceToDoubleConverter());
        defaultConverters.add(new CharSequenceToLongConverter());
        defaultConverters.add(new CharSequenceToFloatConverter());
        defaultConverters.add(new CharSequenceToIntegerConverter());
        defaultConverters.add(new CharSequenceToShortConverter());
        defaultConverters.add(new CharSequenceToByteConverter());
        defaultConverters.add(new CharSequenceToBooleanConverter());
        defaultConverters.add(new CharSequenceToCharacterConverter());
    }

    private void addDateConverters() {
        defaultConverters.add(new DateToInstantConverter());
        defaultConverters.add(new DateToDoubleConverter());
        defaultConverters.add(new DateToLongConverter());
        defaultConverters.add(new DateToFloatConverter());
        defaultConverters.add(new DateToIntegerConverter());
        defaultConverters.add(new DateToShortConverter());
        defaultConverters.add(new DateToByteConverter());
    }

    private void addDefaultConverters(ConversionContext conversionContext) {
        addCharSequenceConverters();
        addNumberConverters();
        addIterableConverters(conversionContext);
        defaultConverters.add(new MapConverter(conversionContext));
        addArrayConverters(conversionContext);
        addInstantConverters();
        addDateConverters();
    }

    private void addInstantConverters() {
        defaultConverters.add(new InstantToDateConverter());
        defaultConverters.add(new InstantToDoubleConverter());
        defaultConverters.add(new InstantToLongConverter());
        defaultConverters.add(new InstantToFloatConverter());
        defaultConverters.add(new InstantToIntegerConverter());
        defaultConverters.add(new InstantToShortConverter());
        defaultConverters.add(new InstantToByteConverter());
    }

    private void addIterableConverters(ConversionContext conversionContext) {
        defaultConverters.add(new IterableToListConverter(conversionContext));
        defaultConverters.add(new IterableToSetConverter(conversionContext));
        defaultConverters.add(new IterableToArrayConverter(conversionContext));
    }

    private void addNumberConverters() {
        defaultConverters.add(new NumberToBooleanConverter());
        defaultConverters.add(new NumberToByteConverter());
        defaultConverters.add(new NumberToCharacterConverter());
        defaultConverters.add(new NumberToDateConverter());
        defaultConverters.add(new NumberToDoubleConverter());
        defaultConverters.add(new NumberToFloatConverter());
        defaultConverters.add(new NumberToInstantConverter());
        defaultConverters.add(new NumberToIntegerConverter());
        defaultConverters.add(new NumberToLongConverter());
        defaultConverters.add(new NumberToShortConverter());
    }
}
