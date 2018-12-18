package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitDoWhileLoopNoneLevel() {
    //unsupported by Groovy now
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitDoWhileLoopMethodErrorLevel() {
    //unsupported by Groovy now
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitDoWhileLoopMethodLevel() {
    //unsupported by Groovy now
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitDoWhileLoopStatementLevel() {
    //unsupported by Groovy now
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitDoWhileLoopExpressionLevel() {
    //unsupported by Groovy now
}