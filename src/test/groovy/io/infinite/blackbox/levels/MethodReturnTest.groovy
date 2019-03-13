package io.infinite.blackbox.levels

import io.infinite.blackbox.BlackBoxTestCase
import io.infinite.carburetor.CarburetorTransformation

class MethodReturnTest extends BlackBoxTestCase {

    void test() {
        assertScript("""
import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel


class Foo {

    @BlackBox(level = CarburetorLevel.METHOD)
    def test(String bar) {
        bar
    }

}

new Foo().test()
""")
        assert CarburetorTransformation.lastCode == expectedCode
    }

    String expectedCode = """
blackBoxEngine.methodBegin(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(8, 11, 5, 6, 'test', 'Foo'), ['bar': bar ])
try {
    blackBoxEngine.executeMethod({ java.lang.Object itVariableReplacement0 ->
        bar 
    }, thisInstance)
} 
catch (java.lang.Exception automaticException) {
    blackBoxEngine.methodException(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(8, 11, 5, 6, 'test', 'Foo'), ['bar': bar ], automaticException)
    throw automaticException 
} 
finally { 
    blackBoxEngine.methodEnd(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(8, 11, 5, 6, 'test', 'Foo'))} 
"""


}