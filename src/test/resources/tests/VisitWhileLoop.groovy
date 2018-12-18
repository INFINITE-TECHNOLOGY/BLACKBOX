package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel


@BlackBox(level = CarburetorLevel.NONE)
void visitWhileLoopNoneLevel() {
    int z = 0
    while (z < 3) {
        System.out.println("Test")
        z++
    }
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitWhileLoopMethodErrorLevel() {
    int z = 0
    while (z < 3) {
        System.out.println("Test")
        z++
    }
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitWhileLoopMethodLevel() {
    int z = 0
    while (z < 3) {
        System.out.println("Test")
        z++
    }
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitWhileLoopStatementLevel() {
    int z = 0
    while (z < 3) {
        System.out.println("Test")
        z++
    }
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitWhileLoopExpressionLevel() {
    int z = 0
    while (z < 3) {
        System.out.println("Test")
        z++
    }
}