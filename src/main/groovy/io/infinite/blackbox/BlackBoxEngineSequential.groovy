package io.infinite.blackbox

import groovy.util.logging.Slf4j
import groovy.xml.XmlUtil
import io.infinite.blackbox.generated.XMLArgument
import io.infinite.blackbox.generated.XMLExpression
import io.infinite.blackbox.generated.XMLMethodNode
import io.infinite.blackbox.generated.XMLStatement

/**
 * This class implements BlackBox Runtime API - Sequential Mode functionality
 * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#runtime-engine-api">BlackBox Blueprint - Runtime Engine API</a>
 * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#real-time-sequential">BlackBox Blueprint - Sequential Operating Mode</a>
 *
 * <br/>
 * This class prints runtime AST Node execution in realitime.
 *
 */
@Slf4j
class BlackBoxEngineSequential extends BlackBoxEngine {

    Integer depth = 0

    private String getPad() {
        if (depth >= 0) {
            return "".padRight(depth*4)
        } else {
            return "!!!"
        }
    }

    /**
     * Logback call to perform actual log printing.
     */
    private void log(String iText) {
        //todo: break multiline text into separate log calls
        log.debug(getPad() + iText)
    }

    /**
     * In addition to base BlackBoxEngine behavior, <br/>
     * - prints opening tags of Expression.
     */
    @Override
    void expressionExecutionOpen(String iExpressionName, String iRestoredScriptCode, Integer iColumnNumber, Integer iLastColumnNumber, Integer iLineNumber, Integer iLastLineNumber, String iNodeSourceName) {
        super.expressionExecutionOpen(iExpressionName, iRestoredScriptCode, iColumnNumber, iLastColumnNumber, iLineNumber, iLastLineNumber, iNodeSourceName)
        log("""<astNode xsi:type="Expression" expressionClassName="${astNode.getExpressionClassName()}" startDateTime="${astNode.startDateTime.toXMLFormat()}" sourceNodeName="${astNode.getSourceNodeName()}" lineNumber="${astNode.getLineNumber()}" columnNumber="${astNode.getColumnNumber()}" lastLineNumber="${astNode.getLastLineNumber()}" lastColumnNumber="${astNode.getLastColumnNumber()}">""")
        depth++
        log("""<restoredScriptCode>${XmlUtil.escapeXml(astNode.getRestoredScriptCode())}</restoredScriptCode>""")
        log("""<astNodeList>""")
        depth++
    }

    /**
     * In addition to base BlackBoxEngine behavior, <br/>
     * - prints opening tags of Statement.
     */
    @Override
    void statementExecutionOpen(String iStatementName, String iRestoredScriptCode, Integer iColumnNumber, Integer iLastColumnNumber, Integer iLineNumber, Integer iLastLineNumber, String iNodeSourceName) {
        super.statementExecutionOpen(iStatementName, iRestoredScriptCode, iColumnNumber, iLastColumnNumber, iLineNumber, iLastLineNumber, iNodeSourceName)
        log("""<astNode xsi:type="Statement" statementClassName="${astNode.getStatementClassName()}" startDateTime="${astNode.startDateTime.toXMLFormat()}" sourceNodeName="${astNode.getSourceNodeName()}" lineNumber="${astNode.getLineNumber()}" columnNumber="${astNode.getColumnNumber()}" lastLineNumber="${astNode.getLastLineNumber()}" lastColumnNumber="${astNode.getLastColumnNumber()}">""")
        depth++
        log("""<restoredScriptCode>${XmlUtil.escapeXml(astNode.getRestoredScriptCode())}</restoredScriptCode>""")
        log("""<astNodeList>""")
        depth++
    }

    /**
     * In addition to base BlackBoxEngine behavior, <br/>
     * - prints closing tags of MethodNode.
     */
    @Override
    void methodExecutionOpen(String iClassSimpleName, String iPackageName, String iMethodName, Integer iColumnNumber, Integer iLastColumnNumber, Integer iLineNumber, Integer iLastLineNumber, Map<String, Object> methodArgumentMap) {
        super.methodExecutionOpen(iClassSimpleName, iPackageName, iMethodName, iColumnNumber, iLastColumnNumber, iLineNumber, iLastLineNumber, methodArgumentMap)
        log("""<astNode xsi:type="MethodNode" methodName="${astNode.getMethodName()}" className="${astNode.getClassName()}" startDateTime="${astNode.startDateTime.toXMLFormat()}" lineNumber="${astNode.getLineNumber()}" columnNumber="${astNode.getColumnNumber()}" lastLineNumber="${astNode.getLastLineNumber()}" lastColumnNumber="${astNode.getLastColumnNumber()}">""")
        depth++
        if (BlackBoxTransformation.methodArgumentsPresent(astNode.getArgumentList().getArgument())) {
            log("""<argumentList>""")
            depth++
            for (XMLArgument xmlArgument in astNode.getArgumentList().getArgument()) {
                log("""<argument argumentClassName="${xmlArgument.getArgumentClassName()}" argumentName="${xmlArgument.getArgumentName()}">""")
                depth++
                log("""<argumentValue>${XmlUtil.escapeXml(xmlArgument.getArgumentValue())}</argumentValue>""")
                depth--
                log("""</argument>""")
            }
            depth--
            log("""</argumentList>""")
        }
        log("""<astNodeList>""")
        depth++
    }

    /**
     * In addition to base BlackBoxEngine behavior, <br/>
     * - prints closing tags of active AST Node.
     */
    @Override
    void executionClose() {
        if (astNode.parentAstNode != null) {
            astNode.parentAstNode.getAstNodeList().getAstNode().remove(astNode)
        }
        switch(astNode) {
            case XMLExpression:
                depth--
                log("""</astNodeList>""")
                if (astNode.getExpressionValue() != null) {
                    log("""<expressionValue className="${astNode.getExpressionValue().getClassName()}">""")
                    depth++
                    log("""<value>${XmlUtil.escapeXml(astNode.getExpressionValue().getValue())}</value>""")
                    depth--
                    log("""</expressionValue>""")
                }
                depth--
                log("""</astNode>""")
                break
            case XMLStatement:
                depth--
                log("""</astNodeList>""")
                depth--
                log("""</astNode>""")
                break
            case XMLMethodNode:
                depth--
                log("""</astNodeList>""")
                if (astNode.getMethodResult() != null) {
                    log("""<methodResult className="${astNode.getMethodResult().getClassName()}">""")
                    depth++
                    log("""<value>${XmlUtil.escapeXml(astNode.getMethodResult().getValue())}</value>""")
                    depth--
                    log("""</methodResult>""")
                }
                if (astNode.getException() != null) {
                    log("""<exception exceptionDateTime="${astNode.getException().getExceptionDateTime().toXMLFormat()}">""")
                    depth++
                    log("""<exceptionStackTrace>${XmlUtil.escapeXml(astNode.getException().getExceptionStackTrace())}</exceptionStackTrace>""")
                    depth--
                    log("""</exception>""")
                }
                depth--
                log("""</astNode>""")
                break
            default:
                depth--
                log("""</astNodeList>""")
                depth--
                log("""</rootAstNode>""")
                break
        }
        super.executionClose()
    }

    /**
     * In addition to base BlackBoxEngine behavior, <br/>
     * - prints rootAstNode XML.
     */
    @Override
    void initRootAstNode() {
        super.initRootAstNode()
        log("""<rootAstNode startDateTime="${astNode.startDateTime.toXMLFormat()}" xmlns="https://i-t.io/logging/groovy/2_x_x/BlackBox.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">""")
        depth++
        log("""<astNodeList>""")
        depth++
    }
}
