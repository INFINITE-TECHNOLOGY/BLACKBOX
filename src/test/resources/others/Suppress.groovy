package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

public class Suppress {

    void exception() {
        throw new Exception("This is test exception")
    }

    @BlackBox(level = CarburetorLevel.EXPRESSION, suppressExceptions = true)
    void test() {
        exception()
    }

}
