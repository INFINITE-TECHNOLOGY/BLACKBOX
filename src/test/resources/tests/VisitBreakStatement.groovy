package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitBreakStatementNoneLevel() {
    switch (1) {
        case 1:
            System.out.println("test")
            System.out.println("test")
            break
        case 2:
            System.out.println("test")
            System.out.println("test")
            break
        default:
            System.out.println("test")
            System.out.println("test")
            break
    }
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitBreakStatementMethodErrorLevel() {
    switch (1) {
        case 1:
            System.out.println("test")
            System.out.println("test")
            break
        case 2:
            System.out.println("test")
            System.out.println("test")
            break
        default:
            System.out.println("test")
            System.out.println("test")
            break
    }
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitBreakStatementMethodLevel() {
    switch (1) {
        case 1:
            System.out.println("test")
            System.out.println("test")
            break
        case 2:
            System.out.println("test")
            System.out.println("test")
            break
        default:
            System.out.println("test")
            System.out.println("test")
            break
    }
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitBreakStatementStatementLevel() {
    switch (1) {
        case 1:
            System.out.println("test")
            System.out.println("test")
            break
        case 2:
            System.out.println("test")
            System.out.println("test")
            break
        default:
            System.out.println("test")
            System.out.println("test")
            break
    }
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitBreakStatementExpressionLevel() {
    switch (1) {
        case 1:
            System.out.println("test")
            System.out.println("test")
            break
        case 2:
            System.out.println("test")
            System.out.println("test")
            break
        default:
            System.out.println("test")
            System.out.println("test")
            break
    }
}