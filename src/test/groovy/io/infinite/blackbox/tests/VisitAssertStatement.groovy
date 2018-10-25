package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitAssertStatementNoneLevel() {
    assert 1==1
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitAssertStatementMethodErrorLevel() {
    assert 1==1
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitAssertStatementMethodLevel() {
    assert 1==1
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitAssertStatementStatementLevel() {
    assert 1==1
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitAssertStatementExpressionLevel() {
    assert 1==1
}