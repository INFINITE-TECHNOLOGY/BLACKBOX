package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitThrowStatementNoneLevel() {
    try {
        System.out.println("test")
        System.out.println("test")
        throw new Exception("test")
    } catch (Throwable throwable) {
        System.out.println("test")
        System.out.println("test")
    } finally {
        System.out.println("test")
        System.out.println("test")
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitThrowStatementMethodErrorLevel() {
    try {
        System.out.println("test")
        System.out.println("test")
        throw new Exception("test")
    } catch (Throwable throwable) {
        System.out.println("test")
        System.out.println("test")
    } finally {
        System.out.println("test")
        System.out.println("test")
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitThrowStatementMethodLevel() {
    try {
        System.out.println("test")
        System.out.println("test")
        throw new Exception("test")
    } catch (Throwable throwable) {
        System.out.println("test")
        System.out.println("test")
    } finally {
        System.out.println("test")
        System.out.println("test")
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitThrowStatementStatementLevel() {
    try {
        System.out.println("test")
        System.out.println("test")
        throw new Exception("test")
    } catch (Throwable throwable) {
        System.out.println("test")
        System.out.println("test")
    } finally {
        System.out.println("test")
        System.out.println("test")
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitThrowStatementExpressionLevel() {
    try {
        System.out.println("test")
        System.out.println("test")
        throw new Exception("test")
    } catch (Throwable throwable) {
        System.out.println("test")
        System.out.println("test")
    } finally {
        System.out.println("test")
        System.out.println("test")
    }
}