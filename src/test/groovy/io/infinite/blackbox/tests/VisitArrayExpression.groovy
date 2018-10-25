package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitArrayExpressionNoneLevel() {
    def strArray = new String[3]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitArrayExpressionMethodErrorLevel() {
    def strArray = new String[3]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitArrayExpressionMethodLevel() {
    def strArray = new String[3]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitArrayExpressionStatementLevel() {
    def strArray = new String[3]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitArrayExpressionExpressionLevel() {
    def strArray = new String[3]
}