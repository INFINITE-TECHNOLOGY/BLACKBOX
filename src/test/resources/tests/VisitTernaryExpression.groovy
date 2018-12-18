package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitTernaryExpressionNoneLevel() {
    true?true:false
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitTernaryExpressionMethodErrorLevel() {
    true?true:false
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitTernaryExpressionMethodLevel() {
    true?true:false
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitTernaryExpressionStatementLevel() {
    true?true:false
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitTernaryExpressionExpressionLevel() {
    true?true:false
}
