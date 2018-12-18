package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitArrayExpressionNoneLevel() {
    def strArray = new String[3]
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitArrayExpressionMethodErrorLevel() {
    def strArray = new String[3]
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitArrayExpressionMethodLevel() {
    def strArray = new String[3]
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitArrayExpressionStatementLevel() {
    def strArray = new String[3]
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitArrayExpressionExpressionLevel() {
    def strArray = new String[3]
}