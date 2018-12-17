package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

class DelegateTest {

    @BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
    void testBlackBox(Object iThis) {
        Closure c = {
            assert delegate == this
            assert owner == this
            assert thisObject == this
            assert delegate == iThis
            assert owner == iThis
            assert thisObject == iThis
            assert iThis == this
            assert iThis == this
            assert iThis == this
        }
        c()
    }

    void test() {
        testBlackBox(this)
    }

}
