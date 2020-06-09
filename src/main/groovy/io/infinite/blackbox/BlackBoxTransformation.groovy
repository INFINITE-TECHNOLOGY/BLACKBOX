package io.infinite.blackbox


import groovy.transform.ToString
import groovy.util.logging.Slf4j
import io.infinite.supplies.ast.metadata.MetaDataMethodNode
import io.infinite.supplies.ast.metadata.MetaDataStatement
import io.infinite.supplies.ast.other.ASTUtils
import jdk.internal.org.objectweb.asm.Opcodes
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.tools.GeneralUtils
import org.codehaus.groovy.classgen.VariableScopeVisitor
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC

@ToString(includeNames = true, includeFields = true, includePackage = false)
@GroovyASTTransformation(
        phase = CompilePhase.SEMANTIC_ANALYSIS
)
@Slf4j
class BlackBoxTransformation extends AbstractASTTransformation {

    private static Integer uniqueClosureParamCounter = 0

    boolean suppressExceptions

    AnnotationNode annotationNode

    BlackBoxLevel blackBoxLevel

    MethodNode methodNode

    ASTUtils astUtils = new ASTUtils()

    // MAIN ENTRY POINT \/\/\/\/\/\/\/\/
    void visit(ASTNode[] iAstNodeArray, SourceUnit iSourceUnit) {
        try {
            init(iAstNodeArray, iSourceUnit)
            if (iAstNodeArray[1] instanceof MethodNode) {
                AnnotationNode methodAnnotationNode = iAstNodeArray[0] as AnnotationNode
                visitMethod(iAstNodeArray[1] as MethodNode, methodAnnotationNode)
            } else if (iAstNodeArray[1] instanceof ClassNode) {
                AnnotationNode classAnnotationNode = iAstNodeArray[0] as AnnotationNode
                visitClassNode(iAstNodeArray[1] as ClassNode, classAnnotationNode)
            } else {
                throw new BlackBoxTransformationException(iAstNodeArray[1], "Unsupported Annotated Node; Only [Class, Method, Constructor] are supported.")
            }
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception)
            throw new BlackBoxTransformationException(iAstNodeArray[1], exception)
        }
    }
    // MAIN ENTRY POINT /\/\/\/\/\/\/\/\

    void visitClassNode(ClassNode classNode, AnnotationNode classAnnotationNode) {
        classNode.methods.each {
            visitMethod(it, it.getAnnotations(classAnnotationNode.getClassNode())[0] ?: classAnnotationNode)
        }
        classNode.declaredConstructors.each {
            visitMethod(it, it.getAnnotations(classAnnotationNode.getClassNode())[0] ?: classAnnotationNode)
        }
    }

    void visitMethod(MethodNode methodNode, AnnotationNode methodAnnotationNode) {
        if (methodNode.isAbstract() || excludeMethodNode(methodNode)) {
            return
        }
        uniqueClosureParamCounter = 0
        this.annotationNode = methodAnnotationNode
        this.methodNode = methodNode
        try {
            if (methodNode.getDeclaringClass().getOuterClass() != null) {
                throw new BlackBoxTransformationException(methodNode, "BlackBox currently does not support annotations in Inner Classes.")
            }
            if (astUtils.codeString(methodNode.getCode()).contains(runtimeVarName)) {
                throw new BlackBoxTransformationException(methodNode, "$runtimeVarName is already declared.")
            }
            mandatoryClassDeclarations(methodNode.getDeclaringClass())
            methodDeclarations(methodNode)
            String methodName = methodNode.getName()
            String className = methodNode.getDeclaringClass().getNameWithoutPackage()
            MDC.put("unitName", "CARBURETOR_$className.${methodName.replace("<", "").replace(">", "")}")
            blackBoxLevel = getAnnotationParameter("level", BlackBoxLevel.ERROR, methodAnnotationNode) as BlackBoxLevel
            getAnnotationParameters()
            transformMethod(methodNode)
            sourceUnit.AST.classes.each {
                new VariableScopeVisitor(sourceUnit, true).visitClass(it)
            }
            log.debug(astUtils.codeString(methodNode.getCode()))
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception)
            throw new BlackBoxTransformationException(methodNode, exception)
        }
    }

    Boolean excludeMethodNode(MethodNode methodNode) {
        return (methodNode.getName() == "toString")
    }


    void optionalDeclarations(ClassNode classNode) {
        if (classNode.getDeclaredField("automaticLog") == null) {
            classNode.addField("automaticLog",
                    Opcodes.ACC_FINAL | Opcodes.ACC_TRANSIENT | Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                    ClassHelper.make(Logger.class),
                    GeneralUtils.callX(
                            new ClassExpression(ClassHelper.make(LoggerFactory.class)),
                            "getLogger",
                            GeneralUtils.constX(classNode.getName())
                    ))
        }
    }


    Class getEngineFactoryClass() {
        return BlackBoxRuntime.class
    }


    Expression getEngineInitArgs() {
        GeneralUtils.args(GeneralUtils.fieldX(methodNode.declaringClass, "automaticLog"))
    }


    void methodDeclarations(MethodNode methodNode) {

    }


    void getAnnotationParameters() {
        suppressExceptions = getAnnotationParameter("suppressExceptions", false, annotationNode)
    }


    String getRuntimeVarName() {
        return "blackBoxRuntime"
    }

    Statement createThrowStatement() {
        if (!suppressExceptions) {
            ThrowStatement throwStatement = GeneralUtils.throwS(GeneralUtils.varX("automaticException"))
            throwStatement.setSourcePosition(annotationNode)
            return throwStatement
        } else {
            return new EmptyStatement()
        }
    }

    void mandatoryClassDeclarations(ClassNode classNode) {
        optionalDeclarations(classNode)
        if (classNode.getDeclaredField(runtimeVarName) == null) {
            classNode.addField(runtimeVarName,
                    Opcodes.ACC_FINAL | Opcodes.ACC_TRANSIENT | Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,//ACC_PUBLIC to workaround MissingPropertyException SpringBootApp$$EnhancerBySpringCGLIB$$<..>
                    ClassHelper.make(BlackBoxRuntime.class),
                    GeneralUtils.callX(
                            GeneralUtils.ctorX(ClassHelper.make(getEngineFactoryClass())),
                            getEngineFactoryMethodName(),
                            getEngineInitArgs()
                    )
            )
        }
    }

    String getMethodEndName() {
        "methodEnd"
    }

    String getEngineFactoryMethodName() {
        "getInstance"
    }

    Object getAnnotationParameter(String annotationName, Object defaultValue, AnnotationNode annotationNode) {
        Object result
        Expression memberExpression = annotationNode.getMember(annotationName)
        log.debug(annotationNode.getClassNode().getName() + ":" + annotationNode.getLineNumber())
        if (memberExpression instanceof PropertyExpression) {
            log.debug("PropertyExpression")
            ConstantExpression constantExpression = memberExpression.getProperty() as ConstantExpression
            result = constantExpression.getValue()
        } else if (memberExpression instanceof ConstantExpression) {
            log.debug("ConstantExpression")
            result = memberExpression.getValue()
        } else if (memberExpression == null) {
            log.debug("defaultValue")
            result = defaultValue
        } else {
            throw new BlackBoxTransformationException(memberExpression, "Unsupported annotation \"$annotationName\" member expression class: " + memberExpression.getClass().getCanonicalName() + " for method " + MDC.get("unitName"))
        }
        log.debug(annotationName + "=" + result)
        return result
    }

    Statement checkSuperConstructorCall(MethodNode iMethodNode) {
        Statement firstStatement = new EmptyStatement()
        if (iMethodNode instanceof ConstructorNode) {
            def initialFirstStatement = ((BlockStatement) iMethodNode.getCode()).getStatements().get(0)
            if (initialFirstStatement instanceof ExpressionStatement) {
                if (initialFirstStatement.getExpression() instanceof ConstructorCallExpression) {
                    if (((ConstructorCallExpression) initialFirstStatement.getExpression()).isSuperCall()) {
                        firstStatement = initialFirstStatement
                        ((BlockStatement) iMethodNode.getCode()).getStatements().remove(initialFirstStatement)
                    }
                }
            }
        }
        return firstStatement
    }

    void transformMethod(MethodNode iMethodNode) {
        if (blackBoxLevel.value() == BlackBoxLevel.NONE.value()) {
            return
        }
        List<MapEntryExpression> argumentMapEntryExpressionList = new ArrayList<>()
        if (astUtils.methodArgumentsPresent(iMethodNode.getParameters())) {
            for (parameter in iMethodNode.getParameters()) {
                argumentMapEntryExpressionList.add(new MapEntryExpression(GeneralUtils.constX(parameter.getName()), GeneralUtils.varX(parameter.getName())))
            }
        }
        Statement firstStatement = checkSuperConstructorCall(iMethodNode)
        Statement methodExecutionOpen = createMethodLogStatement("methodBegin", iMethodNode, argumentMapEntryExpressionList)
        Statement methodException = createMethodLogStatement("methodException", iMethodNode, argumentMapEntryExpressionList, GeneralUtils.varX("automaticException"))
        if (blackBoxLevel.value() == BlackBoxLevel.METHOD.value()) {
            methodStatementLevelTransformation(iMethodNode, firstStatement, methodExecutionOpen, methodException)
        } else if (blackBoxLevel.value() == BlackBoxLevel.ERROR.value()) {
            methodErrorLevelTransformation(iMethodNode, firstStatement, methodException)
        } else {
            throw new BlackBoxTransformationException(iMethodNode, "Unsupported BlackBox Level: " + blackBoxLevel.toString())
        }
    }

    ExpressionStatement createMethodLogStatement(String methodLogName, MethodNode methodNode, ArrayList<MapEntryExpression> argumentMapEntryExpressionList, Expression... additionalArgs) {
        def args = GeneralUtils.args(
                metaDataMethodNode(methodNode),
                new MapExpression(
                        argumentMapEntryExpressionList
                )
        )
        if (astUtils.methodArgumentsPresent(additionalArgs)) {
            additionalArgs.each {
                args.addExpression(it)
            }
        }
        return new ExpressionStatement(
                GeneralUtils.callX(
                        GeneralUtils.varX(runtimeVarName),
                        methodLogName,
                        args
                )
        )
    }

    ConstructorCallExpression metaDataMethodNode(MethodNode methodNode) {
        return GeneralUtils.ctorX(
                ClassHelper.make(MetaDataMethodNode.class),
                GeneralUtils.args(
                        GeneralUtils.constX(methodNode.getLineNumber()),
                        GeneralUtils.constX(methodNode.getLastLineNumber()),
                        GeneralUtils.constX(methodNode.getColumnNumber()),
                        GeneralUtils.constX(methodNode.getLastColumnNumber()),
                        GeneralUtils.constX(methodNode.getName()),
                        GeneralUtils.constX(methodNode.getDeclaringClass().getName())
                )
        )
    }

    void methodStatementLevelTransformation(MethodNode iMethodNode, Statement firstStatement, ExpressionStatement methodExecutionOpen, Statement methodExecutionOpenException) {
        iMethodNode.setCode(
                GeneralUtils.block(
                        firstStatement,
                        methodExecutionOpen,
                        {
                            TryCatchStatement tryCatchStatement = new TryCatchStatement(
                                    iMethodNode.getCode(),
                                    new ExpressionStatement(
                                            GeneralUtils.callX(
                                                    GeneralUtils.varX(runtimeVarName),
                                                    getMethodEndName(),
                                                    GeneralUtils.args(metaDataMethodNode(methodNode))
                                            )
                                    )
                            )
                            tryCatchStatement.addCatch(
                                    GeneralUtils.catchS(
                                            GeneralUtils.param(ClassHelper.make(Exception.class), "automaticException"),
                                            GeneralUtils.block(
                                                    methodExecutionOpenException,
                                                    createThrowStatement()
                                            )
                                    )
                            )
                            return tryCatchStatement
                        }.call() as TryCatchStatement
                )
        )
    }

    void methodErrorLevelTransformation(MethodNode iMethodNode, Statement firstStatement, Statement methodException) {
        iMethodNode.setCode(
                GeneralUtils.block(
                        firstStatement,
                        {
                            TryCatchStatement tryCatchStatement = new TryCatchStatement(iMethodNode.getCode(), EmptyStatement.INSTANCE)
                            tryCatchStatement.addCatch(
                                    GeneralUtils.catchS(
                                            GeneralUtils.param(ClassHelper.make(Exception.class), "automaticException"),
                                            GeneralUtils.block(
                                                    methodException,
                                                    createThrowStatement()
                                            )
                                    )
                            )
                            return tryCatchStatement
                        }.call() as TryCatchStatement
                )
        )
    }

    BlockStatement transformControlStatement(Statement statement, String sourceNodeName) {
        BlockStatement blockStatement = GeneralUtils.block(new VariableScope())
        MethodCallExpression methodCallExpression = GeneralUtils.callX(
                GeneralUtils.varX(runtimeVarName),
                "preprocessControlStatement",
                GeneralUtils.args(
                        GeneralUtils.ctorX(
                                ClassHelper.make(MetaDataStatement.class),
                                GeneralUtils.args(
                                        GeneralUtils.constX(statement.getClass().getSimpleName()),
                                        GeneralUtils.constX(statement.getLineNumber()),
                                        GeneralUtils.constX(statement.getLastLineNumber()),
                                        GeneralUtils.constX(statement.getColumnNumber()),
                                        GeneralUtils.constX(statement.getLastColumnNumber()),
                                        GeneralUtils.constX(methodNode.getName()),
                                        GeneralUtils.constX(methodNode.getDeclaringClass().getName())
                                )
                        )
                )
        )
        blockStatement.addStatement(new ExpressionStatement(methodCallExpression))
        blockStatement.addStatement(statement)
        blockStatement.copyNodeMetaData(statement)
        blockStatement.setSourcePosition(statement)
        return blockStatement
    }

}
