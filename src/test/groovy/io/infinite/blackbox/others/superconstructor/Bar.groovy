package io.infinite.blackbox.others.superconstructor

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

class Bar extends Foo {

    @BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
    Bar(String foo) {
        super("test")
        def z = "q"
        System.out.println("test")
    }

}
