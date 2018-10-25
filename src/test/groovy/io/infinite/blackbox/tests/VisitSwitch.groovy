package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitSwitchNoneLevel() {
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

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitSwitchMethodErrorLevel() {
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

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitSwitchMethodLevel() {
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

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitSwitchStatementLevel() {
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

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitSwitchExpressionLevel() {
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