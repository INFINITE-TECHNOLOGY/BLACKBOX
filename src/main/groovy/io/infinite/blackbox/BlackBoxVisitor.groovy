package io.infinite.blackbox

import groovy.transform.CompileDynamic
import groovy.transform.ToString
import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.stmt.*

@ToString(includeNames = true, includeFields = true)
@CompileDynamic
class BlackBoxVisitor extends CodeVisitorSupport {

    BlackBoxTransformation carburetorTransformation
    BlackBoxLevel carburetorLevel

    BlackBoxVisitor(BlackBoxTransformation carburetorTransformation, BlackBoxLevel carburetorLevel) {
        this.carburetorTransformation = carburetorTransformation
        this.carburetorLevel = carburetorLevel
    }

    @Override
    void visitReturnStatement(ReturnStatement returnStatement) {
        super.visitReturnStatement(returnStatement)
        if (!returnStatement.isReturningNullOrVoid()) {
            returnStatement.setExpression(carburetorTransformation.transformExpression(returnStatement.getExpression(), returnStatement.getClass().getSimpleName() + ":expression"))
        }
    }

}
