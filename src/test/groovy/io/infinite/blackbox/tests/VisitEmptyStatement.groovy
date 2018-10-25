package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitEmptyStatementNoneLevel() {
    //skipped
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitEmptyStatementMethodErrorLevel() {
    //skipped
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitEmptyStatementMethodLevel() {
    //skipped
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitEmptyStatementStatementLevel() {
    //skipped
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitEmptyStatementExpressionLevel() {
    //skipped
}