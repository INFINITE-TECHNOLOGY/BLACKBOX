package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitPostfixExpressionNoneLevel() {
    int z = 1
    z++
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitPostfixExpressionMethodErrorLevel() {
    int z = 1
    z++
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitPostfixExpressionMethodLevel() {
    int z = 1
    z++
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitPostfixExpressionStatementLevel() {
    int z = 1
    z++
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitPostfixExpressionExpressionLevel() {
    int z = 1
    z++
}