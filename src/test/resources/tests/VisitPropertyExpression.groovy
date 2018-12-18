package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitPropertyExpressionNoneLevel() {
    System.out
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitPropertyExpressionMethodErrorLevel() {
    System.out
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitPropertyExpressionMethodLevel() {
    System.out
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitPropertyExpressionStatementLevel() {
    System.out
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitPropertyExpressionExpressionLevel() {
    System.out
}
