package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBoxTestCase


class MethodErrorTest extends BlackBoxTestCase {


    void test() {
        def testInstance = new GroovyClassLoader().parseClass("""
package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

class MethodLevel {

    @BlackBox(level = CarburetorLevel.METHOD)
    def test(String arg1) {
        println "Before exception"
        throw new Exception("Method threw an exception here")
    }
    
    @BlackBox(level = CarburetorLevel.METHOD, suppressExceptions = true)
    def test2(String arg1) {
        test(arg1)
    }

}

new MethodLevel().test2("abcd")""").newInstance().run()
    }


}