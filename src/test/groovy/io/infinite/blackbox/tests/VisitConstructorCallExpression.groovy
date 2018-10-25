package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitConstructorCallExpressionNoneLevel() {
    Object object = new Object()
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitConstructorCallExpressionMethodErrorLevel() {
    Object object = new Object()
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitConstructorCallExpressionMethodLevel() {
    Object object = new Object()
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitConstructorCallExpressionStatementLevel() {
    Object object = new Object()
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitConstructorCallExpressionExpressionLevel() {
    Object object = new Object()
}
