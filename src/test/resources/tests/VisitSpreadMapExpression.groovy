package io.infinite.blackbox.tests

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

@BlackBox(blackBoxLevel = BlackBoxLevel.NONE)
void visitSpreadMapExpressionNoneLevel() {
    def map = [name: 'mrhaki', blog: true]
    assert [name: 'mrhaki', blog: true, subject: 'Groovy Goodness'] == [subject: 'Groovy Goodness', *:map]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD_ERROR)
void visitSpreadMapExpressionMethodErrorLevel() {
    def map = [name: 'mrhaki', blog: true]
    assert [name: 'mrhaki', blog: true, subject: 'Groovy Goodness'] == [subject: 'Groovy Goodness', *:map]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.METHOD)
void visitSpreadMapExpressionMethodLevel() {
}

@BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
void visitSpreadMapExpressionStatementLevel() {

    def map = [name: 'mrhaki', blog: true]
    assert [name: 'mrhaki', blog: true, subject: 'Groovy Goodness'] == [subject: 'Groovy Goodness', *:map]
}

@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
void visitSpreadMapExpressionExpressionLevel() {
    def map = [name: 'mrhaki', blog: true]
    assert [name: 'mrhaki', blog: true, subject: 'Groovy Goodness'] == [subject: 'Groovy Goodness', *:map]
}