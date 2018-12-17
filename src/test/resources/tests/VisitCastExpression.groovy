package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitCastExpressionNoneLevel() {
    (Integer) "1"
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitCastExpressionMethodErrorLevel() {
    (Integer) "1"
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitCastExpressionMethodLevel() {
    (Integer) "1"
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitCastExpressionStatementLevel() {
    (Integer) "1"
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitCastExpressionExpressionLevel() {
    (Integer) "1"
}