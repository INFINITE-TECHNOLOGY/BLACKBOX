package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel


@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitWhileLoopNoneLevel() {
    int z = 0
    while (z < 3) {
        System.out.println("Test")
        z++
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitWhileLoopMethodErrorLevel() {
    int z = 0
    while (z < 3) {
        System.out.println("Test")
        z++
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitWhileLoopMethodLevel() {
    int z = 0
    while (z < 3) {
        System.out.println("Test")
        z++
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitWhileLoopStatementLevel() {
    int z = 0
    while (z < 3) {
        System.out.println("Test")
        z++
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitWhileLoopExpressionLevel() {
    int z = 0
    while (z < 3) {
        System.out.println("Test")
        z++
    }
}