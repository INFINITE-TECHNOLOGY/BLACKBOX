package io.infinite.blackbox

import io.infinite.blackbox.generated.*
import io.infinite.carburetor.CarburetorEngine
import io.infinite.carburetor.ast.MetaDataExpression
import io.infinite.carburetor.ast.MetaDataMethodNode
import io.infinite.carburetor.ast.MetaDataStatement
import org.apache.commons.lang3.exception.ExceptionUtils
import org.codehaus.groovy.runtime.StackTraceUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar

/**
 * This class implements BlackBox Runtime API - Base functionality
 *
 */
class BlackBoxEngine extends CarburetorEngine {

    Logger internalLogger = LoggerFactory.getLogger(BlackBoxEngine.class)

    BlackBoxConfig blackBoxConfig

    XMLASTNode astNode

    BlackBoxEngine() {
        addShutdownHook {
            while (astNode != null) {
                executionClose()
            }
        }
    }

    static BlackBoxEngine getInstance(Logger automaticLog) {
        BlackBoxEngine blackBoxEngine = new BlackBoxFactory().getInstance()
        blackBoxEngine.internalLogger = automaticLog
        return blackBoxEngine
    }

    XMLGregorianCalendar getXMLGregorianCalendar(Date date = new Date()) {
        GregorianCalendar lGregorianCalendar = new GregorianCalendar()
        lGregorianCalendar.setTime(date)
        XMLGregorianCalendar lXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(lGregorianCalendar)
        return lXMLGregorianCalendar
    }

    void expressionExecutionOpen(MetaDataExpression metaDataExpression) {
        XMLExpression xmlExpression = new XMLExpression()
        xmlExpression.parentAstNode = astNode
        xmlExpression.setAstNodeList(new XMLASTNodeList())
        xmlExpression.setStartDateTime(getXMLGregorianCalendar())
        xmlExpression.setExpressionClassName(metaDataExpression.getExpressionClassName())
        xmlExpression.setRestoredScriptCode(metaDataExpression.getRestoredScriptCode())
        xmlExpression.setColumnNumber(metaDataExpression.getColumnNumber() as BigInteger)
        xmlExpression.setLastColumnNumber(metaDataExpression.getLastColumnNumber() as BigInteger)
        xmlExpression.setLineNumber(metaDataExpression.getLineNumber() as BigInteger)
        xmlExpression.setLastLineNumber(metaDataExpression.getLastLineNumber() as BigInteger)
        xmlExpression.setSourceNodeName(metaDataExpression.getSourceNodeName())
        astNode.getAstNodeList().getAstNode().add(xmlExpression)
        astNode = xmlExpression
    }

    @Override
    Object handleExpressionEvaluationResult(Object expressionEvaluationResult) {
        //Avoid logging empty results such as for void method call expressions
        if (expressionEvaluationResult != null) {
            XMLObject xmlObject = new XMLObject()
            xmlObject.setClassName(expressionEvaluationResult.getClass().getCanonicalName())
            if (expressionEvaluationResult instanceof List) {//workaround possible infinite loops with RoundRobin
                xmlObject.setValue(expressionEvaluationResult.toArray().toString())
            } else {
                xmlObject.setValue(TraceSerializer.toString(expressionEvaluationResult))
            }
            astNode.setExpressionValue(xmlObject)
        }
        return expressionEvaluationResult
    }

    void statementExecutionOpen(MetaDataStatement metaDataStatement) {
        XMLStatement xmlStatement = new XMLStatement()
        xmlStatement.parentAstNode = astNode
        xmlStatement.setAstNodeList(new XMLASTNodeList())
        xmlStatement.setStartDateTime(getXMLGregorianCalendar())
        xmlStatement.setStatementClassName(metaDataStatement.getStatementClassName())
        xmlStatement.setRestoredScriptCode(metaDataStatement.getRestoredScriptCode())
        xmlStatement.setColumnNumber(metaDataStatement.getColumnNumber() as BigInteger)
        xmlStatement.setLastColumnNumber(metaDataStatement.getLastColumnNumber() as BigInteger)
        xmlStatement.setLineNumber(metaDataStatement.getLineNumber() as BigInteger)
        xmlStatement.setLastLineNumber(metaDataStatement.getLastLineNumber() as BigInteger)
        xmlStatement.setSourceNodeName(metaDataStatement.getSourceNodeName())
        astNode.getAstNodeList().getAstNode().add(xmlStatement)
        astNode = xmlStatement
    }

    void methodExecutionOpen(MetaDataMethodNode metaDataMethodNode, Map<String, Object> methodArgumentMap) {
        if (astNode == null) {
            initRootAstNode()
        }
        XMLMethodNode xmlMethodNode = new XMLMethodNode()
        xmlMethodNode.parentAstNode = astNode
        xmlMethodNode.setAstNodeList(new XMLASTNodeList())
        xmlMethodNode.setStartDateTime(getXMLGregorianCalendar())
        xmlMethodNode.setMethodName(metaDataMethodNode.getMethodName())
        xmlMethodNode.setClassName(metaDataMethodNode.getPackageName()+ "." + metaDataMethodNode.getClassSimpleName())
        xmlMethodNode.setColumnNumber(metaDataMethodNode.getColumnNumber() as BigInteger)
        xmlMethodNode.setLastColumnNumber(metaDataMethodNode.getLastColumnNumber() as BigInteger)
        xmlMethodNode.setLineNumber(metaDataMethodNode.getLineNumber() as BigInteger)
        xmlMethodNode.setLastLineNumber(metaDataMethodNode.getLastLineNumber() as BigInteger)
        xmlMethodNode.setArgumentList(new XMLArgumentList())
        for (methodArgumentName in methodArgumentMap.keySet()) {
            XMLArgument xmlArgument = new XMLArgument()
            xmlArgument.setArgumentClassName(methodArgumentMap.get(methodArgumentName).getClass().getCanonicalName())
            xmlArgument.setArgumentName(methodArgumentName)
            xmlArgument.setArgumentValue(methodArgumentMap.get(methodArgumentName).toString())
            xmlMethodNode.getArgumentList().getArgument().add(xmlArgument)
        }
        astNode.getAstNodeList().getAstNode().add(xmlMethodNode)
        astNode = xmlMethodNode
    }

    void executionClose() {
        astNode = astNode.parentAstNode
    }

    void handleControlStatement(String iStatementName) {
        switch (iStatementName) {
            case "ReturnStatement":
                while (!(astNode instanceof XMLMethodNode || (astNode instanceof XMLExpression && astNode.getExpressionClassName() == "ClosureExpression"))) {
                    executionClose()
                }
                break
            case "BreakStatement":
                while (!(astNode instanceof XMLStatement && ["DoWhileStatement", "ForStatement", "WhileStatement", "SwitchStatement"].contains(astNode.getStatementClassName()))) {
                    executionClose()
                }
                break
            case "ContinueStatement":
                while (!(astNode instanceof XMLStatement && ["DoWhileStatement", "ForStatement", "WhileStatement"].contains(astNode.getStatementClassName()))) {
                    executionClose()
                }
                break
            case "ThrowStatement":
                while (!(astNode instanceof XMLMethodNode || (astNode instanceof XMLStatement && astNode.getStatementClassName() == "TryCatchStatement"))) {
                    executionClose()
                }
                break
        }
    }

    void initRootAstNode() {
        astNode = new XMLASTNode()
        astNode.setAstNodeList(new XMLASTNodeList())
        astNode.setStartDateTime(getXMLGregorianCalendar())
    }

    void handleMethodResult(Object methodResult) {
        XMLObject xmlMethodResult = new XMLObject()
        xmlMethodResult.setValue(methodResult.toString())
        xmlMethodResult.setClassName(methodResult.getClass().getCanonicalName())
        ((XMLMethodNode) astNode).setMethodResult(xmlMethodResult)
    }

    void exception(Exception exception) {
        while (!(astNode instanceof XMLMethodNode)) {
            executionClose()
        }
        ErrorLoggingStrategy errorLoggingStrategy = ErrorLoggingStrategy.valueOf(blackBoxConfig.runtime.strategy)
        switch (errorLoggingStrategy) {
            case ErrorLoggingStrategy.FULL_THEN_REFERENCE:
                XMLExceptionReference xmlException
                if (exception.isLoggedByBlackBox != true) {
                    xmlException = new XMLException()
                    xmlException.setExceptionStackTrace(ExceptionUtils.getStackTrace(new StackTraceUtils().sanitize(exception)))
                    xmlException.setExceptionUid(UUID.randomUUID().toString())
                    xmlException.setIsAlreadyLogged(false)
                    exception.isLoggedByBlackBox = true
                    exception.uuid = xmlException.getExceptionUid()
                } else {
                    xmlException = new XMLExceptionReference()
                    xmlException.setExceptionUid(exception.uuid)
                    xmlException.setIsAlreadyLogged(true)
                }
                xmlException.setExceptionDateTime(getXMLGregorianCalendar())
                ((XMLMethodNode) astNode).setException(xmlException)
                break
            case ErrorLoggingStrategy.ALWAYS_REFERENCE:
                XMLExceptionReference xmlException = new XMLExceptionReference()
                if (exception.isLoggedByBlackBox != true) {
                    xmlException.setExceptionUid(UUID.randomUUID().toString())
                    xmlException.setIsAlreadyLogged(false)
                    exception.isLoggedByBlackBox = true
                    exception.uuid = xmlException.getExceptionUid()
                } else {
                    xmlException.setExceptionUid(exception.uuid)
                    xmlException.setIsAlreadyLogged(true)
                }
                xmlException.setExceptionDateTime(getXMLGregorianCalendar())
                ((XMLMethodNode) astNode).setException(xmlException)
                break
            case ErrorLoggingStrategy.ALWAYS_FULL:
                XMLException xmlException = new XMLException()
                if (exception.isLoggedByBlackBox != true) {
                    xmlException.setExceptionStackTrace(ExceptionUtils.getStackTrace(new StackTraceUtils().sanitize(exception)))
                    xmlException.setExceptionUid(UUID.randomUUID().toString())
                    xmlException.setIsAlreadyLogged(false)
                    exception.isLoggedByBlackBox = true
                    exception.uuid = xmlException.getExceptionUid()
                } else {
                    xmlException.setExceptionStackTrace(ExceptionUtils.getStackTrace(new StackTraceUtils().sanitize(exception)))
                    xmlException.setExceptionUid(exception.uuid)
                    xmlException.setIsAlreadyLogged(true)
                }
                xmlException.setExceptionDateTime(getXMLGregorianCalendar())
                ((XMLMethodNode) astNode).setException(xmlException)
                break
            case ErrorLoggingStrategy.FULL_THEN_NOTHING:
                XMLException xmlException = new XMLException()
                if (exception.isLoggedByBlackBox != true) {
                    xmlException.setExceptionStackTrace(ExceptionUtils.getStackTrace(new StackTraceUtils().sanitize(exception)))
                    xmlException.setExceptionUid(UUID.randomUUID().toString())
                    xmlException.setIsAlreadyLogged(false)
                    exception.isLoggedByBlackBox = true
                    exception.uuid = xmlException.getExceptionUid()
                    xmlException.setExceptionDateTime(getXMLGregorianCalendar())
                    ((XMLMethodNode) astNode).setException(xmlException)
                }
                break
            case ErrorLoggingStrategy.REFERENCE_THEN_NOTHING:
                XMLExceptionReference xmlException = new XMLExceptionReference()
                if (exception.isLoggedByBlackBox != true) {
                    xmlException.setExceptionUid(UUID.randomUUID().toString())
                    xmlException.setIsAlreadyLogged(false)
                    exception.isLoggedByBlackBox = true
                    exception.uuid = xmlException.getExceptionUid()
                    xmlException.setExceptionDateTime(getXMLGregorianCalendar())
                    ((XMLMethodNode) astNode).setException(xmlException)
                }
                break
            case ErrorLoggingStrategy.NOTHING:
                break
            default:
                this.internalLogger.warn("Unsupported/undefined BlackBox Strategy: " + errorLoggingStrategy)
        }
        XMLStandaloneException xmlStandaloneException = new XMLStandaloneException()
        XMLMethodNode xmlMethodNode = astNode as XMLMethodNode
        xmlStandaloneException.setArgumentList(xmlMethodNode.getArgumentList())
        xmlStandaloneException.setClassName(xmlMethodNode.getClassName())
        xmlStandaloneException.setException(xmlMethodNode.getException())
        xmlStandaloneException.setMethodName(xmlMethodNode.getMethodName())
        xmlStandaloneException.setAstNodeList(xmlMethodNode.getAstNodeList())
        xmlStandaloneException.setColumnNumber(xmlMethodNode.getColumnNumber())
        xmlStandaloneException.setLastColumnNumber(xmlMethodNode.getLastColumnNumber())
        xmlStandaloneException.setLastLineNumber(xmlMethodNode.getLastLineNumber())
        xmlStandaloneException.setLineNumber(xmlMethodNode.getLineNumber())
        xmlStandaloneException.setRestoredScriptCode(xmlMethodNode.getRestoredScriptCode())
        xmlStandaloneException.setSourceNodeName(xmlMethodNode.getSourceNodeName())
        xmlStandaloneException.setStartDateTime(xmlMethodNode.getStartDateTime())
        xmlMethodNode.standaloneException = xmlStandaloneException
    }

}
