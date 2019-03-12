package io.infinite.blackbox.levels

import io.infinite.carburetor.CarburetorTransformation

class ExpressionTest extends GroovyTestCase {

    void test() {
        assertScript("""
import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel


class Foo {

    @BlackBox(level = CarburetorLevel.EXPRESSION)
    void test(String bar) {
        def foo
    }

}

new Foo().test()
""")
        assert CarburetorTransformation.lastCode == expectedCode
    }

    String expectedCode = """
blackBoxEngine.methodBegin(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(8, 11, 5, 6, 'Foo', 'test'), ['bar': bar ])
try {
    blackBoxEngine.expressionBegin(new io.infinite.supplies.ast.metadata.MetaDataExpression('DeclarationExpression', 'java.lang.Object foo ', 10, 10, 9, 16, 'test', 'Foo'))java.lang.Object foo blackBoxEngine.expressionEnd(new io.infinite.supplies.ast.metadata.MetaDataExpression('DeclarationExpression', 'java.lang.Object foo ', 10, 10, 9, 16, 'test', 'Foo'))
} 
catch (java.lang.Exception automaticException) {
    blackBoxEngine.methodException(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(8, 11, 5, 6, 'Foo', 'test'), ['bar': bar ], automaticException)
    throw automaticException 
} 
finally { 
    blackBoxEngine.methodEnd(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(8, 11, 5, 6, 'Foo', 'test'))} 
"""


}