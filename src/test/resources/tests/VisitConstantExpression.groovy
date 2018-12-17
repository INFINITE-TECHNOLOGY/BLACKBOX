package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitConstantExpressionNoneLevel() {
    System.getProperty("z")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitConstantExpressionMethodErrorLevel() {
    System.getProperty("z")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitConstantExpressionMethodLevel() {
    System.getProperty("z")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitConstantExpressionStatementLevel() {
    System.getProperty("z")
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitConstantExpressionExpressionLevel() {
    System.getProperty("z")
}