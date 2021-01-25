[![Build Status](https://travis-ci.org/VladislavSevruk/ModelConverter.svg?branch=develop)](https://travis-ci.com/VladislavSevruk/ModelConverter)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=VladislavSevruk_ModelConverter&metric=alert_status)](https://sonarcloud.io/dashboard?id=VladislavSevruk_ModelConverter)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=VladislavSevruk_ModelConverter&metric=coverage)](https://sonarcloud.io/component_measures?id=VladislavSevruk_ModelConverter&metric=coverage)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vladislavsevruk/model-converter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vladislavsevruk/model-converter)

# Java Model Converter
This utility library helps to convert from one POJO to another using public getters and setters via reflection mechanism.

## Table of contents
* [Getting started](#getting-started)
  * [Maven](#maven)
  * [Gradle](#gradle)
* [Usage](#usage)
  * [Non-parameterized classes](#non-parameterized-classes)
  * [Parameterized classes](#parameterized-classes)
* [Adding custom converters](#adding-custom-converters)
* [License](#license)

## Getting started
To add library to your project perform next steps:

### Maven
Add the following dependency to your pom.xml:
```xml
<dependency>
      <groupId>com.github.vladislavsevruk</groupId>
      <artifactId>model-converter</artifactId>
      <version>1.0.2</version>
</dependency>
```
### Gradle
Add the following dependency to your build.gradle:
```groovy
implementation 'com.github.vladislavsevruk:model-converter:1.0.2'
```

## Usage
ModelConverter uses POJO's getters and setters with matching names to get and set values from one POJO to another. 
Getters and setters name can be in both classic and fluent styles, e.g. getter with name ``getField`` can match 
setters with ``setField`` or ``field`` names (same setter with name ``setField`` can match getters with ``getField`` or 
``field`` names). If return type of getter doesn't match parameter type of setter it'll be converted to required type 
if any of predefined converters can perform such conversion. You can [add your custom converter](#adding-custom-converters) 
if you want to set custom conversion logic or override logic of existent one.

### Non-parameterized classes
Let's assume that we have following POJO:
```java
public class DonorModel {
    private Set<String> field1;
    private Integer field2;

    // getters and setters
}
```

and we want to convert it to following POJO:
```java
public class AcceptorModel {
    private List<String> field1;
    private String field2;

    // getters and setters
}
```

All you have to do is to use ``ModelConverter.convert(Object, Class<T>)`` method:
```kotlin
// creating POJO with values to convert
DonorModel donorModel = new DonorModel();
donorModel.setField1(Collections.singleton("specificValue"));
donorModel.setField2(2);
// converting POJO to another one
AcceptorModel acceptorModel = new ModelConverter().convert(donorModel, AcceptorModel.class);
// verifying field values
Assertions.assertEquals(1, acceptorModel.getField1().size());
Assertions.assertEquals("specificValue", acceptorModel.getField1().get(0));
Assertions.assertEquals("2", acceptorModel.getField2());
```

### Parameterized classes
Let's assume that we have following generic POJO:
```java
public class GenericDonorModel<T> {

    private Set<String> field1;
    private T field2;

    // getters and setters
}
```

and we want to convert it to following generic POJO:
```java
public class GenericAcceptorModel<T> {

    private List<String> field1;
    private T field2;

    // getters and setters
}
```

All you have to do is to use ``ModelConverter.convert(Object, TypeProvider<T>)`` method:
```kotlin
// creating POJO with values to convert
GenericDonorModel<Integer> donorModel = new GenericDonorModel<>();
donorModel.setField1(Collections.singleton("specificValue"));
donorModel.setField2(2);
// converting POJO to another one
GenericAcceptorModel<String> acceptorModel = new ModelConverter()
        .convert(donorModel, new TypeProvider<GenericAcceptorModel<String>>() {});
// verifying field values
Assertions.assertEquals(1, acceptorModel.getField1().size());
Assertions.assertEquals("specificValue", acceptorModel.getField1().get(0));
Assertions.assertEquals("2", acceptorModel.getField2());
```

## Adding custom converters
If you want to set custom conversion logic for any type or override logic of existent one you can implement 
[ClassTypeConverter](/src/main/java/com/github/vladislavsevruk/converter/converter/simple/ClassTypeConverter.java) for 
non-parameterized type or 
[ParameterizedTypeConverter](/src/main/java/com/github/vladislavsevruk/converter/converter/parameterized/ParameterizedTypeConverter.java) 
for parameterized type and add it to 
[TypeConverterStorage](/src/main/java/com/github/vladislavsevruk/converter/converter/storage/TypeConverterStorage.java) 
using one of ``add*`` methods:
```kotlin
ConversionContextManager.getContext().getTypeConverterStorage().add(new SomeCustomTypeConverter());
```

## License
This project is licensed under the MIT License, you can read the full text [here](LICENSE).