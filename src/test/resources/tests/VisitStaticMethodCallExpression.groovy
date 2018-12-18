package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

static String tst(String z, String q, String w) {
    return z+q+w
}

@BlackBox(level = CarburetorLevel.NONE)
void visitStaticMethodCallExpressionNoneLevel() {
    tst("1","2","3")
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitStaticMethodCallExpressionMethodErrorLevel() {
    tst("1","2","3")
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitStaticMethodCallExpressionMethodLevel() {
    tst("1","2","3")
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitStaticMethodCallExpressionStatementLevel() {
    tst("1","2","3")
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitStaticMethodCallExpressionExpressionLevel() {
    tst("1","2","3")
//    1/0
}