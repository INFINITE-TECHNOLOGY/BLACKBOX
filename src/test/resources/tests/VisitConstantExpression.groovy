package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitConstantExpressionNoneLevel() {
    System.getProperty("z")
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitConstantExpressionMethodErrorLevel() {
    System.getProperty("z")
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitConstantExpressionMethodLevel() {
    System.getProperty("z")
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitConstantExpressionStatementLevel() {
    System.getProperty("z")
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitConstantExpressionExpressionLevel() {
    System.getProperty("z")
}