package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitListExpressionNoneLevel() {
    def (int a, int b) = [1,2]
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitListExpressionMethodErrorLevel() {
    def (int a, int b) = [1,2]
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitListExpressionMethodLevel() {
    def (int a, int b) = [1,2]
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitListExpressionStatementLevel() {
    def (int a, int b) = [1,2]
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitListExpressionExpressionLevel() {
    def strArray = new String[3]
}
