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
package com.github.vladislavsevruk.converter;

import com.github.vladislavsevruk.converter.context.ConversionContextManager;
import com.github.vladislavsevruk.converter.mapper.CustomGetterSetterMappingStorage;
import com.github.vladislavsevruk.converter.test.TestEnum;
import com.github.vladislavsevruk.converter.test.acceptor.AbstractElementSequenceAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.AcceptorSuperclassModel;
import com.github.vladislavsevruk.converter.test.acceptor.CustomMappingAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.ElementSequenceAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.FromArrayAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.FromDateAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.FromInstantAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.FromIterableAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.FromMapAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.FromNumberAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.FromStringAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.FromStringToBooleanAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.GenericTestAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.MethodPrefixesAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.NegativeAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.PrimitiveTypesAcceptorModel;
import com.github.vladislavsevruk.converter.test.acceptor.SeveralCandidatesAcceptorModel;
import com.github.vladislavsevruk.converter.test.donor.AbstractElementSequenceDonorModel;
import com.github.vladislavsevruk.converter.test.donor.CustomMappingDonorModel;
import com.github.vladislavsevruk.converter.test.donor.DonorSuperclassModel;
import com.github.vladislavsevruk.converter.test.donor.ElementSequenceDonorModel;
import com.github.vladislavsevruk.converter.test.donor.FromArrayDonorModel;
import com.github.vladislavsevruk.converter.test.donor.FromDateDonorModel;
import com.github.vladislavsevruk.converter.test.donor.FromInstantDonorModel;
import com.github.vladislavsevruk.converter.test.donor.FromIterableDonorModel;
import com.github.vladislavsevruk.converter.test.donor.FromMapDonorModel;
import com.github.vladislavsevruk.converter.test.donor.FromNumberDonorModel;
import com.github.vladislavsevruk.converter.test.donor.FromStringDonorModel;
import com.github.vladislavsevruk.converter.test.donor.FromStringToBooleanDonorModel;
import com.github.vladislavsevruk.converter.test.donor.GenericTestDonorModel;
import com.github.vladislavsevruk.converter.test.donor.MethodPrefixesDonorModel;
import com.github.vladislavsevruk.converter.test.donor.NegativeDonorModel;
import com.github.vladislavsevruk.converter.test.donor.PrimitiveTypesDonorModel;
import com.github.vladislavsevruk.converter.test.donor.SeveralCandidatesDonorModel;
import com.github.vladislavsevruk.resolver.type.TypeProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ModelConverterTest {

    private ModelConverter modelConverter = new ModelConverter();

    @Test
    void abstractElementSequenceModelTest() {
        AbstractElementSequenceDonorModel donor = new AbstractElementSequenceDonorModel();
        AbstractElementSequenceAcceptorModel result = modelConverter
                .convert(donor, AbstractElementSequenceAcceptorModel.class);
        Assertions.assertNotNull(result);
        assertIterable(Collections.singletonList("arrayToAbstractListMatchingType"),
                result.arrayToAbstractListMatchingType());
        assertIterable(Arrays.asList("1", "2"), result.arrayToAbstractListNonMatchingTypeWithConverter());
        assertIterable(Collections.singleton("arrayToAbstractSetMatchingType"),
                result.arrayToAbstractSetMatchingType());
        assertIterable(Stream.of("3", "4").collect(Collectors.toSet()),
                result.arrayToAbstractSetNonMatchingTypeWithConverter());
        assertIterable(Collections.singletonList("arrayToCollectionMatchingType"),
                result.arrayToCollectionMatchingType());
        assertIterable(Arrays.asList("5", "6"), result.arrayToCollectionNonMatchingTypeWithConverter());
        assertIterable(Collections.singletonList("arrayToIterableMatchingType"), result.arrayToIterableMatchingType());
        assertIterable(Arrays.asList("7", "8"), result.arrayToIterableNonMatchingTypeWithConverter());
        assertIterable(Collections.singleton("listToAbstractSetMatchingType"), result.listToAbstractSetMatchingType());
        assertIterable(Stream.of("9", "10").collect(Collectors.toSet()),
                result.listToAbstractSetNonMatchingTypeWithConverter());
        assertIterable(Arrays.asList("11", "12"), result.listToCollectionNonMatchingTypeWithConverter());
        assertIterable(Arrays.asList("13", "14"), result.listToIterableNonMatchingTypeWithConverter());
        assertIterable(Collections.singletonList("setToAbstractListMatchingType"),
                result.setToAbstractListMatchingType());
        assertIterable(Arrays.asList("15", "16"), result.setToAbstractListNonMatchingTypeWithConverter());
        Assertions.assertNull(result.arrayToAbstractListNonMatchingTypeWithoutConverter());
        Assertions.assertNull(result.arrayToAbstractSetNonMatchingTypeWithoutConverter());
        Assertions.assertNull(result.arrayToCollectionNonMatchingTypeWithoutConverter());
        Assertions.assertNull(result.arrayToIterableNonMatchingTypeWithoutConverter());
        Assertions.assertNull(result.listToAbstractSetNonMatchingTypeWithoutConverter());
        Assertions.assertNull(result.listToCollectionNonMatchingTypeWithoutConverter());
        Assertions.assertNull(result.listToIterableNonMatchingTypeWithoutConverter());
        Assertions.assertNull(result.setToAbstractListNonMatchingTypeWithoutConverter());
    }

    @Test
    void convertNullToClassTest() {
        PrimitiveTypesDonorModel result = modelConverter.convert(null, PrimitiveTypesDonorModel.class);
        Assertions.assertNull(result);
    }

    @Test
    void convertNullToProvidedClassTest() {
        List<String> result = modelConverter.convert(null, new TypeProvider<List<String>>() {});
        Assertions.assertNull(result);
    }

    @Test
    void convertSimpleTypeTest() {
        Long donor = 1L;
        String result = modelConverter.convert(donor, String.class);
        Assertions.assertEquals(donor.toString(), result);
    }

    @Test
    void convertWithCustomMappingsTest() throws Throwable {
        CustomGetterSetterMappingStorage storage = ConversionContextManager.getContext()
                .getCustomGetterSetterMappingStorage();
        storage.addGetterSetterMapping(CustomMappingDonorModel.class.getMethod("donorMatchingType"),
                AcceptorSuperclassModel.class.getMethod("acceptorSuperclassMatchingType", String.class));
        storage.addGetterSetterMapping(CustomMappingDonorModel.class.getMethod("donorNonMatchingType"),
                AcceptorSuperclassModel.class.getMethod("acceptorSuperclassNonMatchingType", Long.class),
                new TypeProvider<AcceptorSuperclassModel>() {});
        storage.addGetterSetterMapping(DonorSuperclassModel.class.getMethod("donorSuperclassMatchingType"),
                CustomMappingAcceptorModel.class.getMethod("acceptorMatchingType", String.class));
        storage.addGetterSetterMapping(DonorSuperclassModel.class.getMethod("donorSuperclassNonMatchingType"),
                CustomMappingAcceptorModel.class.getMethod("acceptorNonMatchingType", Long.class));
        CustomMappingDonorModel donorModel = new CustomMappingDonorModel();
        CustomMappingAcceptorModel result = modelConverter.convert(donorModel, CustomMappingAcceptorModel.class);
        Assertions.assertNotNull(result);
        Assertions.assertNull(result.unexpectedIndicator());
        Assertions.assertEquals(donorModel.donorSuperclassMatchingType(), result.acceptorMatchingType());
        Assertions.assertEquals(Long.valueOf(donorModel.donorSuperclassNonMatchingType()),
                result.acceptorNonMatchingType());
        Assertions.assertEquals(donorModel.donorMatchingType(), result.acceptorSuperclassMatchingType());
        Assertions.assertEquals(Long.valueOf(donorModel.donorNonMatchingType()),
                result.acceptorSuperclassNonMatchingType());
    }

    @Test
    void elementSequenceModelTest() {
        ElementSequenceDonorModel donor = new ElementSequenceDonorModel();
        ElementSequenceAcceptorModel result = modelConverter.convert(donor, ElementSequenceAcceptorModel.class);
        Assertions.assertNotNull(result);
        assertArray(new String[]{ "listToArrayMatchingType" }, result.listToArrayMatchingType());
        assertArray(new String[]{ "5" }, result.listToArrayNonMatchingTypeWithConverter());
        assertArray(new String[]{ "setToArrayMatchingType" }, result.setToArrayMatchingType());
        assertArray(new String[]{ "9" }, result.setToArrayNonMatchingTypeWithConverter());
        assertIterable(Stream.of("arrayToArrayListMatchingType").collect(Collectors.toCollection(ArrayList::new)),
                result.arrayToArrayListMatchingType());
        assertIterable(
                Stream.of("arrayToLinkedHashSetMatchingType").collect(Collectors.toCollection(LinkedHashSet::new)),
                result.arrayToLinkedHashSetMatchingType());
        assertIterable(
                Stream.of("listToLinkedHashSetMatchingType").collect(Collectors.toCollection(LinkedHashSet::new)),
                result.listToLinkedHashSetMatchingType());
        assertIterable(Stream.of("setToArrayListMatchingType").collect(Collectors.toCollection(ArrayList::new)),
                result.setToArrayListMatchingType());
        assertIterable(Collections.singletonList("arrayToListMatchingType"), result.arrayToListMatchingType());
        assertIterable(Collections.singletonList("1"), result.arrayToListNonMatchingTypeWithConverter());
        assertIterable(Collections.singletonList("setToListMatchingType"), result.setToListMatchingType());
        assertIterable(Collections.singletonList("11"), result.setToListNonMatchingTypeWithConverter());
        assertIterable(Collections.singleton("arrayToSetMatchingType"), result.arrayToSetMatchingType());
        assertIterable(Collections.singleton("3"), result.arrayToSetNonMatchingTypeWithConverter());
        assertIterable(Collections.singleton("listToSetMatchingType"), result.listToSetMatchingType());
        assertIterable(Collections.singleton("7"), result.listToSetNonMatchingTypeWithConverter());
        Assertions.assertNull(result.arrayToListNonMatchingTypeWithoutConverter());
        Assertions.assertNull(result.arrayToSetNonMatchingTypeWithoutConverter());
        Assertions.assertNull(result.listToArrayNonMatchingTypeWithoutConverter());
        Assertions.assertNull(result.listToSetNonMatchingTypeWithoutConverter());
        Assertions.assertNull(result.setToArrayNonMatchingTypeWithoutConverter());
        Assertions.assertNull(result.setToListNonMatchingTypeWithoutConverter());
    }

    @Test
    void fromArrayModelTest() {
        FromArrayDonorModel donor = new FromArrayDonorModel();
        FromArrayAcceptorModel result = modelConverter.convert(donor, FromArrayAcceptorModel.class);
        Assertions.assertNotNull(result);
        assertArray(donor.arrayMatchingBoundedWildcardTypeAcceptor(),
                result.arrayMatchingBoundedWildcardTypeAcceptor());
        assertArray(donor.arrayMatchingType(), result.arrayMatchingType());
        assertArray(donor.arrayMatchingWildcardType(), result.arrayMatchingWildcardType());
        assertArray(donor.arrayMatchingWildcardTypeAcceptor(), result.arrayMatchingWildcardTypeAcceptor());
        assertArray(new List[]{ Collections.singletonList(4), Collections.singletonList(5) },
                result.arrayMismatchingBoundedWildcardTypeDonorPos());
        assertArray(new Short[]{ 5, 6 }, result.arrayMismatchingTypeWithKnownConverter());
        assertArray(new List[]{ Collections.singletonList(null) },
                result.arrayMismatchingBoundedWildcardTypeAcceptor());
        assertArray(new List[]{ Collections.singletonList(2), Collections.singletonList(3) },
                result.arrayMismatchingBoundedWildcardTypeDonorNeg());
        Assertions.assertNull(result.arrayMismatchingTypeWithoutKnownConverter());
        assertArray(new List[]{ Collections.singletonList(8.8), Collections.singletonList(null) },
                result.arrayMismatchingWildcardTypeDonorNeg());
    }

    @Test
    void fromDateModelTest() {
        FromDateDonorModel donor = new FromDateDonorModel();
        FromDateAcceptorModel result = modelConverter.convert(donor, FromDateAcceptorModel.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals((byte) donor.toByte().getTime(), result.toByte());
        Assertions.assertEquals((double) donor.toDouble().getTime(), result.toDouble());
        Assertions.assertEquals((float) donor.toFloat().getTime(), result.toFloat());
        Assertions.assertEquals(donor.toInstant().toInstant(), result.toInstant());
        Assertions.assertEquals((int) donor.toInteger().getTime(), result.toInteger());
        Assertions.assertEquals(donor.toLong().getTime(), result.toLong());
        Assertions.assertEquals((short) donor.toShort().getTime(), result.toShort());
    }

    @Test
    void fromInstantModelTest() {
        FromInstantDonorModel donor = new FromInstantDonorModel();
        FromInstantAcceptorModel result = modelConverter.convert(donor, FromInstantAcceptorModel.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals((byte) donor.toByte().toEpochMilli(), result.toByte());
        Assertions.assertEquals(Date.from(donor.toDate()), result.toDate());
        Assertions.assertEquals((double) donor.toDouble().toEpochMilli(), result.toDouble());
        Assertions.assertEquals((float) donor.toFloat().toEpochMilli(), result.toFloat());
        Assertions.assertEquals((int) donor.toInteger().toEpochMilli(), result.toInteger());
        Assertions.assertEquals(donor.toLong().toEpochMilli(), result.toLong());
        Assertions.assertEquals((short) donor.toShort().toEpochMilli(), result.toShort());
    }

    @Test
    void fromIterableModelTest() {
        FromIterableDonorModel donor = new FromIterableDonorModel();
        FromIterableAcceptorModel result = modelConverter.convert(donor, FromIterableAcceptorModel.class);
        Assertions.assertNotNull(result);
        assertIterable(donor.listMatchingParameterType(), result.listMatchingParameterType());
        assertIterable(donor.listMatchingWildcardType(), result.listMatchingWildcardType());
        assertIterable(new LinkedList<>(donor.listMismatchingDescendantsMatchingTypes()),
                result.listMismatchingDescendantsMatchingTypes());
        assertIterable(Stream.of(5L, null).collect(Collectors.toCollection(LinkedList::new)),
                result.listMismatchingDescendantsMismatchingTypesWithKnownConverter());
        Assertions.assertNull(result.listMismatchingDescendantsMismatchingTypesWithoutKnownConverter());
        assertIterable(Stream.of(null, 6).collect(Collectors.toList()),
                result.listMismatchingParameterTypeWithKnownConverter());
        Assertions.assertNull(result.listMismatchingParameterTypeWithoutKnownConverter());
        assertIterable(donor.listMismatchingWildcardTypeAcceptor(), result.listMismatchingWildcardTypeAcceptor());
        Assertions.assertNull(result.listMismatchingWildcardTypeDonor());
        assertIterable(donor.setMatchingParameterType(), result.setMatchingParameterType());
        assertIterable(donor.setMatchingWildcardType(), result.setMatchingWildcardType());
        assertIterable(new HashSet<>(donor.setMismatchingDescendantsMatchingTypes()),
                result.setMismatchingDescendantsMatchingTypes());
        assertIterable(Stream.of(null, 10.10f).collect(Collectors.toCollection(HashSet::new)),
                result.setMismatchingDescendantsMismatchingTypesWithKnownConverter());
        Assertions.assertNull(result.setMismatchingDescendantsMismatchingTypesWithoutKnownConverter());
        assertIterable(Stream.of(true, false).collect(Collectors.toCollection(LinkedHashSet::new)),
                result.setMismatchingParameterTypeWithKnownConverter());
        Assertions.assertNull(result.setMismatchingParameterTypeWithoutKnownConverter());
        assertIterable(donor.setMismatchingWildcardTypeAcceptor(), result.setMismatchingWildcardTypeAcceptor());
        Assertions.assertNull(result.setMismatchingWildcardTypeDonor());
    }

    @Test
    void fromMapToBooleanModelTest() {
        FromMapDonorModel donor = new FromMapDonorModel();
        FromMapAcceptorModel result = modelConverter.convert(donor, FromMapAcceptorModel.class);
        Assertions.assertNotNull(result);
        assertMap(donor.toAbstractMapMatchingTypes(), result.toAbstractMapMatchingTypes());
        assertMap(donor.toHashMapMatchingTypes(), result.toHashMapMatchingTypes());
        assertMap(donor.toLinkedHashMapMatchingTypes(), result.toLinkedHashMapMatchingTypes());
        assertMap(donor.toMapMatchingTypes(), result.toMapMatchingTypes());
        assertMap(Collections.singletonMap("2", "value2"), result.toMapMismatchingKeyTypeWithConverter());
        Assertions.assertNull(result.toMapMismatchingKeyTypeWithoutConverter());
        assertMap(Collections.singletonMap("4", "5"), result.toMapMismatchingTypesWithConverter());
        Assertions.assertNull(result.toMapMismatchingTypesWithoutConverter());
        assertMap(Collections.singletonMap("key6", "6"), result.toMapMismatchingValueTypeWithConverter());
        Assertions.assertNull(result.toMapMismatchingValueTypeWithoutConverter());
    }

    @Test
    void fromNumberModelTest() {
        FromNumberDonorModel donor = new FromNumberDonorModel();
        FromNumberAcceptorModel result = modelConverter.convert(donor, FromNumberAcceptorModel.class);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.zeroToBoolean());
        Assertions.assertFalse(result.nonZeroToBoolean());
        Assertions.assertEquals(donor.toByte().byteValue(), result.toByte());
        Assertions.assertEquals(new Date(donor.toDate()), result.toDate());
        Assertions.assertEquals((char) donor.toInteger().intValue(), result.toChar());
        Assertions.assertEquals(donor.toDouble().doubleValue(), result.toDouble());
        Assertions.assertEquals(donor.toFloat().floatValue(), result.toFloat());
        Assertions.assertEquals(Instant.ofEpochMilli(donor.toInstant()), result.toInstant());
        Assertions.assertEquals(donor.toInteger().intValue(), result.toInteger());
        Assertions.assertEquals(donor.toLong().longValue(), result.toLong());
        Assertions.assertEquals(donor.toShort().shortValue(), result.toShort());
        Assertions.assertEquals(donor.toStringVal().toString(), result.toStringVal());
    }

    @Test
    void fromStringModelTest() {
        FromStringDonorModel donor = new FromStringDonorModel();
        FromStringAcceptorModel result = modelConverter.convert(donor, FromStringAcceptorModel.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Byte.valueOf(donor.toBytePos()), result.toBytePos());
        Assertions.assertEquals(Double.valueOf(donor.toDoublePos()), result.toDoublePos());
        Assertions.assertEquals(TestEnum.valueOf(donor.toEnumPos()), result.toEnumPos());
        Assertions.assertEquals(Float.valueOf(donor.toFloatPos()), result.toFloatPos());
        Assertions.assertEquals(Integer.valueOf(donor.toIntegerPos()), result.toIntegerPos());
        Assertions.assertEquals(Long.valueOf(donor.toLongPos()), result.toLongPos());
        Assertions.assertEquals(Short.valueOf(donor.toShortPos()), result.toShortPos());
        Assertions.assertEquals('1', result.toCharPos());
        Assertions.assertNull(result.toByteNeg());
        Assertions.assertNull(result.toDoubleNeg());
        Assertions.assertNull(result.toEnumNeg());
        Assertions.assertNull(result.toFloatNeg());
        Assertions.assertNull(result.toIntegerNeg());
        Assertions.assertNull(result.toLongNeg());
        Assertions.assertNull(result.toShortNeg());
        Assertions.assertNull(result.toCharNeg());
    }

    @Test
    void fromStringToBooleanModelTest() {
        FromStringToBooleanDonorModel donor = new FromStringToBooleanDonorModel();
        FromStringToBooleanAcceptorModel result = modelConverter.convert(donor, FromStringToBooleanAcceptorModel.class);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.smallY());
        Assertions.assertTrue(result.greatY());
        Assertions.assertTrue(result.smallYes());
        Assertions.assertTrue(result.greatYes());
        Assertions.assertTrue(result.smallT());
        Assertions.assertTrue(result.greatT());
        Assertions.assertTrue(result.smallTrue());
        Assertions.assertTrue(result.greatTrue());
        Assertions.assertTrue(result.one());
        Assertions.assertFalse(result.smallN());
        Assertions.assertFalse(result.greatN());
        Assertions.assertFalse(result.smallNo());
        Assertions.assertFalse(result.greatNo());
        Assertions.assertFalse(result.smallF());
        Assertions.assertFalse(result.greatF());
        Assertions.assertFalse(result.smallFalse());
        Assertions.assertFalse(result.greatFalse());
        Assertions.assertFalse(result.zero());
        Assertions.assertNull(result.negVal());
    }

    @Test
    @SuppressWarnings("unchecked")
    void genericModelMatchingTypesTest() {
        GenericTestDonorModel<String, String> donor = new GenericTestDonorModel<>();
        donor.genericParameter("genericParameter");
        donor.genericArray(new String[]{ "genericArray" });
        donor.genericMap(Collections.singletonMap("genericMapKey", "genericMapValue"));
        donor.genericMapArray(
                new Map[]{ Collections.singletonMap("key1", "value1"), Collections.singletonMap("key2", "value2") });
        donor.innerGenericType(Collections.singleton(Collections.singletonList("innerGenericType")));
        GenericTestAcceptorModel<String, String> result = modelConverter
                .convert(donor, new TypeProvider<GenericTestAcceptorModel<String, String>>() {});
        Assertions.assertNotNull(result);
        Assertions.assertEquals(donor.genericParameter(), result.genericParameter());
        assertArray(donor.genericArray(), result.genericArray());
        assertMap(donor.genericMap(), result.genericMap());
        assertArray(donor.genericMapArray(), result.genericMapArray());
        assertIterable(donor.innerGenericType(), result.innerGenericType());
    }

    @Test
    @SuppressWarnings("unchecked")
    void genericModelMismatchingTypesWithConverterTest() {
        GenericTestDonorModel<Long, Long> donor = new GenericTestDonorModel<>();
        donor.genericParameter(1L);
        donor.genericArray(new Long[]{ 2L });
        donor.genericMap(Collections.singletonMap(3L, 4L));
        donor.genericMapArray(new Map[]{ Collections.singletonMap(4L, 5L), Collections.singletonMap(5L, 6L) });
        donor.innerGenericType(Collections.singleton(Collections.singletonList(6L)));
        GenericTestAcceptorModel<String, String> result = modelConverter
                .convert(donor, new TypeProvider<GenericTestAcceptorModel<String, String>>() {});
        Assertions.assertNotNull(result);
        Assertions.assertEquals("1", result.genericParameter());
        assertArray(new String[]{ "2" }, result.genericArray());
        assertMap(Collections.singletonMap("3", "4"), result.genericMap());
        assertArray(new Map[]{ Collections.singletonMap("4", "5"), Collections.singletonMap("5", "6") },
                result.genericMapArray());
        assertIterable(Collections.singleton(Collections.singletonList("6")), result.innerGenericType());
    }

    @Test
    @SuppressWarnings("unchecked")
    void genericModelMismatchingTypesWithoutConverterTest() {
        GenericTestDonorModel<Character, Character> donor = new GenericTestDonorModel<>();
        donor.genericParameter('1');
        donor.genericArray(new Character[]{ '2' });
        donor.genericMap(Collections.singletonMap('3', '4'));
        donor.genericMapArray(new Map[]{ Collections.singletonMap('4', '5'), Collections.singletonMap('5', '6') });
        donor.innerGenericType(Collections.singleton(Collections.singletonList('6')));
        GenericTestAcceptorModel<Date, Date> result = modelConverter
                .convert(donor, new TypeProvider<GenericTestAcceptorModel<Date, Date>>() {});
        Assertions.assertNotNull(result);
        Assertions.assertNull(result.genericParameter());
        Assertions.assertNull(result.genericArray());
        Assertions.assertNull(result.genericMap());
        assertArray(new Map[]{ null, null }, result.genericMapArray());
        assertIterable(Collections.singleton(null), result.innerGenericType());
    }

    @Test
    void matchingTypesTest() {
        Integer donor = 1;
        Number result = modelConverter.convert(donor, Number.class);
        Assertions.assertEquals(donor, result);
    }

    @Test
    void methodsPrefixesTest() {
        MethodPrefixesDonorModel donor = new MethodPrefixesDonorModel();
        MethodPrefixesAcceptorModel result = modelConverter.convert(donor, MethodPrefixesAcceptorModel.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(donor.getDonorMethodWithPrefix(), result.getDonorMethodWithPrefix());
        Assertions.assertEquals(donor.acceptorMethodWithPrefix(), result.getAcceptorMethodWithPrefix());
        Assertions.assertEquals(donor.getMethodStartsWithDonorPrefix(), result.getMethodStartsWithDonorPrefix());
        Assertions.assertEquals(donor.setMethodStartsWithAcceptorPrefix(), result.getMethodStartsWithAcceptorPrefix());
        Assertions.assertEquals(donor.getMethodsWithPrefixes(), result.getMethodsWithPrefixes());
        Assertions.assertEquals(donor.methodsWithoutPrefixes(), result.getMethodsWithoutPrefixes());
    }

    @Test
    void noUnexpectedMethodCalledTest() {
        NegativeDonorModel donor = new NegativeDonorModel();
        NegativeAcceptorModel result = modelConverter.convert(donor, NegativeAcceptorModel.class);
        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getIndicator());
        Assertions.assertNull(NegativeAcceptorModel.getStaticIndicator());
    }

    @Test
    void primitiveMethodCalledTest() {
        PrimitiveTypesDonorModel donor = new PrimitiveTypesDonorModel();
        PrimitiveTypesAcceptorModel result = modelConverter.convert(donor, PrimitiveTypesAcceptorModel.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(donor.acceptorMatchingPrimitiveMethod(), result.acceptorMatchingPrimitiveMethod());
        Assertions.assertEquals(donor.acceptorNonMatchingPrimitiveMethod().shortValue(),
                result.acceptorNonMatchingPrimitiveMethod());
        Assertions.assertEquals(donor.donorMatchingPrimitiveMethod(), result.donorMatchingPrimitiveMethod());
        Assertions.assertEquals((short) donor.donorNonMatchingPrimitiveMethod(),
                result.donorNonMatchingPrimitiveMethod());
        Assertions.assertEquals(donor.primitiveMethod(), result.primitiveMethod());
        Assertions.assertEquals(donor.nonMatchingPrimitiveMethod(), result.nonMatchingPrimitiveMethod());
    }

    @Test
    void severalCandidateMethodTest() {
        SeveralCandidatesDonorModel donor = new SeveralCandidatesDonorModel();
        SeveralCandidatesAcceptorModel result = modelConverter.convert(donor, SeveralCandidatesAcceptorModel.class);
        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getUnexpectedIndicator());
        Assertions.assertNotNull(result.getExpectedIndicator1());
        Assertions.assertNotNull(result.getExpectedIndicator2());
    }

    private void assertArray(Object[] expected, Object[] actual) {
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; ++i) {
            Assertions.assertEquals(expected[i], actual[i]);
        }
    }

    private void assertArray(List[] expected, List[] actual) {
        Assertions.assertNotNull(actual);
        Assertions.assertNotEquals(0, actual.length);
        Assertions.assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; ++i) {
            assertIterable(expected[i], actual[i]);
        }
    }

    private void assertIterable(Iterable expected, Iterable actual) {
        Assertions.assertNotNull(actual);
        Iterator expectedIterator = expected.iterator();
        Iterator actualIterator = actual.iterator();
        while (expectedIterator.hasNext()) {
            Assertions.assertTrue(actualIterator.hasNext());
            Assertions.assertEquals(expectedIterator.next(), actualIterator.next());
        }
        Assertions.assertFalse(actualIterator.hasNext());
    }

    private void assertMap(Map expected, Map actual) {
        Assertions.assertNotNull(actual);
        Iterator expectedIterator = expected.entrySet().iterator();
        Iterator actualIterator = actual.entrySet().iterator();
        while (expectedIterator.hasNext()) {
            Assertions.assertTrue(actualIterator.hasNext());
            Assertions.assertEquals(expectedIterator.next(), actualIterator.next());
        }
        Assertions.assertFalse(actualIterator.hasNext());
    }
}
