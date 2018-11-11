package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

class ThisInner implements Runnable{

    class InnerClass {

        @BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
        String foo() {
            ThisInner this2 = ThisInner.this
            return this2
        }

    }

    @Override
    String toString() {
        return "Outer"
    }

    @Override
    void run() {
        System.out.println(new InnerClass().foo())
    }
}
