package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitCastExpressionNoneLevel() {
    (Integer) "1"
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitCastExpressionMethodErrorLevel() {
    (Integer) "1"
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitCastExpressionMethodLevel() {
    (Integer) "1"
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitCastExpressionStatementLevel() {
    (Integer) "1"
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitCastExpressionExpressionLevel() {
    (Integer) "1"
}