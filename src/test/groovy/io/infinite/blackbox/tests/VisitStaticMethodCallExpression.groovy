package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

static String tst(String z, String q, String w) {
    return z+q+w
}

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitStaticMethodCallExpressionNoneLevel() {
    tst("1","2","3")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitStaticMethodCallExpressionMethodErrorLevel() {
    tst("1","2","3")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitStaticMethodCallExpressionMethodLevel() {
    tst("1","2","3")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitStaticMethodCallExpressionStatementLevel() {
    tst("1","2","3")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitStaticMethodCallExpressionExpressionLevel() {
    tst("1","2","3")
//    1/0
}