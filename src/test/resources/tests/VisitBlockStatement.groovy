package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitBlockStatementNoneLevel() {
    System.out.println("Test")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitBlockStatementMethodErrorLevel() {
    System.out.println("Test")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitBlockStatementMethodLevel() {
    System.out.println("Test")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitBlockStatementStatementLevel() {
    System.out.println("Test")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitBlockStatementExpressionLevel() {
    System.out.println("Test")
}