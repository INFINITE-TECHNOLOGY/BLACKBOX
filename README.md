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
compile "io.infinite:blackbox:1.0.15"
```

## Try it now!

> ❗ Requires Groovy 2.5.4

Just simply run the below code in Groovy console:

```groovy
@Grab('io.infinite:blackbox:1.0.15')
@Grab('io.infinite:bobbin:2.0.3')

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

Result:

```
2019-03-14 15:42:30:493|debug|Thread-3|ConsoleScript0|METHOD: ConsoleScript0.foobar(7,1,12,2)
2019-03-14 15:42:30:500|debug|Thread-3|ConsoleScript0|EXPRESSION BEGIN: ConsoleScript0.foobar(DeclarationExpression:9,5,9,23) - java.lang.String bar = 'bar'
2019-03-14 15:42:30:501|debug|Thread-3|ConsoleScript0|EXPRESSION BEGIN: ConsoleScript0.foobar(ConstantExpression:9,18,9,23) - 'bar'
2019-03-14 15:42:30:501|debug|Thread-3|ConsoleScript0|EXPRESSION VALUE (value class=java.lang.String; ConsoleScript0.foobar(ConstantExpression:9,18,9,23)):
2019-03-14 15:42:30:502|debug|Thread-3|ConsoleScript0|'bar' = bar
2019-03-14 15:42:30:502|debug|Thread-3|ConsoleScript0|EXPRESSION END: ConsoleScript0.foobar(ConstantExpression:9,18,9,23) - 'bar'
2019-03-14 15:42:30:504|debug|Thread-3|ConsoleScript0|EXPRESSION END: ConsoleScript0.foobar(DeclarationExpression:9,5,9,23) - java.lang.String bar = 'bar'
2019-03-14 15:42:30:505|debug|Thread-3|ConsoleScript0|EXPRESSION BEGIN: ConsoleScript0.foobar(DeclarationExpression:10,5,10,30) - java.lang.String foobar = foo + bar 
2019-03-14 15:42:30:506|debug|Thread-3|ConsoleScript0|EXPRESSION BEGIN: ConsoleScript0.foobar(BinaryExpression:10,25,10,26) - foo + bar 
2019-03-14 15:42:30:507|debug|Thread-3|ConsoleScript0|EXPRESSION BEGIN: ConsoleScript0.foobar(VariableExpression:10,21,10,24) - foo 
2019-03-14 15:42:30:507|debug|Thread-3|ConsoleScript0|EXPRESSION VALUE (value class=java.lang.String; ConsoleScript0.foobar(VariableExpression:10,21,10,24)):
2019-03-14 15:42:30:507|debug|Thread-3|ConsoleScript0|foo  = foo
2019-03-14 15:42:30:507|debug|Thread-3|ConsoleScript0|EXPRESSION END: ConsoleScript0.foobar(VariableExpression:10,21,10,24) - foo 
2019-03-14 15:42:30:508|debug|Thread-3|ConsoleScript0|EXPRESSION BEGIN: ConsoleScript0.foobar(VariableExpression:10,27,10,30) - bar 
2019-03-14 15:42:30:508|debug|Thread-3|ConsoleScript0|EXPRESSION VALUE (value class=java.lang.String; ConsoleScript0.foobar(VariableExpression:10,27,10,30)):
2019-03-14 15:42:30:508|debug|Thread-3|ConsoleScript0|bar  = bar
2019-03-14 15:42:30:508|debug|Thread-3|ConsoleScript0|EXPRESSION END: ConsoleScript0.foobar(VariableExpression:10,27,10,30) - bar 
2019-03-14 15:42:30:508|debug|Thread-3|ConsoleScript0|EXPRESSION VALUE (value class=java.lang.String; ConsoleScript0.foobar(BinaryExpression:10,25,10,26)):
2019-03-14 15:42:30:508|debug|Thread-3|ConsoleScript0|foo + bar  = foobar
2019-03-14 15:42:30:508|debug|Thread-3|ConsoleScript0|EXPRESSION END: ConsoleScript0.foobar(BinaryExpression:10,25,10,26) - foo + bar 
2019-03-14 15:42:30:509|debug|Thread-3|ConsoleScript0|EXPRESSION END: ConsoleScript0.foobar(DeclarationExpression:10,5,10,30) - java.lang.String foobar = foo + bar 
2019-03-14 15:42:30:513|debug|Thread-3|ConsoleScript0|STATEMENT BEGIN: ConsoleScript0.foobar(ReturnStatement:11,5,11,18)
2019-03-14 15:42:30:513|debug|Thread-3|ConsoleScript0|STATEMENT END: ConsoleScript0.foobar(ReturnStatement:11,5,11,18)
2019-03-14 15:42:30:513|debug|Thread-3|ConsoleScript0|CONTROL STATEMENT: ReturnStatement
2019-03-14 15:42:30:514|debug|Thread-3|ConsoleScript0|EXPRESSION BEGIN: ConsoleScript0.foobar(VariableExpression:11,12,11,18) - foobar 
2019-03-14 15:42:30:514|debug|Thread-3|ConsoleScript0|EXPRESSION VALUE (value class=java.lang.String; ConsoleScript0.foobar(VariableExpression:11,12,11,18)):
2019-03-14 15:42:30:514|debug|Thread-3|ConsoleScript0|foobar  = foobar
2019-03-14 15:42:30:514|debug|Thread-3|ConsoleScript0|EXPRESSION END: ConsoleScript0.foobar(VariableExpression:11,12,11,18) - foobar 
2019-03-14 15:42:30:514|debug|Thread-3|ConsoleScript0|METHOD RESULT:
2019-03-14 15:42:30:514|debug|Thread-3|ConsoleScript0|java.lang.String
2019-03-14 15:42:30:514|debug|Thread-3|ConsoleScript0|foobar
2019-03-14 15:42:30:515|debug|Thread-3|ConsoleScript0|METHOD END: ConsoleScript0.foobar(7,1,12,2)
Result: foobar
```