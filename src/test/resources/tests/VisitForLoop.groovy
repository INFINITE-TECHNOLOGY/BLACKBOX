package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitForLoopNoneLevel() {
    for (z in [1,2,3,4]) {
        System.out.println("Test")
    }
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitForLoopMethodErrorLevel() {
    for (z in [1,2,3,4]) {
        System.out.println("Test")
    }
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitForLoopMethodLevel() {
    for (z in [1,2,3,4]) {
        System.out.println("Test")
    }
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitForLoopStatementLevel() {
    for (z in [1,2,3,4]) {
        System.out.println("Test")
    }
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitForLoopExpressionLevel() {
    for (z in [1,2,3,4]) {
        System.out.println("Test")
    }
}
