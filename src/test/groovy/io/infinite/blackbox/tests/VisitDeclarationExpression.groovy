package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitDeclarationExpressionNoneLevel() {
    def z = 1
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitDeclarationExpressionMethodErrorLevel() {
    def z = 1
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitDeclarationExpressionMethodLevel() {
    def z = 1
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitDeclarationExpressionStatementLevel() {
    def z = 1
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitDeclarationExpressionExpressionLevel() {
    def z = 1
}
