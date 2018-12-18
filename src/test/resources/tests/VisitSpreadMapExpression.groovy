package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

@BlackBox(level = CarburetorLevel.NONE)
void visitSpreadMapExpressionNoneLevel() {
    def map = [name: 'mrhaki', blog: true]
    assert [name: 'mrhaki', blog: true, subject: 'Groovy Goodness'] == [subject: 'Groovy Goodness', *:map]
}

@BlackBox(level = CarburetorLevel.ERROR)
void visitSpreadMapExpressionMethodErrorLevel() {
    def map = [name: 'mrhaki', blog: true]
    assert [name: 'mrhaki', blog: true, subject: 'Groovy Goodness'] == [subject: 'Groovy Goodness', *:map]
}

@BlackBox(level = CarburetorLevel.METHOD)
void visitSpreadMapExpressionMethodLevel() {
}

@BlackBox(level = CarburetorLevel.STATEMENT)
void visitSpreadMapExpressionStatementLevel() {

    def map = [name: 'mrhaki', blog: true]
    assert [name: 'mrhaki', blog: true, subject: 'Groovy Goodness'] == [subject: 'Groovy Goodness', *:map]
}

@BlackBox(level = CarburetorLevel.EXPRESSION)
void visitSpreadMapExpressionExpressionLevel() {
    def map = [name: 'mrhaki', blog: true]
    assert [name: 'mrhaki', blog: true, subject: 'Groovy Goodness'] == [subject: 'Groovy Goodness', *:map]
}