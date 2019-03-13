package io.infinite.blackbox.levels

import io.infinite.blackbox.BlackBoxTestCase
import io.infinite.carburetor.CarburetorTransformation

class ErrorTest extends BlackBoxTestCase {

    void test() {
        assertScript("""
import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel


class Foo {

    @BlackBox(level = CarburetorLevel.ERROR)
    def test(String bar) {
        bar
    }

}

new Foo().test()
""")
        assert CarburetorTransformation.lastCode == expectedCode
    }

    String expectedCode = """
try {
    bar 
} 
catch (java.lang.Exception automaticException) {
    blackBoxEngine.methodException(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(8, 11, 5, 6, 'test', 'Foo'), ['bar': bar ], automaticException)
    throw automaticException 
} 
finally { 
} 
"""


}