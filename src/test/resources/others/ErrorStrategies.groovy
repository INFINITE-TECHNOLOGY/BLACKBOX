package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

class ErrorStrategies {

    @BlackBox(level = CarburetorLevel.EXPRESSION, suppressExceptions = true)
    void test() {
        test2()
    }

    @BlackBox(level = CarburetorLevel.EXPRESSION)
    void test2() {
        test3()
    }

    @BlackBox(level = CarburetorLevel.EXPRESSION)
    void test3() {
        throw new Exception("Test exception")
    }

}
