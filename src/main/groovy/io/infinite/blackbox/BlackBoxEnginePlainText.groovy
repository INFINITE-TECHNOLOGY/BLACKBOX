package io.infinite.blackbox

import io.infinite.blackbox.generated.XMLStackTrace
import io.infinite.supplies.ast.metadata.MetaDataExpression
import io.infinite.supplies.ast.metadata.MetaDataMethodNode
import io.infinite.supplies.ast.metadata.MetaDataStatement
import org.codehaus.groovy.runtime.StackTraceUtils

class BlackBoxEnginePlainText extends BlackBoxEngine {

    /**
     * Logs exception as plaintext<br/>
     * Does not use XML format<br/>
     * Does not log neither method arguments nor preceding AST.<br/>
     *
     * @param exception
     */
    void exception(Exception exception) {
        ErrorLoggingStrategy errorLoggingStrategy = ErrorLoggingStrategy.valueOf(blackBoxConfig.runtime.strategy)
        switch (errorLoggingStrategy) {
            case ErrorLoggingStrategy.FULL_THEN_REFERENCE:
                if (exception.isLoggedByBlackBox != true) {
                    exception.isLoggedByBlackBox = true
                    exception.uuid = UUID.randomUUID().toString()
                    this.internalLogger.error(exception.getMessage(), exception.uuid, new StackTraceUtils().sanitize(exception))
                } else {
                    this.internalLogger.error(exception.getMessage(), exception.uuid)
                }
                break
            case ErrorLoggingStrategy.ALWAYS_REFERENCE:
                if (exception.isLoggedByBlackBox != true) {
                    exception.isLoggedByBlackBox = true
                    exception.uuid = UUID.randomUUID().toString()
                    this.internalLogger.error(exception.getMessage(), exception.uuid)
                } else {
                    this.internalLogger.error(exception.getMessage(), exception.uuid)
                }
                break
            case ErrorLoggingStrategy.ALWAYS_FULL:
                if (exception.isLoggedByBlackBox != true) {
                    exception.isLoggedByBlackBox = true
                    exception.uuid = UUID.randomUUID().toString()
                    this.internalLogger.error(exception.getMessage(), exception.uuid, new StackTraceUtils().sanitize(exception))
                } else {
                    this.internalLogger.error(exception.getMessage(), exception.uuid, new StackTraceUtils().sanitize(exception))
                }
                break
            case ErrorLoggingStrategy.FULL_THEN_NOTHING:
                if (exception.isLoggedByBlackBox != true) {
                    exception.isLoggedByBlackBox = true
                    exception.uuid = UUID.randomUUID().toString()
                    this.internalLogger.error(exception.getMessage(), exception.uuid, new StackTraceUtils().sanitize(exception))
                }
                break
            case ErrorLoggingStrategy.REFERENCE_THEN_NOTHING:
                XMLStackTrace xmlException = new XMLStackTrace()
                if (exception.isLoggedByBlackBox != true) {
                    exception.isLoggedByBlackBox = true
                    exception.uuid = UUID.randomUUID().toString()
                    this.internalLogger.error(exception.getMessage(), exception.uuid)
                }
                break
            case ErrorLoggingStrategy.NOTHING:
                break
            default:
                this.internalLogger.warn("Unsupported/undefined BlackBox Strategy: " + errorLoggingStrategy)
        }
    }

    @Override
    void expressionExecutionOpen(MetaDataExpression metaDataExpression) {

    }

    @Override
    Object handleExpressionEvaluationResult(Object expressionEvaluationResult) {
        return null
    }

    @Override
    void statementExecutionOpen(MetaDataStatement metaDataStatement) {

    }

    @Override
    void methodExecutionOpen(MetaDataMethodNode metaDataMethodNode, Map<String, Object> methodArgumentMap) {

    }

    @Override
    void executionClose() {

    }

    @Override
    void handleControlStatement(String controlStatementClassName) {

    }

    @Override
    void handleMethodResult(Object methodResult) {

    }

}
