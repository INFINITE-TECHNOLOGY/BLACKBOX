package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitBooleanExpressionNoneLevel() {
    if (true) false
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitBooleanExpressionMethodErrorLevel() {
    if (true) false
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitBooleanExpressionMethodLevel() {
    if (true) false
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitBooleanExpressionStatementLevel() {
    if (true) false
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitBooleanExpressionExpressionLevel() {
    if (true) false
}