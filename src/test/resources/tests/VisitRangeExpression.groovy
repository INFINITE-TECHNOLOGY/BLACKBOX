package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitRangeExpressionNoneLevel() {
    for (i in 1..3) {
        System.out.println(i)
    }
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitRangeExpressionMethodErrorLevel() {
    for (i in 1..3) {
        System.out.println(i)
    }
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitRangeExpressionMethodLevel() {
    for (i in 1..3) {
        System.out.println(i)
    }
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitRangeExpressionStatementLevel() {
    for (i in 1..3) {
        System.out.println(i)
    }
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitRangeExpressionExpressionLevel() {
    for (i in 1..3) {
        System.out.println(i)
    }
}
