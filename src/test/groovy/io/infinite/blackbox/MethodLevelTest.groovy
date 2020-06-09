package io.infinite.blackbox


class MethodLevelTest extends BlackBoxTestCase {


    static void main(String[] args) {
        assertScript("""import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

abstract class Foo extends Thread {

    @BlackBox(level = BlackBoxLevel.METHOD)
    String foo() {
        println "foo"
        return "foo"
    }

}

class Bar extends Foo {
    
    @BlackBox(level = BlackBoxLevel.METHOD)
    Bar() {
        super()
    }

    @BlackBox(level = BlackBoxLevel.METHOD)
    String bar() {
        return foo() + "bar"
    }

}

new Bar().bar()
""")
    }


}