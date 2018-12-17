package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitIfElseNoneLevel() {
    if (true) {
        if (false) {
            System.out.println("Test")
        } else {
            System.out.println("Test")
        }
    } else {
        System.out.println("Test")
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitIfElseMethodErrorLevel() {
    if (true) {
        if (false) {
            System.out.println("Test")
        } else {
            System.out.println("Test")
        }
    } else {
        System.out.println("Test")
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitIfElseMethodLevel() {
    if (true) {
        if (false) {
            System.out.println("Test")
        } else {
            System.out.println("Test")
        }
    } else {
        System.out.println("Test")
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitIfElseStatementLevel() {
    if (true) {
        if (false) {
            System.out.println("Test")
        } else {
            System.out.println("Test")
        }
    } else {
        System.out.println("Test")
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitIfElseExpressionLevel() {
    if (true) {
        if (false) {
            System.out.println("Test")
        } else {
            System.out.println("Test")
        }
    } else {
        System.out.println("Test")
    }
}