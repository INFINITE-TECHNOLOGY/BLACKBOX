package io.infinite.blackbox

import groovy.xml.XmlUtil
import io.infinite.blackbox.generated.*
import io.infinite.supplies.ast.metadata.MetaDataExpression
import io.infinite.supplies.ast.metadata.MetaDataMethodNode
import io.infinite.supplies.ast.metadata.MetaDataStatement

/**
 * This class implements BlackBox Runtime API - Sequential Mode functionality
 * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki#runtime-engine-api">BlackBox Blueprint - Runtime Engine API</a>
 * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki#real-time-sequential">BlackBox Blueprint - Sequential Operating Mode</a>
 *
 * <br/>
 * This class prints runtime AST Node execution in realitime.
 *
 */
class BlackBoxEngineSequential extends BlackBoxEngine {

    Integer depth = 0

    private String getPad() {
        if (depth >= 0) {
            return "".padRight(depth * 4)
        } else {
            return "!!!"
        }
    }

    /**
     * Logback call to perform actual log printing.
     */
    private void log(String iText) {
        //todo: break multiline text into separate log calls
        internalLogger.debug(getPad() + iText)
    }

    /**
     * Logback call to perform actual log printing.
     */
    private void logError(String iText) {
        //todo: break multiline text into separate log calls
        internalLogger.error(iText)
    }

    /**
     * In addition to base BlackBoxEngine behavior, <br/>
     * - prints opening tags of Expression.
     */
    @Override
    void expressionExecutionOpen(MetaDataExpression metaDataExpression) {
        super.expressionExecutionOpen(metaDataExpression)
        log("""<astNode xsi:type="Expression" expressionClassName="${
            astNode.getExpressionClassName()
        }" startDateTime="${astNode.startDateTime.toXMLFormat()}" sourceNodeName="${
            astNode.getSourceNodeName()
        }" lineNumber="${astNode.getLineNumber()}" columnNumber="${astNode.getColumnNumber()}" lastLineNumber="${
            astNode.getLastLineNumber()
        }" lastColumnNumber="${astNode.getLastColumnNumber()}">""")
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
    void statementExecutionOpen(MetaDataStatement metaDataStatement) {
        super.statementExecutionOpen(metaDataStatement)
        log("""<astNode xsi:type="Statement" statementClassName="${astNode.getStatementClassName()}" startDateTime="${
            astNode.startDateTime.toXMLFormat()
        }" sourceNodeName="${astNode.getSourceNodeName()}" lineNumber="${astNode.getLineNumber()}" columnNumber="${
            astNode.getColumnNumber()
        }" lastLineNumber="${astNode.getLastLineNumber()}" lastColumnNumber="${astNode.getLastColumnNumber()}">""")
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
    void methodExecutionOpen(MetaDataMethodNode metaDataMethodNode, Map<String, Object> methodArgumentMap) {
        super.methodExecutionOpen(metaDataMethodNode, methodArgumentMap)
        log("""<astNode xsi:type="MethodNode" methodName="${astNode.getMethodName()}" className="${
            astNode.getClassName()
        }" startDateTime="${astNode.startDateTime.toXMLFormat()}" lineNumber="${
            astNode.getLineNumber()
        }" columnNumber="${astNode.getColumnNumber()}" lastLineNumber="${
            astNode.getLastLineNumber()
        }" lastColumnNumber="${astNode.getLastColumnNumber()}">""")
        depth++
        if (methodArgumentsPresent(astNode.getArgumentList().getArgument())) {
            log("""<argumentList>""")
            depth++
            for (XMLArgument xmlArgument in astNode.getArgumentList().getArgument()) {
                log("""<argument argumentClassName="${xmlArgument.getArgumentClassName()}" argumentName="${
                    xmlArgument.getArgumentName()
                }">""")
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
        switch (astNode) {
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
                XMLMethodNode xmlMethodNode = astNode as XMLMethodNode
                if (xmlMethodNode.getException() != null) {
                    XMLStackTrace xmlException = xmlMethodNode.getException()
                    if (xmlException instanceof XMLStackTrace) {
                        log("""<exception xsi:type="Exception" exceptionDateTime="${
                            xmlException.getExceptionDateTime().toXMLFormat()
                        }" exceptionUid="${xmlException.getExceptionUid()}" isAlreadyLogged="${
                            xmlException.isIsAlreadyLogged()
                        }">""")
                        depth++
                        log("""<exceptionStackTrace>${
                            XmlUtil.escapeXml(xmlException.getExceptionStackTrace())
                        }</exceptionStackTrace>""")
                        depth--
                        log("""</exception>""")
                    } else if (xmlException instanceof XMLStackTrace) {
                        log("""<exception exceptionDateTime="${
                            xmlException.getExceptionDateTime().toXMLFormat()
                        }" exceptionUid="${xmlException.getExceptionUid()}" isAlreadyLogged="${
                            xmlException.isIsAlreadyLogged()
                        }"/>""")
                    }
                }
                if (xmlMethodNode.xmlException != null) {
                    logError("""<exception methodName="${xmlMethodNode.getMethodName()}" className="${
                        xmlMethodNode.getClassName()
                    }" startDateTime="${xmlMethodNode.startDateTime.toXMLFormat()}" lineNumber="${
                        xmlMethodNode.getLineNumber()
                    }" columnNumber="${xmlMethodNode.getColumnNumber()}" lastLineNumber="${
                        xmlMethodNode.getLastLineNumber()
                    }" lastColumnNumber="${xmlMethodNode.getLastColumnNumber()}">""")
                    if (methodArgumentsPresent(xmlMethodNode.getArgumentList().getArgument())) {
                        logError("""    <argumentList>""")
                        for (XMLArgument xmlArgument in xmlMethodNode.getArgumentList().getArgument()) {
                            logError("""        <argument argumentClassName="${
                                xmlArgument.getArgumentClassName()
                            }" argumentName="${xmlArgument.getArgumentName()}">""")
                            logError("""            <argumentValue>${
                                XmlUtil.escapeXml(xmlArgument.getArgumentValue())
                            }</argumentValue>""")
                            logError("""        </argument>""")
                        }
                        logError("""    </argumentList>""")
                    }
                    if (xmlMethodNode.xmlException.getException() != null) {
                        XMLStackTrace xmlException = xmlMethodNode.xmlException.getException()
                        if (xmlException instanceof XMLStackTrace) {
                            logError("""    <exception xsi:type="Exception" exceptionDateTime="${
                                xmlException.getExceptionDateTime().toXMLFormat()
                            }" exceptionUid="${xmlException.getExceptionUid()}" isAlreadyLogged="${
                                xmlException.isIsAlreadyLogged()
                            }">""")
                            depth++
                            logError("""        <exceptionStackTrace>${
                                XmlUtil.escapeXml(xmlException.getExceptionStackTrace())
                            }</exceptionStackTrace>""")
                            depth--
                            logError("""    </exception>""")
                        } else if (xmlException instanceof XMLStackTrace) {
                            logError("""    <exception exceptionDateTime="${
                                xmlException.getExceptionDateTime().toXMLFormat()
                            }" exceptionUid="${xmlException.getExceptionUid()}" isAlreadyLogged="${
                                xmlException.isIsAlreadyLogged()
                            }"/>""")
                        }
                    }
                    logError("""</exception>""")
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
        log("""<rootAstNode startDateTime="${
            astNode.startDateTime.toXMLFormat()
        }" xmlns="https://i-t.io/BlackBox/xsd/2_x_x/BlackBox.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">""")
        depth++
        log("""<astNodeList>""")
        depth++
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

}
