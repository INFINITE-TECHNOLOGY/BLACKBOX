package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
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

@BlackBox(level = CarburetorLevel.ERROR)
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

@BlackBox(level = CarburetorLevel.METHOD)
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

@BlackBox(level = CarburetorLevel.STATEMENT)
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

@BlackBox(level = CarburetorLevel.EXPRESSION)
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