package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitArgumentlistExpressionNoneLevel() {
    tst(1,2,3)
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitArgumentlistExpressionMethodErrorLevel() {
    tst(1,2,3)
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitArgumentlistExpressionMethodLevel() {
    tst(1,2,3)
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitArgumentlistExpressionStatementLevel() {
    tst(1,2,3)
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitArgumentlistExpressionExpressionLevel() {
    tst(1,2,3)
}

void tst(def a, def b, def c) {
    System.out.println(a+b+c)
}