package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitExpressionStatementNoneLevel() {
    int z
    z = z
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitExpressionStatementMethodErrorLevel() {
    int z
    z = z
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitExpressionStatementMethodLevel() {
    int z
    z = z
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitExpressionStatementStatementLevel() {
    int z
    z = z
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitExpressionStatementExpressionLevel() {
    int z
    z = z
}