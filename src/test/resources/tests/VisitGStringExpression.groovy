package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitGStringExpressionNoneLevel() {
    System.out.println("test gstring ${new Date()} this is test ${Integer.valueOf("123").toString()}")
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitGStringExpressionMethodErrorLevel() {
    System.out.println("test gstring ${new Date()} this is test ${Integer.valueOf("123").toString()}")
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitGStringExpressionMethodLevel() {
    System.out.println("test gstring ${new Date()} this is test ${Integer.valueOf("123").toString()}")
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitGStringExpressionStatementLevel() {
    System.out.println("test gstring ${new Date()} this is test ${Integer.valueOf("123").toString()}")
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitGStringExpressionExpressionLevel() {
    System.out.println("test gstring ${new Date()} this is test ${Integer.valueOf("123").toString()}")
}