package io.infinite.blackbox

import io.infinite.supplies.ast.other.ASTUtils
import org.codehaus.groovy.ast.ASTNode

class BlackBoxTransformationException extends Exception {

    BlackBoxTransformationException(ASTNode astNode, Exception exception) {
        super(new ASTUtils().prepareExceptionMessage(astNode), exception)
        this.stackTrace = [] as StackTraceElement[]
    }

    BlackBoxTransformationException(ASTNode astNode, String message) {
        super(message + "\n" + new ASTUtils().prepareExceptionMessage(astNode))
    }

}