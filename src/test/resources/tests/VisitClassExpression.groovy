package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitClassExpressionNoneLevel() {
    System.getProperty("z")
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitClassExpressionMethodErrorLevel() {
    System.getProperty("z")
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitClassExpressionMethodLevel() {
    System.getProperty("z")
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitClassExpressionStatementLevel() {
    System.getProperty("z")
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitClassExpressionExpressionLevel() {
    System.getProperty("z")
}
