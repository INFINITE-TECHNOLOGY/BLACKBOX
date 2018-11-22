package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
class ClassAnnotation {

    void someMethod() {
        anotherMethod()
    }

    void anotherMethod() {

    }
}
