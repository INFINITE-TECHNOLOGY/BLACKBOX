package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.EXPRESSION)
class ClassAnnotation {

    void someMethod() {
        anotherMethod()
    }

    void anotherMethod() {

    }
}
