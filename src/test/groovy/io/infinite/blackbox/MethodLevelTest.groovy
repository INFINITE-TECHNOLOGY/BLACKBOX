package io.infinite.blackbox


class MethodLevelTest extends BlackBoxTestCase {


    public static void main(String[] args) {
        assertScript("""import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

abstract class Foo extends Thread {

    @Override
    @BlackBox(level = BlackBoxLevel.METHOD)
    void run() {
        println "OK"
    }

}

class Bar extends Foo {

    @Override
    @BlackBox(level = BlackBoxLevel.METHOD)
    void run() {
        println "OK"
        super.run()
    }

}

new Bar().run()
""")
    }


}