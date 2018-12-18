package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitMapExpressionNoneLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitMapExpressionMethodErrorLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitMapExpressionMethodLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitMapExpressionStatementLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitMapExpressionExpressionLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}

@BlackBox(level = CarburetorLevel.NONE)
void visitMapEntryExpressionNoneLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}