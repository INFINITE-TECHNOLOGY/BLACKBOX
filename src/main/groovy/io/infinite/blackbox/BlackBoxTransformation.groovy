package io.infinite.blackbox

import groovy.transform.ToString
import groovy.util.logging.Slf4j
import io.infinite.carburetor.CarburetorTransformation
import jdk.internal.org.objectweb.asm.Opcodes
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ClassExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.stmt.EmptyStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.ThrowStatement
import org.codehaus.groovy.ast.tools.GeneralUtils
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * This class implements BlackBox Transformation Rules for @BlackBox annotation.
 *
 * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki#transformation-rules">BlackBox Blueprint - Transformation Rules</a>
 *
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
@GroovyASTTransformation(
        phase = CompilePhase.SEMANTIC_ANALYSIS
)
@Slf4j
class BlackBoxTransformation extends CarburetorTransformation {

    boolean suppressExceptions

    static {
        ASTNode.getMetaClass().origCodeString = null
        ASTNode.getMetaClass().isTransformed = null
    }

    @Override
    Boolean excludeMethodNode(MethodNode methodNode) {
        return (methodNode.getName() == "toString")
    }

    @Override
    void optionalDeclarations(ClassNode classNode) {
        if (classNode.getDeclaredField("automaticLog") == null) {
            classNode.addFieldFirst("automaticLog",
                    Opcodes.ACC_FINAL | Opcodes.ACC_TRANSIENT | Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                    ClassHelper.make(Logger.class),
                    GeneralUtils.callX(
                            new ClassExpression(ClassHelper.make(LoggerFactory.class)),
                            "getLogger",
                            GeneralUtils.constX(classNode.getName())
                    ))
        }
    }

    @Override
    Class getEngineFactoryClass() {
        return BlackBoxEngine.class
    }

    @Override
    Expression getEngineInitArgs() {
        GeneralUtils.args(GeneralUtils.fieldX(methodNode.declaringClass, "automaticLog"))
    }

    @Override
    void methodDeclarations(MethodNode methodNode) {

    }

    @Override
    void getAnnotationParameters() {
        suppressExceptions = getAnnotationParameter("suppressExceptions", false, annotatationNode)
    }

    @Override
    String getEngineVarName() {
        return "blackBoxEngine"
    }

    Statement createThrowStatement() {
        if (!suppressExceptions) {
            ThrowStatement throwStatement = GeneralUtils.throwS(GeneralUtils.varX("automaticException"))
            throwStatement.setSourcePosition(annotatationNode)
            return throwStatement
        } else {
            return new EmptyStatement()
        }
    }

}
