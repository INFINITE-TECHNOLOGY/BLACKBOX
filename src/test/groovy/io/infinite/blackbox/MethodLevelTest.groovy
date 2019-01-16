package io.infinite.blackbox


class MethodLevelTest extends GroovyTestCase {


    void test() {
        assertScript("""import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

abstract class Foo extends Thread {

    @Override
    @BlackBox(level = CarburetorLevel.METHOD)
    void run() {
        println "OK"
    }

}

class Bar extends Foo {

    @Override
    @BlackBox
    void run() {
        println "OK"
        super.run()
    }

}

new Bar().run()
""")
    }


}