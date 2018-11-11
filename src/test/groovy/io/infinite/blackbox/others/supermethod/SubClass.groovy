package io.infinite.blackbox.others.supermethod

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

class SubClass extends SuperClass implements Runnable {

    @BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
    String bar(String bar) {
        super.bar("bar")
    }

    @Override
    void run() {
        bar()
    }
}
