package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitTupleExpressionNoneLevel() {
    def (int a, int b) = [1,2]
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitTupleExpressionMethodErrorLevel() {
    def (int a, int b) = [1,2]
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitTupleExpressionMethodLevel() {
    def (int a, int b) = [1,2]
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitTupleExpressionStatementLevel() {
    def (int a, int b) = [1,2]
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitTupleExpressionExpressionLevel() {
    def (int a, int b) = [1,2]
}