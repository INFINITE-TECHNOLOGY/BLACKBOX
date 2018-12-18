package io.infinite.blackbox.others.superconstructor

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

class Bar extends Foo {

    @BlackBox(level = CarburetorLevel.EXPRESSION)
    Bar(String foo) {
        super("test")
        def z = "q"
        System.out.println("test")
    }

}
