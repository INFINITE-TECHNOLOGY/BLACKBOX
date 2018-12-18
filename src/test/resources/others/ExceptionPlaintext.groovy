package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

class ExceptionPlaintext {

    @BlackBox(level = CarburetorLevel.ERROR, suppressExceptions = true)
    void test() {
        throw new Exception("Test throwable")
    }

}
