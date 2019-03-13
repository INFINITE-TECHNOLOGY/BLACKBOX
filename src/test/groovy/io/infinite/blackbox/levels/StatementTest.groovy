package io.infinite.blackbox.levels

import io.infinite.blackbox.BlackBoxTestCase
import io.infinite.carburetor.CarburetorTransformation

class StatementTest extends BlackBoxTestCase {

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
blackBoxEngine.methodBegin(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(10, 13, 5, 6, 'test', 'Foo'), ['bar': bar ])
try {
    blackBoxEngine.statementBegin(new io.infinite.supplies.ast.metadata.MetaDataStatement('IfStatement', 12, 12, 9, 21, 'test', 'Foo'))
    if (true) {
    } else {
    }
    blackBoxEngine.statementEnd(new io.infinite.supplies.ast.metadata.MetaDataStatement('IfStatement', 12, 12, 9, 21, 'test', 'Foo'))
} 
catch (java.lang.Exception automaticException) {
    blackBoxEngine.methodException(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(10, 13, 5, 6, 'test', 'Foo'), ['bar': bar ], automaticException)
    throw automaticException 
} 
finally { 
    blackBoxEngine.methodEnd(new io.infinite.supplies.ast.metadata.MetaDataMethodNode(10, 13, 5, 6, 'test', 'Foo'))} 
"""

}