package io.infinite.blackbox

import groovy.transform.CompileDynamic
import io.infinite.carburetor.CarburetorEngine
import io.infinite.supplies.ast.exceptions.ExceptionUtils
import io.infinite.supplies.ast.metadata.MetaDataASTNode
import io.infinite.supplies.ast.metadata.MetaDataExpression
import io.infinite.supplies.ast.metadata.MetaDataMethodNode
import io.infinite.supplies.ast.metadata.MetaDataStatement
import org.slf4j.Logger

class BlackBoxEngine extends CarburetorEngine {

    static {
        staticInit()
    }

    @CompileDynamic
    static void staticInit() {
        Exception.getMetaClass().isLoggedByBlackBox = null
        Exception.getMetaClass().uuid = null
    }

    Logger internalLogger

    BlackBoxEngine() {
    }

    BlackBoxEngine(Logger internalLogger) {
        this.internalLogger = internalLogger
    }

    BlackBoxEngine getInstance(Logger automaticLog) {
        return new BlackBoxEngine(automaticLog)
    }

    private void log(String iText) {
        internalLogger.debug(iText)
    }

    private void logError(String iText) {
        internalLogger.error(iText)
    }

    @Override
    void expressionBegin(MetaDataExpression metaDataExpression) {
        log("""EXPRESSION BEGIN: ${metaDataExpression.className}.${metaDataExpression.methodName}(${
            metaDataExpression.expressionClassName
        }:${metaDataExpression.lineNumber},${metaDataExpression.columnNumber},${metaDataExpression.lastLineNumber},${
            metaDataExpression.lastColumnNumber
        }) - ${metaDataExpression.restoredScriptCode}""")
    }

    @Override
    void expressionEnd(MetaDataExpression metaDataExpression) {
        log("""EXPRESSION END: ${metaDataExpression.className}.${metaDataExpression.methodName}(${
            metaDataExpression.expressionClassName
        }:${metaDataExpression.lineNumber},${metaDataExpression.columnNumber},${metaDataExpression.lastLineNumber},${
            metaDataExpression.lastColumnNumber
        }) - ${metaDataExpression.restoredScriptCode}""")
    }

    @Override
    void statementBegin(MetaDataStatement metaDataStatement) {
        log("""STATEMENT BEGIN: ${metaDataStatement.className}.${metaDataStatement.methodName}(${
            metaDataStatement.statementClassName
        }:${metaDataStatement.lineNumber},${metaDataStatement.columnNumber},${metaDataStatement.lastLineNumber},${
            metaDataStatement.lastColumnNumber
        })""")
    }

    @Override
    void statementEnd(MetaDataStatement metaDataStatement) {
        log("""STATEMENT END: ${metaDataStatement.className}.${metaDataStatement.methodName}(${
            metaDataStatement.statementClassName
        }:${metaDataStatement.lineNumber},${metaDataStatement.columnNumber},${metaDataStatement.lastLineNumber},${
            metaDataStatement.lastColumnNumber
        })""")
    }

    @Override
    void methodEnd(MetaDataMethodNode metaDataMethodNode) {
        log("""METHOD END: ${metaDataMethodNode.className}.${metaDataMethodNode.methodName}(${
            metaDataMethodNode.lineNumber
        },${metaDataMethodNode.columnNumber},${metaDataMethodNode.lastLineNumber},${
            metaDataMethodNode.lastColumnNumber
        })""")
    }

    @Override
    void methodBegin(MetaDataMethodNode metaDataMethodNode, Map<String, Object> methodArgumentMap) {
        log("""METHOD: ${metaDataMethodNode.className}.${metaDataMethodNode.methodName}(${
            metaDataMethodNode.lineNumber
        },${metaDataMethodNode.columnNumber},${metaDataMethodNode.lastLineNumber},${
            metaDataMethodNode.lastColumnNumber
        })""")
        if (astUtils.methodArgumentsPresent(methodArgumentMap)) {
            for (entry in methodArgumentMap.entrySet()) {
                if (entry.value != null) {
                    log("""ARGUMENT: ${entry.key}:${entry.value.getClass().getCanonicalName()}""")
                    log(entry.value.toString())
                } else {
                    log("""ARGUMENT: ${entry.key}: null""")
                }
            }
        }
    }

    void methodException(MetaDataMethodNode metaDataMethodNode, Map<String, Object> methodArgumentMap, Exception exception) {
        logError("""METHOD EXCEPTION: ${metaDataMethodNode.className}.${metaDataMethodNode.methodName}(${
            metaDataMethodNode.lineNumber
        },${metaDataMethodNode.columnNumber},${metaDataMethodNode.lastLineNumber},${
            metaDataMethodNode.lastColumnNumber
        })""")
        if (astUtils.methodArgumentsPresent(methodArgumentMap)) {
            for (entry in methodArgumentMap.entrySet()) {
                if (entry.value != null) {
                    logError("""ARGUMENT: ${entry.key}:${entry.value.getClass().getCanonicalName()}""")
                    logError(entry.value.toString())
                } else {
                    logError("""ARGUMENT: ${entry.key}: null""")
                }
            }
        }
        logException(exception)
    }

    @Override
    void handleControlStatement(String controlStatementClassName) {
        log("CONTROL STATEMENT: " + controlStatementClassName)
    }

    @Override
    Object handleExpressionResult(Object expressionEvaluationResult, MetaDataExpression metaDataExpression) {
        //Avoid logging empty results such as for void method call expressions
        if (expressionEvaluationResult != null) {
            log("""EXPRESSION VALUE (value class=${expressionEvaluationResult.getClass().getCanonicalName()}; ${metaDataExpression.className}.${metaDataExpression.methodName}(${
                metaDataExpression.expressionClassName
            }:${metaDataExpression.lineNumber},${metaDataExpression.columnNumber},${metaDataExpression.lastLineNumber},${
                metaDataExpression.lastColumnNumber
            })):""")
            if (expressionEvaluationResult instanceof List) {//workaround possible infinite loops with RoundRobin
                log(metaDataExpression.restoredScriptCode + " = " + expressionEvaluationResult.toArray().toString())
            } else {
                log(metaDataExpression.restoredScriptCode + " = " + TraceSerializer.toString(expressionEvaluationResult))
            }
        }
        return expressionEvaluationResult
    }

    @Override
    Exception handleException(Exception exception, MetaDataASTNode metaDataASTNode) {
        logError("""EXPRESSION EVALUATION EXCEPTION: ${metaDataASTNode.toString()}""")
        return exception
    }

    void handleMethodResult(Object methodResult) {
        log("METHOD RESULT:")
        if (methodResult != null) {
            log(methodResult.getClass().getCanonicalName())
            if (methodResult instanceof List) {
                log(methodResult.toArray().toString())
            } else {
                log(TraceSerializer.toString(methodResult))
            }
        }
    }

    @CompileDynamic
    void logException(Exception exception) {
        if (exception.isLoggedByBlackBox != true) {
            logError("""EXCEPTION (first occurrence):""")
            exception.uuid = UUID.randomUUID().toString()
            logError(exception.uuid)
            logError(new ExceptionUtils().stacktrace(exception))
            exception.isLoggedByBlackBox = true
        } else {
            logError("""EXCEPTION (additional occurrence):""")
            logError(exception.uuid)
        }
    }

}
