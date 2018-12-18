package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitConstructorCallExpressionNoneLevel() {
    Object object = new Object()
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitConstructorCallExpressionMethodErrorLevel() {
    Object object = new Object()
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitConstructorCallExpressionMethodLevel() {
    Object object = new Object()
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitConstructorCallExpressionStatementLevel() {
    Object object = new Object()
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitConstructorCallExpressionExpressionLevel() {
    Object object = new Object()
}
