package io.infinite.blackbox


import io.infinite.blackbox.generated.*
import org.apache.commons.lang3.exception.ExceptionUtils

import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar

/**
 * This class implements BlackBox Runtime API - Base functionality
 *
 */
class BlackBoxEngine {

    /**
     * Placeholder for Thread-specific instance of BlackBoxEngine
     *
     * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#thread-safety">BlackBox Blueprint - Thread Safety</a>
     *
     */
    public static ThreadLocal blackBoxEngineThreadLocal = new ThreadLocal()

    /**
     * This is current active node of runtime stack managed by BlackBoxEngine.
     *
     */
    XMLASTNode astNode

    /**
     * Default constructor adds shutdown hook to close any active runtime stack AST nodes.
     *
     */
    BlackBoxEngine() {
        addShutdownHook {
            //todo: possibly need to set this thread name to parent thread name for proper sifting appender file selection
            Thread.currentThread().setName("BlackBoxEngine Shutdown Hook " + Thread.currentThread().getId())
            while (astNode != null) {
                executionClose()
            }
        }
    }

    /**
     *
     * This is the central method to get thread-specific instance of BlackBoxEngine.
     * <br/>
     * BlackBoxEngine implementation is returned as per "blackbox.mode" system property.
     * <br/>
     * Default implementation (operation mode) is: Emergency.
     *
     * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#operating-modes">BlackBox Blueprint - Operating Modes</a>
     * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#thread-safety">BlackBox Blueprint - Thread Safety</a>
     *
     * @return BlackBoxEngine instance implementation as per "blackbox.mode" System property.
     */
    static BlackBoxEngine getInstance() {
        BlackBoxEngine blackBoxEngine = blackBoxEngineThreadLocal.get() as BlackBoxEngine
        if (blackBoxEngine == null) {
            XMLASTNode.getMetaClass().parentAstNode = null
            Throwable.getMetaClass().isLoggedByBlackBox = null
            if (System.getProperty("blackbox.mode") == BlackBoxMode.SEQUENTIAL.value()) {
                blackBoxEngine = new BlackBoxEngineSequential()
            } else if (System.getProperty("blackbox.mode") == BlackBoxMode.HIERARCHICAL.value()) {
                blackBoxEngine = new BlackBoxEngineHierarchical()
            } else {
                blackBoxEngine = new BlackBoxEngineEmergency()
            }
            blackBoxEngineThreadLocal.set(blackBoxEngine)
        }
        return blackBoxEngine
    }

    /**
     * Utility method to get datetime for JAXB generated classes
     * @param date if specified - will set the calendar to this date
     * @return XMLGregorianCalendar with specified or current datetime
     */
    static XMLGregorianCalendar getXMLGregorianCalendar(Date date = new Date()) {
        GregorianCalendar lGregorianCalendar = new GregorianCalendar()
        lGregorianCalendar.setTime(date)
        XMLGregorianCalendar lXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(lGregorianCalendar)
        return lXMLGregorianCalendar
    }

    /**
     *
     * Used for DeclarationExpressions only.<br/>
     * Precedes DeclarationExpression.<br/>
     * Logs the beginning of DeclarationExpression. <br/>
     * Sets current astNode to new expression.
     *
     * @param iExpressionName expression Class name normally always DeclarationExpression.
     * @param iRestoredScriptCode expression code.
     * @param iColumnNumber Start column number of the ASTNode - you can use it to navigate into your code.
     * @param iLastColumnNumber Ending column number of the ASTNode - you can use it to navigate into your code.
     * @param iLineNumber Start line number of the ASTNode - you can use it to navigate into your code.
     * @param iLastLineNumber Ending line number of the ASTNode - you can use it to navigate into your code.
     * @param iNodeSourceName Standalone ASTNode such as BlockStatement has no awareness to where it belongs.
     * However BlackBox adds a useful visibility to help to understand that e.g. BloackStatement is originating from "IfStatement:elseBlock".
     * Format of this field is "Class name:field name" of the source variable where this AST Node is referenced.
     */
    void expressionExecutionOpen(String iExpressionName, String iRestoredScriptCode, Integer iColumnNumber, Integer iLastColumnNumber, Integer iLineNumber, Integer iLastLineNumber, String iNodeSourceName) {
        XMLExpression xmlExpression = new XMLExpression()
        xmlExpression.parentAstNode = astNode
        xmlExpression.setAstNodeList(new XMLASTNodeList())
        xmlExpression.setStartDateTime(getXMLGregorianCalendar())
        xmlExpression.setExpressionClassName(iExpressionName)
        xmlExpression.setRestoredScriptCode(iRestoredScriptCode)
        xmlExpression.setColumnNumber(iColumnNumber as BigInteger)
        xmlExpression.setLastColumnNumber(iLastColumnNumber as BigInteger)
        xmlExpression.setLineNumber(iLineNumber as BigInteger)
        xmlExpression.setLastLineNumber(iLastLineNumber as BigInteger)
        xmlExpression.setSourceNodeName(iNodeSourceName)
        astNode.getAstNodeList().getAstNode().add(xmlExpression)
        astNode = xmlExpression
    }

    /**
     * Used for standard expression logging.
     * <br/>
     * Logs the beginning of Expression.
     * <br/>
     * Executes expression via closure.call() <br/>
     * Logs the ending of Expression along with the result. <br/>
     * Sets current astNode to new expression.
     *
     * @param iExpressionName expression Class name
     * @param iRestoredScriptCode expression code.
     * @param iColumnNumber Start column number of the ASTNode - you can use it to navigate into your code.
     * @param iLastColumnNumber Ending column number of the ASTNode - you can use it to navigate into your code.
     * @param iLineNumber Start line number of the ASTNode - you can use it to navigate into your code.
     * @param iLastLineNumber Ending line number of the ASTNode - you can use it to navigate into your code.
     * @param iClosure Actual Expression wrapped in ClosureExpression.
     * @param iNodeSourceName Standalone ASTNode such as BlockStatement has no awareness to where it belongs.
     * However BlackBox adds a useful visibility to help to understand that e.g. BloackStatement is originating from "IfStatement:elseBlock".
     * Format of this field is "Class name:field name" of the source variable where this AST Node is referenced.
     * @return Actual Expression value after evaluation.
     */
    Object expressionEvaluation(String iExpressionName, String iRestoredScriptCode, Integer iColumnNumber, Integer iLastColumnNumber, Integer iLineNumber, Integer iLastLineNumber, Closure iClosure, String iNodeSourceName) {
        expressionExecutionOpen(iExpressionName, iRestoredScriptCode, iColumnNumber, iLastColumnNumber, iLineNumber, iLastLineNumber, iNodeSourceName)
        try {
            Object evaluationResult = iClosure.call()
            //Avoid logging empty results such as for void method call expressions
            if (evaluationResult != null) {
                XMLObject xmlObject = new XMLObject()
                xmlObject.setClassName(evaluationResult.getClass().getCanonicalName())
                xmlObject.setValue(evaluationResult.toString())
                astNode.setExpressionValue(xmlObject)
            }
            return evaluationResult
        } catch (Throwable throwable) {
            throw throwable
        } finally {
            executionClose()
        }
    }

    /**
     *
     * Used for standard Statement logging.<br/>
     * Precedes Statement execution.<br/>
     * Logs the beginning of Statement execution. <br/>
     * Sets current astNode to new statement.
     *
     * @param iStatementName Statement Class name
     * @param iRestoredScriptCode expression code.
     * @param iColumnNumber Start column number of the ASTNode - you can use it to navigate into your code.
     * @param iLastColumnNumber Ending column number of the ASTNode - you can use it to navigate into your code.
     * @param iLineNumber Start line number of the ASTNode - you can use it to navigate into your code.
     * @param iLastLineNumber Ending line number of the ASTNode - you can use it to navigate into your code.
     * @param iNodeSourceName Standalone ASTNode such as BlockStatement has no awareness to where it belongs.
     * However BlackBox adds a useful visibility to help to understand that e.g. BloackStatement is originating from "IfStatement:elseBlock".
     * Format of this field is "Class name:field name" of the source variable where this AST Node is referenced.
     */
    void statementExecutionOpen(String iStatementName, String iRestoredScriptCode, Integer iColumnNumber, Integer iLastColumnNumber, Integer iLineNumber, Integer iLastLineNumber, String iNodeSourceName) {
        XMLStatement xmlStatement = new XMLStatement()
        xmlStatement.parentAstNode = astNode
        xmlStatement.setAstNodeList(new XMLASTNodeList())
        xmlStatement.setStartDateTime(getXMLGregorianCalendar())
        xmlStatement.setStatementClassName(iStatementName)
        xmlStatement.setRestoredScriptCode(iRestoredScriptCode)
        xmlStatement.setColumnNumber(iColumnNumber as BigInteger)
        xmlStatement.setLastColumnNumber(iLastColumnNumber as BigInteger)
        xmlStatement.setLineNumber(iLineNumber as BigInteger)
        xmlStatement.setLastLineNumber(iLastLineNumber as BigInteger)
        xmlStatement.setSourceNodeName(iNodeSourceName)
        astNode.getAstNodeList().getAstNode().add(xmlStatement)
        astNode = xmlStatement
    }

    /**
     * Logs Method invocation start along with its arguments.
     *
     * @param iClassSimpleName Method declaring class simple name
     * @param iPackageName Method declaring class package name
     * @param iMethodName Method name
     * @param iColumnNumber Start column number of the ASTNode - you can use it to navigate into your code.
     * @param iLastColumnNumber Ending column number of the ASTNode - you can use it to navigate into your code.
     * @param iLineNumber Start line number of the ASTNode - you can use it to navigate into your code.
     * @param iLastLineNumber Ending line number of the ASTNode - you can use it to navigate into your code.
     * @param methodArgumentMap Method arguments (at the moment when exception is occurred! NOT before method invocation. Unserialized yet arguments.
     */
    void methodExecutionOpen(
            String iClassSimpleName,
            String iPackageName,
            String iMethodName,
            Integer iColumnNumber,
            Integer iLastColumnNumber,
            Integer iLineNumber,
            Integer iLastLineNumber,
            Map<String, Object> methodArgumentMap
    ) {
        if (astNode == null) {
            initRootAstNode()
        }
        XMLMethodNode xmlMethodNode = new XMLMethodNode()
        xmlMethodNode.parentAstNode = astNode
        xmlMethodNode.setAstNodeList(new XMLASTNodeList())
        xmlMethodNode.setStartDateTime(getXMLGregorianCalendar())
        xmlMethodNode.setMethodName(iMethodName)
        xmlMethodNode.setClassName(iPackageName + "." + iClassSimpleName)
        xmlMethodNode.setColumnNumber(iColumnNumber as BigInteger)
        xmlMethodNode.setLastColumnNumber(iLastColumnNumber as BigInteger)
        xmlMethodNode.setLineNumber(iLineNumber as BigInteger)
        xmlMethodNode.setLastLineNumber(iLastLineNumber as BigInteger)
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

    /**
     * Closes any active current astNode and sets current astNode to its parent.
     */
    void executionClose() {
        astNode = astNode.parentAstNode
    }

    /**
     * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#control-statement-transformation">BlackBox Blueprint - Control Statement Transformation</a>
     * Used only for Control Statement logging (return, continue, break, throw).
     * Precedes Control Statement.<br/>
     * Logs the bypassing (closing) of AST Nodes until first AST Node of type applicable to Control Statement scope is encountered.<br/>
     * Return: until either Method or Closure becomes current active log node. <br/>
     * Break: until DoWhile, For, While or Switch becomes current active log node. <br/>
     * Continue: until DoWhile, For or While becomes current active log node. <br/>
     * Throw: until Method or TryCatch becomes current active log node<br/>
     *
     * @param iStatementName Statement Class name.
     * @param iRestoredScriptCode expression code.
     * @param iColumnNumber Start column number of the ASTNode - you can use it to navigate into your code.
     * @param iLastColumnNumber Ending column number of the ASTNode - you can use it to navigate into your code.
     * @param iLineNumber Start line number of the ASTNode - you can use it to navigate into your code.
     * @param iLastLineNumber Ending line number of the ASTNode - you can use it to navigate into your code.
     * @param iNodeSourceName Standalone ASTNode such as BlockStatement has no awareness to where it belongs.
     * However BlackBox adds a useful visibility to help to understand that e.g. BloackStatement is originating from "IfStatement:elseBlock".
     * Format of this field is "Class name:field name" of the source variable where this AST Node is referenced.
     */
    void preprocessControlStatement(String iStatementName, String iRestoredScriptCode, Integer iColumnNumber, Integer iLastColumnNumber, Integer iLineNumber, Integer iLastLineNumber, String iNodeSourceName) {
        statementExecutionOpen(iStatementName, iRestoredScriptCode, iColumnNumber, iLastColumnNumber, iLineNumber, iLastLineNumber, iNodeSourceName)
        executionClose()
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

    /**
     * Initializes root log AST Node.
     */
    void initRootAstNode() {
        astNode = new XMLASTNode()
        astNode.setAstNodeList(new XMLASTNodeList())
        astNode.setStartDateTime(getXMLGregorianCalendar())
    }

    /**
     * Runs the method code enclosed in iMethodClosure.<br/>
     * Sets result of method to result of this closure call.<br/>
     *
     *
     * @param iMethodClosure Method code to run.
     * @return Actual Method result
     */
    Object executeMethod(Closure iMethodClosure) {
        Object methodResult = iMethodClosure.call()
        XMLObject xmlMethodResult = new XMLObject()
        xmlMethodResult.setValue(methodResult.toString())
        xmlMethodResult.setClassName(methodResult.getClass().getCanonicalName())
        ((XMLMethodNode) astNode).setMethodResult(xmlMethodResult)
        return methodResult
    }

    /**
     * Logs method exception<br/>
     * Logs the bypassing (closing) of AST Nodes until MethodNode becomes active node.<br/>
     *
     * @param throwable
     */
    void exception(Throwable throwable) {
        //todo: log only 1 time
        //todo: log.error for exceptions
        XMLException xmlException = new XMLException()
        xmlException.setExceptionStackTrace(ExceptionUtils.getStackTrace(throwable))
        xmlException.setExceptionDateTime(getXMLGregorianCalendar())
        while (!(astNode instanceof XMLMethodNode)) {
            executionClose()
        }
        ((XMLMethodNode) astNode).setException(xmlException)
    }

}
