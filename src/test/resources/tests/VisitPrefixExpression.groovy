package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitPrefixExpressionNoneLevel() {
    int z = 1
    ++z
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitPrefixExpressionMethodErrorLevel() {
    int z = 1
    ++z
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitPrefixExpressionMethodLevel() {
    int z = 1
    ++z
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitPrefixExpressionStatementLevel() {
    int z = 1
    ++z
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitPrefixExpressionExpressionLevel() {
    int z = 1
    ++z
}
