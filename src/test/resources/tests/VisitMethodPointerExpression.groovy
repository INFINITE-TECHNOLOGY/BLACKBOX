package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitMethodPointerExpressionNoneLevel() {
    this.&tst(1,2,3)
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitMethodPointerExpressionMethodErrorLevel() {
    this.&tst(1,2,3)
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitMethodPointerExpressionMethodLevel() {
    this.&tst(1,2,3)
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitMethodPointerExpressionStatementLevel() {
    this.&tst(1,2,3)
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitMethodPointerExpressionExpressionLevel() {
    this.&tst(1,2,3)
}


void tst(def a, def b, def c) {
    System.out.println(a+b+c)
}