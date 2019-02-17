package io.infinite.blackbox.levels

import io.infinite.carburetor.CarburetorTransformation

class StatementTest extends GroovyTestCase {

    void test() {
        assertScript("""
import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel


class Foo {

    void foo() {}

    @BlackBox(level = CarburetorLevel.STATEMENT)
    void test(String bar) {
        if (true) {}
    }

}

new Foo().test()
""")
        assert CarburetorTransformation.lastCode == expectedCode
    }

    String expectedCode = """
blackBoxEngine.methodStart(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(10, 13, 5, 6, 'Foo', 'test'), ['bar': bar ])
try {
    blackBoxEngine.statementStart(new io.infinite.supplies.ast.metadata.MetaDataStatement('IfStatement', 12, 12, 9, 21, 'test', 'Foo'))
    if (true) {
    } else {
    }
    blackBoxEngine.statementEnd(new io.infinite.supplies.ast.metadata.MetaDataStatement('IfStatement', 12, 12, 9, 21, 'test', 'Foo'))
} 
catch (java.lang.Exception automaticException) {
    blackBoxEngine.methodException(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(10, 13, 5, 6, 'Foo', 'test'), ['bar': bar ], automaticException)
    throw automaticException 
} 
finally { 
    blackBoxEngine.methodEnd(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(10, 13, 5, 6, 'Foo', 'test'))} 
"""

}