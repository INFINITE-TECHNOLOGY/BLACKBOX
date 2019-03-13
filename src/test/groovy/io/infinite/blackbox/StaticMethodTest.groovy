package io.infinite.blackbox

class StaticMethodTest extends BlackBoxTestCase {

    void test() {
        assertScript("""
import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel


class Foo {

    @BlackBox(level = CarburetorLevel.EXPRESSION)
    static void test(String bar) {
        if (true) {
            def foo
        }
    }

@BlackBox(level = CarburetorLevel.NONE)
void visitBlockStatementNoneLevel() {
    System.out.println("Test")
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitBlockStatementMethodErrorLevel() {
    System.out.println("Test")
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitBlockStatementMethodLevel() {
    System.out.println("Test")
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitBlockStatementStatementLevel() {
    System.out.println("Test")
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitBlockStatementExpressionLevel() {
    System.out.println("Test")
}

}

new Foo().test()
""")
    }

}