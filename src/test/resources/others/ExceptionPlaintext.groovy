package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

class ExceptionPlaintext {

    @BlackBox(blackBoxLevel = BlackBoxLevel.ERROR, suppressExceptions = true)
    void test() {
        throw new Exception("Test throwable")
    }

}
