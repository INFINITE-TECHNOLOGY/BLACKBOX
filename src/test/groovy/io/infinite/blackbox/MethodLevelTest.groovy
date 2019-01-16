package io.infinite.blackbox


class MethodLevelTest extends GroovyTestCase {


    void test() {
        assertScript("""import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

class MethodLevel {

    @BlackBox(level = CarburetorLevel.METHOD)
    def test() {
        println "OK"
    }

}

new MethodLevel().test()
""")
    }


}