package io.infinite.blackbox

import groovy.inspect.swingui.AstNodeToScriptVisitor
import groovy.transform.ToString
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.exception.ExceptionUtils
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.tools.GeneralUtils
import org.codehaus.groovy.classgen.VariableScopeVisitor
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.codehaus.groovy.transform.sc.ListOfExpressionsExpression

/**
 * This class implements BlackBox Transformation Rules for @BlackBox annotation.
 *
 * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#transformation-rules">BlackBox Blueprint - Transformation Rules</a>
 *
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
@GroovyASTTransformation(
        phase = CompilePhase.SEMANTIC_ANALYSIS
)
@Slf4j
class BlackBoxTransformation extends AbstractASTTransformation {

    AnnotationNode annotationNode
    BlackBoxLevel blackBoxLevel

    /**
     * Visits method or constructor<br/>
     * Fixes variable scopes of modified code.
     *
     * @param iAstNodeArray
     * @param iSourceUnit
     */
    void visit(ASTNode[] iAstNodeArray, SourceUnit iSourceUnit) {
        try {
            ASTNode.getMetaClass().origCodeString = null
            ASTNode.getMetaClass().isTransformed = null
            init(iAstNodeArray, iSourceUnit)
            MethodNode methodNode = iAstNodeArray[1] as MethodNode
            String methodName = methodNode.getName()
            String className = methodNode.getDeclaringClass().getNameWithoutPackage()
            Thread.currentThread().setName("Compilation_$className.$methodName")
            annotationNode = iAstNodeArray[0] as AnnotationNode
            blackBoxLevel = getBlackBoxLevel(annotationNode, methodName)
            transformMethod(methodNode)
            new VariableScopeVisitor(sourceUnit, true).visitClass(methodNode.getDeclaringClass())//<<<<<<<<<
            log.debug(codeString(methodNode.getCode()))
        } catch (Throwable throwable) {
            log.error(ExceptionUtils.getStackTrace(throwable))
            throw throwable
        }
    }

    /**
     * Gets BlackBox Level from Annotation node.
     *
     * @param iAnnotationNode
     * @param iMethodName
     * @return
     */
    static BlackBoxLevel getBlackBoxLevel(ASTNode iAnnotationNode, String iMethodName) {
        AnnotationNode annotationNode = iAnnotationNode as AnnotationNode
        Expression memberExpression = annotationNode.getMember("blackBoxLevel")
        if (memberExpression instanceof PropertyExpression) {
            ConstantExpression constantExpression = memberExpression.getProperty() as ConstantExpression
            return constantExpression.getValue() as BlackBoxLevel
        } else {
            throw new Exception("Unsupported annotation member expression class: " + memberExpression.getClass().getCanonicalName() + " for method " + iMethodName)
        }
    }

    /**
     * Used for Standard Expression transformation. <br/>
     * Transforms any expression into MethodCallExpression to BlackBoxEngine instance -> expressionEvaluation method.
     *
     * @param iExpression
     * @param iSourceNodeName
     * @return
     */
    private static MethodCallExpression wrapExpressionIntoMethodCallExpression(Expression iExpression, iSourceNodeName) {
        ClosureExpression closureExpression = GeneralUtils.closureX(GeneralUtils.returnS(iExpression))
        closureExpression.setVariableScope(new VariableScope())
        MethodCallExpression methodCallExpression = GeneralUtils.callX(
                GeneralUtils.varX("automaticBlackBox"),
                "expressionEvaluation",
                GeneralUtils.args(
                        GeneralUtils.constX(iExpression.getClass().getSimpleName()),
                        GeneralUtils.constX(iExpression.origCodeString),
                        GeneralUtils.constX(iExpression.getColumnNumber()),
                        GeneralUtils.constX(iExpression.getLastColumnNumber()),
                        GeneralUtils.constX(iExpression.getLineNumber()),
                        GeneralUtils.constX(iExpression.getLastLineNumber()),
                        closureExpression,
                        GeneralUtils.constX(iSourceNodeName)
                )
        )
        methodCallExpression.copyNodeMetaData(iExpression)
        methodCallExpression.setSourcePosition(iExpression)
        methodCallExpression.isTransformed = true
        return methodCallExpression
    }

    /**
     * Transforms method or constructor according to BlackBox Transformation rules.
     *
     * @param iMethodNode
     */
    private void transformMethod(MethodNode iMethodNode) {
        List<MapEntryExpression> argumentMapEntryExpressionList = new ArrayList<>()
        if (methodArgumentsPresent(iMethodNode.getParameters())) {
            for (parameter in iMethodNode.getParameters()) {
                argumentMapEntryExpressionList.add(new MapEntryExpression(GeneralUtils.constX(parameter.getName()), GeneralUtils.varX(parameter.getName())))
            }
        }
        Statement blackBoxDeclaration = GeneralUtils.declS(
                GeneralUtils.varX("automaticBlackBox", ClassHelper.make(BlackBoxEngine.class)),
                GeneralUtils.callX(ClassHelper.make(BlackBoxEngine.class), "getInstance")
        )
        Statement methodExecutionOpen = new ExpressionStatement(
                GeneralUtils.callX(
                        GeneralUtils.varX("automaticBlackBox"),
                        "methodExecutionOpen",
                        GeneralUtils.args(
                                GeneralUtils.constX(iMethodNode.getDeclaringClass().getNameWithoutPackage()),
                                GeneralUtils.constX(iMethodNode.getDeclaringClass().getPackageName()),
                                GeneralUtils.constX(iMethodNode.getName()),
                                GeneralUtils.constX(iMethodNode.getColumnNumber()),
                                GeneralUtils.constX(iMethodNode.getLastColumnNumber()),
                                GeneralUtils.constX(iMethodNode.getLineNumber()),
                                GeneralUtils.constX(iMethodNode.getLastLineNumber()),
                                new MapExpression(
                                        argumentMapEntryExpressionList
                                )
                        )
                )
        )
        Statement logException = new ExpressionStatement(GeneralUtils.callX(GeneralUtils.varX("automaticBlackBox"), "exception", GeneralUtils.args(GeneralUtils.varX("automaticThrowable"))))
        if (blackBoxLevel.value() >= BlackBoxLevel.METHOD.value()) {
            //todo: serialize parameters before method execution
            iMethodNode.getCode().visit(new BlackBoxVisitor(this, blackBoxLevel))//<<<<<<<<<<<<<<VISIT<<<<<
            iMethodNode.setCode(
                    GeneralUtils.block(
                            blackBoxDeclaration,
                            methodExecutionOpen,
                            {
                                TryCatchStatement tryCatchStatement = new TryCatchStatement(
                                        {
                                            if (iMethodNode.isVoidMethod()) {
                                                return iMethodNode.getCode()
                                            } else {
                                                return new ExpressionStatement(GeneralUtils.callX(
                                                        GeneralUtils.varX("automaticBlackBox"),
                                                        "executeMethod",
                                                        GeneralUtils.args(GeneralUtils.closureX(iMethodNode.getCode()))
                                                ))
                                            }
                                        }.call() as Statement,
                                        new ExpressionStatement(GeneralUtils.callX(GeneralUtils.varX("automaticBlackBox"), "executionClose"))
                                )
                                tryCatchStatement.addCatch(
                                        GeneralUtils.catchS(GeneralUtils.param(ClassHelper.make(Throwable.class), "automaticThrowable"), GeneralUtils.block(logException, createThrowStatement()))
                                )
                                return tryCatchStatement
                            }.call() as TryCatchStatement
                    )
            )
        } else if (blackBoxLevel == BlackBoxLevel.METHOD_ERROR) {
            iMethodNode.setCode(
                    GeneralUtils.block(
                            {
                                TryCatchStatement tryCatchStatement = new TryCatchStatement(iMethodNode.getCode(), EmptyStatement.INSTANCE)
                                tryCatchStatement.addCatch(
                                        GeneralUtils.catchS(
                                                GeneralUtils.param(ClassHelper.make(Throwable.class), "automaticThrowable"),
                                                GeneralUtils.block(
                                                        blackBoxDeclaration,
                                                        methodExecutionOpen,
                                                        logException,
                                                        new ExpressionStatement(GeneralUtils.callX(GeneralUtils.varX("automaticBlackBox"), "executionClose")),
                                                        createThrowStatement()
                                                )
                                        )
                                )
                                return tryCatchStatement
                            }.call() as TryCatchStatement
                    )
            )
        }
    }

    private Statement createThrowStatement() {
        ThrowStatement throwStatement = GeneralUtils.throwS(GeneralUtils.varX("automaticThrowable"))
        throwStatement.setSourcePosition(annotationNode)
        return throwStatement
    }

    static Statement text2statement(String iCodeText) {
        List<ASTNode> resultingStatements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, true, iCodeText)
        return resultingStatements.first() as Statement
    }

    static String codeString(ASTNode iAstNode) {
        StringWriter stringWriter = new StringWriter()
        iAstNode.visit(new AstNodeToScriptVisitor(stringWriter))
        return stringWriter.getBuffer().toString().replace("\$", "")
    }

    static final Boolean methodArgumentsPresent(Object iArgs) {
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

    private static BlockStatement transformControlStatement(Statement iStatement, String iSourceNodeName) {
        BlockStatement blockStatement = GeneralUtils.block(new VariableScope())
        blockStatement.addStatement(text2statement("""automaticBlackBox.preprocessControlStatement("${
            iStatement.getClass().getSimpleName()
        }", \"\"\"${iStatement.origCodeString}\"\"\", ${iStatement.getColumnNumber()}, ${
            iStatement.getLastColumnNumber()
        }, ${
            iStatement.getLineNumber()
        }, ${iStatement.getLastLineNumber()}, "${iSourceNodeName}")"""))
        blockStatement.addStatement(iStatement)
        blockStatement.copyNodeMetaData(iStatement)
        blockStatement.setSourcePosition(iStatement)
        blockStatement.isTransformed = true
        return blockStatement
    }

    Statement transformStatement(Statement iStatement, String iSourceNodeName) {
        if (iStatement == null || iStatement instanceof EmptyStatement || iStatement.isTransformed == true) {
            return iStatement
        }
        if (blackBoxLevel.value() < BlackBoxLevel.STATEMENT.value() || iStatement instanceof BlockStatement || iStatement instanceof ExpressionStatement) {
            return iStatement
        }
        if (iStatement instanceof ReturnStatement || iStatement instanceof ContinueStatement || iStatement instanceof BreakStatement || iStatement instanceof ThrowStatement) {
            return transformControlStatement(iStatement, iSourceNodeName)
        }
        BlockStatement blockStatement = GeneralUtils.block(new VariableScope())
        blockStatement.addStatement(text2statement("""automaticBlackBox.statementExecutionOpen("${
            iStatement.getClass().getSimpleName()
        }", \"\"\"${iStatement.origCodeString}\"\"\", ${iStatement.getColumnNumber()}, ${
            iStatement.getLastColumnNumber()
        }, ${
            iStatement.getLineNumber()
        }, ${iStatement.getLastLineNumber()}, "${iSourceNodeName}")"""))
        blockStatement.addStatement(iStatement)
        blockStatement.addStatement(text2statement("automaticBlackBox.executionClose()"))
        blockStatement.copyNodeMetaData(iStatement)
        blockStatement.setSourcePosition(iStatement)
        blockStatement.isTransformed = true
        return blockStatement
    }

    private ListOfExpressionsExpression transformDeclarationExpression(DeclarationExpression iExpression, String iSourceNodeName) {
        ListOfExpressionsExpression listOfExpressionsExpression = new ListOfExpressionsExpression()
        Expression transformedRightExpression = transformExpression(iExpression.rightExpression as Expression, "DeclarationExpression:rightExpression")
        DeclarationExpression transformedDeclarationExpression = new DeclarationExpression(iExpression.leftExpression as Expression, iExpression.operation as Token, transformedRightExpression)
        MethodCallExpression expressionExecutionOpenMethodCallExpression = GeneralUtils.callX(
                GeneralUtils.varX("automaticBlackBox"),
                "expressionExecutionOpen",
                GeneralUtils.args(
                        GeneralUtils.constX(iExpression.getClass().getSimpleName()),
                        GeneralUtils.constX(iExpression.origCodeString),
                        GeneralUtils.constX(iExpression.getColumnNumber()),
                        GeneralUtils.constX(iExpression.getLastColumnNumber()),
                        GeneralUtils.constX(iExpression.getLineNumber()),
                        GeneralUtils.constX(iExpression.getLastLineNumber()),
                        GeneralUtils.constX(iSourceNodeName)
                )
        )
        MethodCallExpression expressionExecutionCloseMethodCallExpression = GeneralUtils.callX(
                GeneralUtils.varX("automaticBlackBox"),
                "executionClose"
        )
        listOfExpressionsExpression.addExpression(expressionExecutionOpenMethodCallExpression)
        listOfExpressionsExpression.addExpression(transformedDeclarationExpression)
        listOfExpressionsExpression.addExpression(expressionExecutionCloseMethodCallExpression)
        listOfExpressionsExpression.copyNodeMetaData(iExpression)
        listOfExpressionsExpression.setSourcePosition(iExpression)
        listOfExpressionsExpression.isTransformed = true
        return listOfExpressionsExpression
    }

    Expression transformExpression(Expression iExpression, String iSourceNodeName) {
        //see also: https://issues.apache.org/jira/browse/GROOVY-8834
        Expression transformedExpression = iExpression
        if (iExpression == null ||
                blackBoxLevel.value() < BlackBoxLevel.EXPRESSION.value() ||
                iExpression instanceof EmptyExpression ||
                iExpression instanceof MapEntryExpression ||
                iExpression instanceof ArgumentListExpression ||
                iExpression.isTransformed == true
        ) {
            return iExpression
        } else if (iExpression.getClass() == DeclarationExpression.class) {
            return transformDeclarationExpression(iExpression as DeclarationExpression, iSourceNodeName)
        } else if (iExpression.getClass() == BinaryExpression.class) {
            Expression transformedRightExpression = transformExpression(iExpression.rightExpression as Expression, "BinaryExpression:rightExpression")
            Expression transformedLeftExpression
            if (iExpression.operation.text == "=") {
                transformedLeftExpression = iExpression.leftExpression
            } else {
                transformedLeftExpression = transformExpression(iExpression.leftExpression as Expression, "BinaryExpression:leftExpression")
            }
            transformedExpression = new BinaryExpression(transformedLeftExpression as Expression, iExpression.operation as Token, transformedRightExpression)
        } else if (iExpression.getClass() == BitwiseNegationExpression.class) {
            transformedExpression = new BitwiseNegationExpression(transformExpression(iExpression.getExpression() as Expression, "BitwiseNegationExpression:expression"))
        } else if (iExpression.getClass() == NotExpression.class) {
            transformedExpression = new NotExpression(transformExpression(iExpression.getExpression() as Expression, "NotExpression:expression"))
        } else if (iExpression.getClass() == BooleanExpression.class) {
            transformedExpression = new BooleanExpression(transformExpression(iExpression.getExpression() as Expression, "BooleanExpression:expression"))
        } else if (iExpression.getClass() == CastExpression.class) {
            transformedExpression = new CastExpression(iExpression.getType(), transformExpression(iExpression.getExpression() as Expression, "ClassExpression:expression"))
        } else if (iExpression.getClass() == ConstructorCallExpression.class) {
            transformedExpression = new ConstructorCallExpression(iExpression.getType(), transformExpression(iExpression.getArguments() as Expression, "ConstructorCallExpression:arguments"))
        } else if (iExpression.getClass() == MethodPointerExpression.class) {
            transformedExpression = new MethodPointerExpression(transformExpression(iExpression.getExpression() as Expression, "MethodPointerExpression:expression"),
                    transformExpression(iExpression.getMethodName() as Expression, "MethodPointerExpression:methodName"))
        } else if (iExpression.getClass() == AttributeExpression.class) {
            transformedExpression = new AttributeExpression(transformExpression(iExpression.getObjectExpression() as Expression, "AttributeExpression:objectExpression"),
                    transformExpression(iExpression.getProperty() as Expression, "PropertyExpression:property"))
        } else if (iExpression.getClass() == PropertyExpression.class) {
            transformedExpression = new PropertyExpression(transformExpression(iExpression.getObjectExpression() as Expression, "PropertyExpression:objectExpression"),
                    transformExpression(iExpression.getProperty() as Expression, "PropertyExpression:property"))
        } else if (iExpression.getClass() == RangeExpression.class) {
            transformedExpression = new RangeExpression(transformExpression(iExpression.getFrom() as Expression, "RangeExpression:from"),
                    transformExpression(iExpression.getTo() as Expression, "RangeExpression:to"), iExpression.isInclusive() as Boolean)
        } else if (iExpression.getClass() == SpreadExpression.class) {
            transformedExpression = new SpreadExpression(transformExpression(iExpression.getExpression() as Expression, "SpreadExpression:expression"))
        } else if (iExpression.getClass() == SpreadMapExpression.class) {
            transformedExpression = new SpreadMapExpression(transformExpression(iExpression.getExpression() as Expression, "SpreadExpression:expression"))
        } else if (iExpression.getClass() == StaticMethodCallExpression.class) {
            transformedExpression = new StaticMethodCallExpression(iExpression.getOwnerType() as ClassNode, iExpression.getMethod() as String,
                    transformExpression(iExpression.getArguments() as Expression, "StaticMethodCallExpression:arguments"))
        } else if (iExpression.getClass() == ElvisOperatorExpression.class) {
            transformedExpression = new ElvisOperatorExpression(
                    transformExpression(iExpression.getTrueExpression() as Expression, "ElvisOperatorExpression:trueExpression"),
                    transformExpression(iExpression.getFalseExpression() as Expression, "ElvisOperatorExpression:falseExpression")
            )
        } else if (iExpression.getClass() == TernaryExpression.class) {
            transformedExpression = new TernaryExpression(
                    new BooleanExpression(transformExpression(iExpression.getBooleanExpression() as Expression, "TernaryExpression:booleanExpression")),
                    transformExpression(iExpression.getTrueExpression() as Expression, "TernaryExpression:trueExpression"),
                    transformExpression(iExpression.getFalseExpression() as Expression, "TernaryExpression:falseExpression")
            )
        } else if (iExpression.getClass() == UnaryMinusExpression.class) {
            transformedExpression = new UnaryMinusExpression(transformExpression(iExpression.getExpression() as Expression, "UnaryMinusExpression:expression"))
        } else if (iExpression.getClass() == UnaryPlusExpression.class) {
            transformedExpression = new UnaryPlusExpression(transformExpression(iExpression.getExpression() as Expression, "UnaryPlusExpression:expression"))
        }
        transformedExpression.isTransformed = true
        transformedExpression.copyNodeMetaData(iExpression)
        transformedExpression.setSourcePosition(iExpression)
        return wrapExpressionIntoMethodCallExpression(transformedExpression, iSourceNodeName)
    }

}
