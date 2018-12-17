package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitMethodCallExpressionNoneLevel() {
    System.out.println("test")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitMethodCallExpressionMethodErrorLevel() {
    System.out.println("test")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitMethodCallExpressionMethodLevel() {
    System.out.println("test")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitMethodCallExpressionStatementLevel() {
    System.out.println("test")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitMethodCallExpressionExpressionLevel() {
    System.out.println("test")
}