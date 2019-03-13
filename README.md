# Infinite Logging ∞ BlackBox ⬛

|Attribute\Release type|Latest|Stable|
|----------------------|------|------|
|Version|2.0.0-SNAPSHOT|1.0.x|
|Branch|[master](https://github.com/INFINITE-TECHNOLOGY/BLACKBOX)|[BLACKBOX_1_0_X](https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/tree/BLACKBOX_1_0_X)|
|CI Build status|[![Build Status](https://travis-ci.com/INFINITE-TECHNOLOGY/BLACKBOX.svg?branch=master)](https://travis-ci.com/INFINITE-TECHNOLOGY/BLACKBOX)|[![Build Status](https://travis-ci.com/INFINITE-TECHNOLOGY/BLACKBOX.svg?branch=BLACKBOX_1_0_X)](https://travis-ci.com/INFINITE-TECHNOLOGY/BLACKBOX)|
|Test coverage|[![codecov](https://codecov.io/gh/INFINITE-TECHNOLOGY/BLACKBOX/branch/master/graphs/badge.svg)](https://codecov.io/gh/INFINITE-TECHNOLOGY/BLACKBOX/branch/master/graphs)|[![codecov](https://codecov.io/gh/INFINITE-TECHNOLOGY/BLACKBOX/branch/BLACKBOX_1_0_X/graphs/badge.svg)](https://codecov.io/gh/INFINITE-TECHNOLOGY/BLACKBOX/branch/BLACKBOX_1_0_X/graphs)|
|Library (Maven)|[oss.jfrog.org snapshot](https://oss.jfrog.org/artifactory/webapp/#/artifacts/browse/tree/General/oss-snapshot-local/io/infinite/blackbox/2.0.0-SNAPSHOT)|[ ![Download](https://api.bintray.com/packages/infinite-technology/m2/blackbox/images/download.svg) ](https://bintray.com/infinite-technology/m2/blackbox/_latestVersion)|

## Purpose
BlackBox is a solution to automatically generate Groovy Semantic logging code and inject it into User code during the Compilation stage resulting in a possibility to produce and review exhaustive application runtime data in a form of XML files with XSD model based on simplified Groovy AST class model - by the means of developing and using a BlackBox Annotation,Groovy Annotation.

## In short
BlackBox Annotation automatically injects a lot of logging code into user-defined Groovy methods/constructors without affecting the user program logic. +
Granularity of injected code can be defined by the user (programmer) up to:

* Method Exception handling transformation, Method Exceptions logging (exception and causing method arguments are logged)
* Method transformation, Method invocation logging (method arguments and result are logged)
* Statement transformation, Statement-level logging
* Expression transformation, Expression-level logging

## Documentation

* [BlackBox Documentation](https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki)

## Gradle

> ❗ Via **JCenter** repository

```groovy
compile "io.infinite:blackbox:1.0.13"
```

## Try it now!

> ❗ Requires Groovy 2.5.4

Just simply run the below code in Groovy console:

```groovy
@Grab('io.infinite:blackbox:1.0.13')

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.EXPRESSION)
String foobar(String foo) {
    String bar = "bar"
    String foobar = foo + bar
    return foobar
}

foobar("foo")
```
