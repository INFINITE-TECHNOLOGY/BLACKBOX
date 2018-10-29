package io.infinite.blackbox.issues

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

class Issue001 {

    @BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
    void test() {
        Object[] objects = [1,2,3,4,5]
        objects.each {
            assert it != null
        }
    }

}
