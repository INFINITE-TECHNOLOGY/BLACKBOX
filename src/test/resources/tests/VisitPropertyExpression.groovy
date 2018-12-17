package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitPropertyExpressionNoneLevel() {
    System.out
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitPropertyExpressionMethodErrorLevel() {
    System.out
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitPropertyExpressionMethodLevel() {
    System.out
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitPropertyExpressionStatementLevel() {
    System.out
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitPropertyExpressionExpressionLevel() {
    System.out
}
