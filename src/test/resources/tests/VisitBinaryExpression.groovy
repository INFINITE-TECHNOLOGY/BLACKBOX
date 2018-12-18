package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitBinaryExpressionNoneLevel() {
    Object object = new Object()
    Object object2 = new Object()
    object = object2
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitBinaryExpressionMethodErrorLevel() {
    Object object = new Object()
    Object object2 = new Object()
    object = object2
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitBinaryExpressionMethodLevel() {
    Object object = new Object()
    Object object2 = new Object()
    object = object2
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitBinaryExpressionStatementLevel() {
    Object object = new Object()
    Object object2 = new Object()
    object = object2
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitBinaryExpressionExpressionLevel() {
    Object object = new Object()
    Object object2 = new Object()
    object = object2
}
