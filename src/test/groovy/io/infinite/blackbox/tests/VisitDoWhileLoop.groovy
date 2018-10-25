package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitDoWhileLoopNoneLevel() {
    //unsupported by Groovy now
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitDoWhileLoopMethodErrorLevel() {
    //unsupported by Groovy now
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitDoWhileLoopMethodLevel() {
    //unsupported by Groovy now
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitDoWhileLoopStatementLevel() {
    //unsupported by Groovy now
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitDoWhileLoopExpressionLevel() {
    //unsupported by Groovy now
}