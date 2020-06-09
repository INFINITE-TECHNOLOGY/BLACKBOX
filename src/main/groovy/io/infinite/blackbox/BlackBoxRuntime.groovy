package io.infinite.blackbox

import groovy.transform.CompileDynamic
import groovy.util.logging.Slf4j
import io.infinite.supplies.ast.exceptions.ExceptionUtils
import io.infinite.supplies.ast.metadata.MetaDataMethodNode
import io.infinite.supplies.ast.other.ASTUtils
import org.slf4j.Logger

@Slf4j
/**
 * - Supports usage in static context (BlackBoxRuntime var is declared as static)
 * - Thread safe
 */
class BlackBoxRuntime {

    ASTUtils astUtils = new ASTUtils()

    static {
        staticInit()
    }

    @CompileDynamic
    static void staticInit() {
        Exception.getMetaClass().isLoggedByBlackBox = null
        Exception.getMetaClass().uuid = null
    }

    Logger internalLogger

    BlackBoxRuntime() {
    }

    BlackBoxRuntime(Logger internalLogger) {
        this.internalLogger = internalLogger
    }

    BlackBoxRuntime getInstance(Logger automaticLog) {
        return new BlackBoxRuntime(automaticLog)
    }

    private void log(String iText) {
        internalLogger.debug(iText)
    }

    private void logError(String iText) {
        internalLogger.error(iText)
    }

    void methodEnd(MetaDataMethodNode metaDataMethodNode) {
        log("""METHOD END: ${metaDataMethodNode.className}.${metaDataMethodNode.methodName}(${
            metaDataMethodNode.lineNumber
        },${metaDataMethodNode.columnNumber},${metaDataMethodNode.lastLineNumber},${
            metaDataMethodNode.lastColumnNumber
        })""")
    }

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

    void handleReturnStatement(String controlStatementClassName) {
        log("CONTROL STATEMENT: " + controlStatementClassName)
    }

    void logMethodResult(MetaDataMethodNode metaDataMethodNode, Object methodResult) {
        log("""METHOD RESULT: ${metaDataMethodNode.className}.${metaDataMethodNode.methodName}(${
            metaDataMethodNode.lineNumber
        },${metaDataMethodNode.columnNumber},${metaDataMethodNode.lastLineNumber},${
            metaDataMethodNode.lastColumnNumber
        })""")
        if (methodResult != null) {
            log(methodResult.getClass().getCanonicalName())
        }
        if (methodResult instanceof List) {
            log(methodResult.toArray().toString())
        } else {
            log(TraceSerializer.toString(methodResult))
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
