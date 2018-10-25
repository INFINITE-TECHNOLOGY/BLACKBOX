package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitTryCatchFinallyNoneLevel() {
    int wwww = 0
    while (wwww < 3) {
        try {
            System.out.println("try")
            System.out.println("zzzzzzzzzz " + wwww)
            wwww++
        } catch (Throwable z) {
            System.out.println("Catched " + z)
            System.out.println("Catch")
        } finally {
            System.out.println("Finally1 " + wwww)
            System.out.println("Finally2 " + wwww)
        }
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitTryCatchFinallyMethodErrorLevel() {
    int wwww = 0
    while (wwww < 3) {
        try {
            System.out.println("try")
            System.out.println("zzzzzzzzzz " + wwww)
            wwww++
        } catch (Throwable z) {
            System.out.println("Catched " + z)
            System.out.println("Catch")
        } finally {
            System.out.println("Finally1 " + wwww)
            System.out.println("Finally2 " + wwww)
        }
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitTryCatchFinallyMethodLevel() {
    int wwww = 0
    while (wwww < 3) {
        try {
            System.out.println("try")
            System.out.println("zzzzzzzzzz " + wwww)
            wwww++
        } catch (Throwable z) {
            System.out.println("Catched " + z)
            System.out.println("Catch")
        } finally {
            System.out.println("Finally1 " + wwww)
            System.out.println("Finally2 " + wwww)
        }
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitTryCatchFinallyStatementLevel() {
    int wwww = 0
    while (wwww < 3) {
        try {
            System.out.println("try")
            System.out.println("zzzzzzzzzz " + wwww)
            wwww++
        } catch (Throwable z) {
            System.out.println("Catched " + z)
            System.out.println("Catch")
        } finally {
            System.out.println("Finally1 " + wwww)
            System.out.println("Finally2 " + wwww)
        }
    }
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitTryCatchFinallyExpressionLevel() {
    int wwww = 0
    while (wwww < 3) {
        try {
            System.out.println("try")
            System.out.println("zzzzzzzzzz " + wwww)
            wwww++
        } catch (Throwable z) {
            System.out.println("Catched " + z)
            System.out.println("Catch")
        } finally {
            System.out.println("Finally1 " + wwww)
            System.out.println("Finally2 " + wwww)
        }
    }
}
