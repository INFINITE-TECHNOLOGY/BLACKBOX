package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitBlockStatementNoneLevel() {
    System.out.println("Test")
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitBlockStatementMethodErrorLevel() {
    System.out.println("Test")
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitBlockStatementMethodLevel() {
    System.out.println("Test")
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitBlockStatementStatementLevel() {
    System.out.println("Test")
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitBlockStatementExpressionLevel() {
    System.out.println("Test")
}