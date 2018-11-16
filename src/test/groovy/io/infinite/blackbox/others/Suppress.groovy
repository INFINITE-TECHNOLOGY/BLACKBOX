package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

public class Suppress {

    void exception() {
        throw new Exception("This is test exception")
    }

    @BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION, suppressExceptions = true)
    void test() {
        exception()
    }

}
