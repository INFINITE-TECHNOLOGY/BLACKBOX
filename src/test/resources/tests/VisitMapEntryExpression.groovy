package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitMapEntryExpressionNoneLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitMapEntryExpressionMethodErrorLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitMapEntryExpressionMethodLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitMapEntryExpressionStatementLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitMapEntryExpressionExpressionLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}
