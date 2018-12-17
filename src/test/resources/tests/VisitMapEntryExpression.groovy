package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitMapEntryExpressionNoneLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitMapEntryExpressionMethodErrorLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitMapEntryExpressionMethodLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitMapEntryExpressionStatementLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitMapEntryExpressionExpressionLevel() {
    def map = ["abcd": 1234, "tdgf": 55436]
}
