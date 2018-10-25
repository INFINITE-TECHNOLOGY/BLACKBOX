package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitTupleExpressionNoneLevel() {
    def (int a, int b) = [1,2]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitTupleExpressionMethodErrorLevel() {
    def (int a, int b) = [1,2]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitTupleExpressionMethodLevel() {
    def (int a, int b) = [1,2]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitTupleExpressionStatementLevel() {
    def (int a, int b) = [1,2]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitTupleExpressionExpressionLevel() {
    def (int a, int b) = [1,2]
}