package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitClosureExpressionNoneLevel() {
    Closure c = {
        System.out.println("z")
    }
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitClosureExpressionMethodErrorLevel() {
    Closure c = {
        System.out.println("z")
    }
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitClosureExpressionMethodLevel() {
    Closure c = {
        System.out.println("z")
    }
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitClosureExpressionStatementLevel() {
    Closure c = {
        System.out.println("z")
    }
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitClosureExpressionExpressionLevel() {
    Closure c = {
        System.out.println("z")
    }
}
