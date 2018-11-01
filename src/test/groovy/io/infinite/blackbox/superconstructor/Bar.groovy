package io.infinite.blackbox.superconstructor

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxEngine
import io.infinite.blackbox.BlackBoxLevel


class Bar extends Foo {

    @BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
    Bar(String foo) {
            super(BlackBoxEngine.getInstance().expressionEvaluation('ConstantExpression', '\'bar\'', 15, 20, 11, 11, { Object itVariableReplacement14 ->
                return 'bar'
            }, 'ArgumentListExpression:expressions') as String)
    }

}
