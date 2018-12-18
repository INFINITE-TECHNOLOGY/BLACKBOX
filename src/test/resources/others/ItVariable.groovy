package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

class ItVariable implements Runnable{

    @BlackBox(level = CarburetorLevel.EXPRESSION)
    void test() {
        Object[] objects = [1,2,3,4,5]
        objects.each {
            assert it != null
        }
    }

    @Override
    void run() {
        test()
    }
}
