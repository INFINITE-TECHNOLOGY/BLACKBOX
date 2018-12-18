package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
String visitReturnStatementNoneLevel() {
    if (true) {
        if (false) {
            return "Test"
        } else {
            return "Test"
        }
    } else {
        return "Test"
    }
}

@BlackBox(level = CarburetorLevel.ERROR)
String visitReturnStatementMethodErrorLevel() {
    if (true) {
        if (false) {
            return "Test"
        } else {
            return "Test"
        }
    } else {
        return "Test"
    }
}

@BlackBox(level = CarburetorLevel.METHOD)
String visitReturnStatementMethodLevel() {
    if (true) {
        if (false) {
            return "Test"
        } else {
            return "Test"
        }
    } else {
        return "Test"
    }
}

@BlackBox(level = CarburetorLevel.STATEMENT)
String visitReturnStatementStatementLevel() {
    if (true) {
        if (false) {
            return "Test"
        } else {
            return "Test"
        }
    } else {
        return "Test"
    }
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
String visitReturnStatementExpressionLevel() {
    if (true) {
        if (false) {
            return "Test"
        } else {
            return "Test"
        }
    } else {
        return "Test"
    }
}
