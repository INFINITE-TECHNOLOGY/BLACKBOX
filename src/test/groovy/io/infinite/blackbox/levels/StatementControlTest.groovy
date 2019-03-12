package io.infinite.blackbox.levels

import io.infinite.carburetor.CarburetorTransformation

class StatementControlTest extends GroovyTestCase {

    void test() {
        assertScript("""
import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel


class Foo {

    void foo() {}

    @BlackBox(level = CarburetorLevel.STATEMENT)
    def test(String bar) {
        return bar
    }

}

new Foo().test()
""")
        assert CarburetorTransformation.lastCode == expectedCode
    }

    String expectedCode = """
blackBoxEngine.methodBegin(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(10, 13, 5, 6, 'Foo', 'test'), ['bar': bar ])
try {
    blackBoxEngine.executeMethod({ java.lang.Object itVariableReplacement0 ->
        blackBoxEngine.preprocessControlStatement(new io.infinite.supplies.ast.metadata.MetaDataStatement('ReturnStatement', 12, 12, 9, 19, 'test', 'Foo'))
        return bar 
    }, thisInstance)
} 
catch (java.lang.Exception automaticException) {
    blackBoxEngine.methodException(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(10, 13, 5, 6, 'Foo', 'test'), ['bar': bar ], automaticException)
    throw automaticException 
} 
finally { 
    blackBoxEngine.methodEnd(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(10, 13, 5, 6, 'Foo', 'test'))} 
"""

}