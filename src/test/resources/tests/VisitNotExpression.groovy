package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitNotExpressionNoneLevel() {
    if (!true) false
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitNotExpressionMethodErrorLevel() {
    if (!true) false
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitNotExpressionMethodLevel() {
    if (!true) false
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitNotExpressionStatementLevel() {
    if (!true) false
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitNotExpressionExpressionLevel() {
    if (!true) false
}
