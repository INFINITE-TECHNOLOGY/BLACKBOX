package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

class ErrorStrategies {

    @BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION, suppressExceptions = true)
    void test() {
        test2()
    }

    @BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
    void test2() {
        test3()
    }

    @BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
    void test3() {
        throw new Exception("Test exception")
    }

}
