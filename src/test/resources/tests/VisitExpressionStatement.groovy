package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitExpressionStatementNoneLevel() {
    int z
    z = z
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitExpressionStatementMethodErrorLevel() {
    int z
    z = z
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitExpressionStatementMethodLevel() {
    int z
    z = z
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitExpressionStatementStatementLevel() {
    int z
    z = z
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitExpressionStatementExpressionLevel() {
    int z
    z = z
}