package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitContinueStatementNoneLevel() {
    for (z in [1,2,3,4]) {
        System.out.println("Test")
        if (true) {
            continue
        }
        System.out.println("Test")
    }
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitContinueStatementMethodErrorLevel() {
    for (z in [1,2,3,4]) {
        System.out.println("Test")
        if (true) {
            continue
        }
        System.out.println("Test")
    }
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitContinueStatementMethodLevel() {
    for (z in [1,2,3,4]) {
        System.out.println("Test")
        if (true) {
            continue
        }
        System.out.println("Test")
    }
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitContinueStatementStatementLevel() {
    for (z in [1,2,3,4]) {
        System.out.println("Test")
        if (true) {
            continue
        }
        System.out.println("Test")
    }
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitContinueStatementExpressionLevel() {
    for (z in [1,2,3,4]) {
        System.out.println("Test")
        if (true) {
            continue
        }
        System.out.println("Test")
    }
}
