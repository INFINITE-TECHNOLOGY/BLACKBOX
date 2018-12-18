package io.infinite.blackbox.others.supermethod

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

class SubClass extends SuperClass implements Runnable {

    @BlackBox(level = CarburetorLevel.EXPRESSION)
    String bar(String bar) {
        super.bar("bar")
    }

    @Override
    void run() {
        bar()
    }
}
