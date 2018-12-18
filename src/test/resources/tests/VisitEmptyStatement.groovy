package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitEmptyStatementNoneLevel() {
    //skipped
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitEmptyStatementMethodErrorLevel() {
    //skipped
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitEmptyStatementMethodLevel() {
    //skipped
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitEmptyStatementStatementLevel() {
    //skipped
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitEmptyStatementExpressionLevel() {
    //skipped
}