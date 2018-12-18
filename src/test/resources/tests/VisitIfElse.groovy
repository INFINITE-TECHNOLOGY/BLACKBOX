package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
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

@BlackBox(level = CarburetorLevel.ERROR)
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

@BlackBox(level = CarburetorLevel.METHOD)
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

@BlackBox(level = CarburetorLevel.STATEMENT)
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

@BlackBox(level = CarburetorLevel.EXPRESSION)
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