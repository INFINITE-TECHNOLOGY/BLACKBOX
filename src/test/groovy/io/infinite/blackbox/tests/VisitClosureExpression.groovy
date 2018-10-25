package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitClosureExpressionNoneLevel() {
    Closure c = {
        System.out.println("z")
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitClosureExpressionMethodErrorLevel() {
    Closure c = {
        System.out.println("z")
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitClosureExpressionMethodLevel() {
    Closure c = {
        System.out.println("z")
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitClosureExpressionStatementLevel() {
    Closure c = {
        System.out.println("z")
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitClosureExpressionExpressionLevel() {
    Closure c = {
        System.out.println("z")
    }
}
