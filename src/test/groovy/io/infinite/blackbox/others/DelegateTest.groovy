package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

class DelegateTest {


    @BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
    void test() {
        Closure c = {
            assert delegate == this
        }
        c()
    }

}
