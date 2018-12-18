package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitDeclarationExpressionNoneLevel() {
    def z = 1
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitDeclarationExpressionMethodErrorLevel() {
    def z = 1
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitDeclarationExpressionMethodLevel() {
    def z = 1
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitDeclarationExpressionStatementLevel() {
    def z = 1
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitDeclarationExpressionExpressionLevel() {
    def z = 1
}
