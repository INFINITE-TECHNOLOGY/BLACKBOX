package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitAssertStatementNoneLevel() {
    assert 1==1
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitAssertStatementMethodErrorLevel() {
    assert 1==1
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitAssertStatementMethodLevel() {
    assert 1==1
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitAssertStatementStatementLevel() {
    assert 1==1
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitAssertStatementExpressionLevel() {
    assert 1==1
}