package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

public class DuplicateException {

    @BlackBox(level = CarburetorLevel.ERROR)
    void exception() {
        throw new Exception("This is test exception")
    }

    @BlackBox(level = CarburetorLevel.ERROR)
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
