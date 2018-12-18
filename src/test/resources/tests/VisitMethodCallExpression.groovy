package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitMethodCallExpressionNoneLevel() {
    System.out.println("test")
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitMethodCallExpressionMethodErrorLevel() {
    System.out.println("test")
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitMethodCallExpressionMethodLevel() {
    System.out.println("test")
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitMethodCallExpressionStatementLevel() {
    System.out.println("test")
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitMethodCallExpressionExpressionLevel() {
    System.out.println("test")
}