package tests

import groovy.transform.CompileStatic
import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.ERROR)
@CompileStatic
abstract class Foo {

    abstract void foo()

}

@BlackBox(level = CarburetorLevel.ERROR)
@CompileStatic
class Bar extends Foo {

    @Override
    void foo() {

    }

}

new Bar()