package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitPrefixExpressionNoneLevel() {
    int z = 1
    ++z
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitPrefixExpressionMethodErrorLevel() {
    int z = 1
    ++z
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitPrefixExpressionMethodLevel() {
    int z = 1
    ++z
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitPrefixExpressionStatementLevel() {
    int z = 1
    ++z
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitPrefixExpressionExpressionLevel() {
    int z = 1
    ++z
}
