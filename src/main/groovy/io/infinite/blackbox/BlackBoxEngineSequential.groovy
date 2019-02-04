package io.infinite.blackbox


import io.infinite.carburetor.CarburetorEngine
import io.infinite.supplies.ast.exceptions.ExceptionUtils
import io.infinite.supplies.ast.metadata.MetaDataASTNode
import io.infinite.supplies.ast.metadata.MetaDataExpression
import io.infinite.supplies.ast.metadata.MetaDataMethodNode
import io.infinite.supplies.ast.metadata.MetaDataStatement
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class BlackBoxEngineSequential extends CarburetorEngine {

    Logger internalLogger = LoggerFactory.getLogger(BlackBoxEngineSequential.class)

    static BlackBoxEngineSequential getInstance(Logger automaticLog) {
        BlackBoxEngineSequential blackBoxEngine = new BlackBoxEngineFactory().getInstance()
        blackBoxEngine.internalLogger = automaticLog
        return blackBoxEngine
    }

    private void log(String iText) {
        internalLogger.debug(iText)
    }

    private void logError(String iText) {
        internalLogger.error(iText)
    }

    @Override
    void expressionExecutionOpen(MetaDataExpression metaDataExpression) {
        log("""EXPRESSION->${metaDataExpression.className}.${metaDataExpression.methodName}(${
            metaDataExpression.expressionClassName
        }:${metaDataExpression.lineNumber},${metaDataExpression.columnNumber},${metaDataExpression.lastLineNumber},${
            metaDataExpression.lastColumnNumber
        }) - ${metaDataExpression.restoredScriptCode}""")
    }

    @Override
    void statementExecutionOpen(MetaDataStatement metaDataStatement) {
        log("""STATEMENT->${metaDataStatement.className}.${metaDataStatement.methodName}(${
            metaDataStatement.statementClassName
        }:${metaDataStatement.lineNumber},${metaDataStatement.columnNumber},${metaDataStatement.lastLineNumber},${
            metaDataStatement.lastColumnNumber
        })""")
    }

    @Override
    void methodExecutionOpen(MetaDataMethodNode metaDataMethodNode, Map<String, Object> methodArgumentMap) {
        log("""METHOD->${metaDataMethodNode.className}.${metaDataMethodNode.methodName}(${
            metaDataMethodNode.lineNumber
        },${metaDataMethodNode.columnNumber},${metaDataMethodNode.lastLineNumber},${
            metaDataMethodNode.lastColumnNumber
        })""")
        if (methodArgumentsPresent(methodArgumentMap)) {
            for (entry in methodArgumentMap.entrySet()) {
                log("""ARGUMENT:${entry.key}:${entry.value.getClass().getCanonicalName()}""")
                log(entry.value.toString())
            }
        }
    }

    @Override
    void methodExecutionException(MetaDataMethodNode metaDataMethodNode, Map<String, Object> methodArgumentMap) {
        logError("""METHOD EXCEPTION->${metaDataMethodNode.className}.${metaDataMethodNode.methodName}(${
            metaDataMethodNode.lineNumber
        },${metaDataMethodNode.columnNumber},${metaDataMethodNode.lastLineNumber},${
            metaDataMethodNode.lastColumnNumber
        })""")
        if (methodArgumentsPresent(methodArgumentMap)) {
            for (entry in methodArgumentMap.entrySet()) {
                logError("""ARGUMENT:${entry.key}:${entry.value.getClass().getCanonicalName()}""")
                logError(entry.value.toString())
            }
        }
    }

    @Override
    void executionClose() {
        log("""DONE""")
    }

    @Override
    void handleControlStatement(String controlStatementClassName) {

    }

    @Override
    Object handleExpressionEvaluationResult(Object expressionEvaluationResult) {
        log("""EXPRESSION VALUE:""")
        //Avoid logging empty results such as for void method call expressions
        if (expressionEvaluationResult != null) {
            log(expressionEvaluationResult.getClass().getCanonicalName())
            if (expressionEvaluationResult instanceof List) {//workaround possible infinite loops with RoundRobin
                log(expressionEvaluationResult.toArray().toString())
            } else {
                log(TraceSerializer.toString(expressionEvaluationResult))
            }
        }
        return expressionEvaluationResult
    }

    @Override
    Exception carburetorRuntimeExceptionHandle(Exception exception, MetaDataASTNode metaDataASTNode) {
        logError("""EXPRESSION EVALUATION EXCEPTION: ${metaDataASTNode.toString()}""")
        return exception
    }

    void handleMethodResult(Object methodResult) {
        log("METHOD RESULT:")
        log(methodResult.getClass().getCanonicalName())
        log(methodResult.toString())
    }

    Boolean methodArgumentsPresent(Object iArgs) {
        if (iArgs != null) {
            if (iArgs instanceof Collection) {
                return iArgs.size() > 0
            } else if (iArgs instanceof Object[]) {
                return iArgs.length > 0
            } else {
                return false
            }
        } else {
            return false
        }
    }

    void exception(Exception exception) {
        if (exception.isLoggedByBlackBox != true) {
            logError("""EXCEPTION (first occurrence):""")
            exception.uuid = UUID.randomUUID().toString()
            logError(exception.uuid)
            logError(new ExceptionUtils().stacktrace(exception))
        } else {
            logError("""EXCEPTION (additional occurrence):""")
            logError(exception.uuid)
        }
    }

}
