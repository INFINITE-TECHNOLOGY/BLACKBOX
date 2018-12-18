package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitVariableExpressionNoneLevel() {
    def z = 1
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitVariableExpressionMethodErrorLevel() {
    def z = 1
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitVariableExpressionMethodLevel() {
    def z = 1
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitVariableExpressionStatementLevel() {
    def z = 1
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitVariableExpressionExpressionLevel() {
    def z = 1
}
