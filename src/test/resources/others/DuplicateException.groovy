package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

public class DuplicateException {

    @BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
    void exception() {
        throw new Exception("This is test exception")
    }

    @BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
    void exception2() {
        exception()
    }

    void test() {
        try {
            exception2()
        } catch (Exception ignored) {

        }
    }

}
