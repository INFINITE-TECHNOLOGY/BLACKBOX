package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitShortTernaryExpressionNoneLevel() {
    def location = true ?: "z"
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitShortTernaryExpressionMethodErrorLevel() {
    def location = true ?: "z"
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitShortTernaryExpressionMethodLevel() {
    def location = true ?: "z"
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitShortTernaryExpressionStatementLevel() {
    def location = true ?: "z"
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitShortTernaryExpressionExpressionLevel() {
    def location = true ?: "z"
}
