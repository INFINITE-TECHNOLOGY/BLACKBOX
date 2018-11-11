package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

class ItVariable {

    @BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
    void test() {
        Object[] objects = [1,2,3,4,5]
        objects.each {
            assert it != null
        }
    }

}
