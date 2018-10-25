package io.infinite.blackbox

import groovy.transform.ToString
import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.VariableScope
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.tools.GeneralUtils


/**
 * This class traverses AST and ensures BlackBox Transformation Rules are applied for inner transformation of AST Nodes
 *
 * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#transformation-rules">BlackBox Blueprint - Transformation Rules</a>
 *
 */
@ToString(includeNames = true, includeFields = true)
class BlackBoxVisitor extends CodeVisitorSupport {

    BlackBoxTransformation blackBoxTransformation
    BlackBoxLevel blackBoxLevel

    BlackBoxVisitor(BlackBoxTransformation iBlackBoxTransformation, BlackBoxLevel iBlackBoxLevel) {
        blackBoxTransformation = iBlackBoxTransformation
        blackBoxLevel = iBlackBoxLevel
    }

    void transformStatementList(List<Statement> iStatementListToTransform, String iSourceNodeName) {
        List<Statement> transformedStatementList = iStatementListToTransform.getClass().newInstance() as List<Statement>
        for (Statement statementToTransform : iStatementListToTransform) {
            transformedStatementList.add(blackBoxTransformation.transformStatement(statementToTransform, iSourceNodeName))
        }
        iStatementListToTransform.clear()
        iStatementListToTransform.addAll(transformedStatementList)
    }

    //todo: log return for all blackbox levels
    //todo: copy metadata for all nodes
    void transformExpressionList(List<Expression> iExpressionListToTransform, String iSourceNodeName) {
        List<Expression> transformedExpressionList = iExpressionListToTransform.getClass().newInstance() as List<Expression>
        for (Expression expressionToTransform : iExpressionListToTransform) {
            transformedExpressionList.addAll(blackBoxTransformation.transformExpression(expressionToTransform, iSourceNodeName))
        }
        iExpressionListToTransform.clear()
        iExpressionListToTransform.addAll(transformedExpressionList)
    }

    @Override
    void visitBlockStatement(BlockStatement iBlockStatement) {
        iBlockStatement.origCodeString = blackBoxTransformation.codeString(iBlockStatement)
        super.visitBlockStatement(iBlockStatement)
        transformStatementList(iBlockStatement.getStatements(), iBlockStatement.getClass().getSimpleName() + ":statements")
    }

    @Override
    void visitForLoop(ForStatement iForStatement) {
        iForStatement.origCodeString = blackBoxTransformation.codeString(iForStatement)
        super.visitForLoop(iForStatement)
        iForStatement.setCollectionExpression(blackBoxTransformation.transformExpression(iForStatement.getCollectionExpression(), iForStatement.getClass().getSimpleName() + ":collectionExpression"))
        iForStatement.setLoopBlock(GeneralUtils.block(new VariableScope(), blackBoxTransformation.transformStatement(iForStatement.getLoopBlock(), iForStatement.getClass().getSimpleName() + ":loopBlock")))
    }

    @Override
    void visitWhileLoop(WhileStatement iWhileStatement) {
        iWhileStatement.origCodeString = blackBoxTransformation.codeString(iWhileStatement)
        super.visitWhileLoop(iWhileStatement)
        iWhileStatement.setBooleanExpression(new BooleanExpression(blackBoxTransformation.transformExpression(iWhileStatement.getBooleanExpression(), iWhileStatement.getClass().getSimpleName() + ":booleanExpression")))
        iWhileStatement.setLoopBlock(GeneralUtils.block(new VariableScope(), blackBoxTransformation.transformStatement(iWhileStatement.getLoopBlock(), iWhileStatement.getClass().getSimpleName() + ":loopBlock")))
    }

    @Override
    void visitDoWhileLoop(DoWhileStatement iDoWhileStatement) {
        iDoWhileStatement.origCodeString = blackBoxTransformation.codeString(iDoWhileStatement)
        super.visitDoWhileLoop(iDoWhileStatement)
        iDoWhileStatement.setBooleanExpression(new BooleanExpression(blackBoxTransformation.transformExpression(iDoWhileStatement.getBooleanExpression(), iDoWhileStatement.getClass().getSimpleName() + ":booleanExpression")))
        iDoWhileStatement.setLoopBlock(GeneralUtils.block(new VariableScope(), blackBoxTransformation.transformStatement(iDoWhileStatement.getLoopBlock(), iDoWhileStatement.getClass().getSimpleName() + ":loopBlock")))
    }

    @Override
    void visitIfElse(IfStatement iIfStatement) {
        iIfStatement.origCodeString = blackBoxTransformation.codeString(iIfStatement)
        super.visitIfElse(iIfStatement)
        iIfStatement.setBooleanExpression(new BooleanExpression(blackBoxTransformation.transformExpression(iIfStatement.getBooleanExpression(), iIfStatement.getClass().getSimpleName() + ":booleanExpression")))
        iIfStatement.setIfBlock(GeneralUtils.block(new VariableScope(), blackBoxTransformation.transformStatement(iIfStatement.getIfBlock(), iIfStatement.getClass().getSimpleName() + ":ifBlock")))
        iIfStatement.setElseBlock(GeneralUtils.block(new VariableScope(), blackBoxTransformation.transformStatement(iIfStatement.getElseBlock(), iIfStatement.getClass().getSimpleName() + ":elseBlock")))
    }

    @Override
    void visitExpressionStatement(ExpressionStatement iExpressionStatement) {
        iExpressionStatement.origCodeString = blackBoxTransformation.codeString(iExpressionStatement)
        super.visitExpressionStatement(iExpressionStatement)
        iExpressionStatement.setExpression(blackBoxTransformation.transformExpression(iExpressionStatement.getExpression(), iExpressionStatement.getClass().getSimpleName() + ":expression"))
    }

    @Override
    void visitReturnStatement(ReturnStatement iReturnStatement) {
        iReturnStatement.origCodeString = blackBoxTransformation.codeString(iReturnStatement)
        super.visitReturnStatement(iReturnStatement)
        if (!iReturnStatement.isReturningNullOrVoid()) {
            iReturnStatement.setExpression(blackBoxTransformation.transformExpression(iReturnStatement.getExpression(), iReturnStatement.getClass().getSimpleName() + ":expression"))
        }
    }

    @Override
    void visitAssertStatement(AssertStatement iAssertStatement) {
        iAssertStatement.origCodeString = blackBoxTransformation.codeString(iAssertStatement)
        super.visitAssertStatement(iAssertStatement)
        iAssertStatement.setBooleanExpression(new BooleanExpression(blackBoxTransformation.transformExpression(iAssertStatement.getBooleanExpression(), iAssertStatement.getClass().getSimpleName() + ":booleanExpression")))
        iAssertStatement.setMessageExpression(blackBoxTransformation.transformExpression(iAssertStatement.getMessageExpression(), iAssertStatement.getClass().getSimpleName() + ":messageExpression"))
    }

    @Override
    void visitTryCatchFinally(TryCatchStatement iTryCatchStatement) {
        iTryCatchStatement.origCodeString = blackBoxTransformation.codeString(iTryCatchStatement)
        super.visitTryCatchFinally(iTryCatchStatement)
        iTryCatchStatement.setTryStatement(GeneralUtils.block(new VariableScope(), blackBoxTransformation.transformStatement(iTryCatchStatement.getTryStatement(), iTryCatchStatement.getClass().getSimpleName() + ":tryStatement")))
        for (CatchStatement catchStatement : iTryCatchStatement.getCatchStatements()) {
            catchStatement.setCode(GeneralUtils.block(new VariableScope(), blackBoxTransformation.transformStatement(catchStatement.getCode(), catchStatement.getClass().getSimpleName() + ":code")))
        }
        iTryCatchStatement.setFinallyStatement(GeneralUtils.block(new VariableScope(), blackBoxTransformation.transformStatement(iTryCatchStatement.getFinallyStatement(), iTryCatchStatement.getClass().getSimpleName() + ":finallyStatement")))
    }

    @Override
    protected void visitEmptyStatement(EmptyStatement iEmptyStatement) {
        iEmptyStatement.origCodeString = blackBoxTransformation.codeString(iEmptyStatement)
        super.visitEmptyStatement(iEmptyStatement)

    }

    @Override
    void visitSwitch(SwitchStatement iSwitchStatement) {
        iSwitchStatement.origCodeString = blackBoxTransformation.codeString(iSwitchStatement)
        super.visitSwitch(iSwitchStatement)
        iSwitchStatement.setExpression(blackBoxTransformation.transformExpression(iSwitchStatement.getExpression(), iSwitchStatement.getClass().getSimpleName() + ":expression"))
        for (CaseStatement caseStatement : iSwitchStatement.getCaseStatements()) {
            caseStatement.setCode(GeneralUtils.block(new VariableScope(), blackBoxTransformation.transformStatement(caseStatement.getCode(), caseStatement.getClass().getSimpleName() + ":code")))
        }
        iSwitchStatement.setDefaultStatement(GeneralUtils.block(new VariableScope(), blackBoxTransformation.transformStatement(iSwitchStatement.getDefaultStatement(), iSwitchStatement.getClass().getSimpleName() + ":defaultStatement")))
    }

    @Override
    void visitCaseStatement(CaseStatement iCaseStatement) {
        iCaseStatement.origCodeString = blackBoxTransformation.codeString(iCaseStatement)
        super.visitCaseStatement(iCaseStatement)
        iCaseStatement.setExpression(blackBoxTransformation.transformExpression(iCaseStatement.getExpression(), iCaseStatement.getClass().getSimpleName() + ":expression"))
        iCaseStatement.setCode(GeneralUtils.block(new VariableScope(), blackBoxTransformation.transformStatement(iCaseStatement.getCode(), iCaseStatement.getClass().getSimpleName() + ":code")))
    }

    @Override
    void visitBreakStatement(BreakStatement iBreakStatement) {
        iBreakStatement.origCodeString = blackBoxTransformation.codeString(iBreakStatement)
        super.visitBreakStatement(iBreakStatement)
    }

    @Override
    void visitContinueStatement(ContinueStatement iContinueStatement) {
        iContinueStatement.origCodeString = blackBoxTransformation.codeString(iContinueStatement)
        super.visitContinueStatement(iContinueStatement)

    }

    @Override
    void visitSynchronizedStatement(SynchronizedStatement iSynchronizedStatement) {
        iSynchronizedStatement.origCodeString = blackBoxTransformation.codeString(iSynchronizedStatement)
        super.visitSynchronizedStatement(iSynchronizedStatement)
        iSynchronizedStatement.setExpression(blackBoxTransformation.transformExpression(iSynchronizedStatement.getExpression(), iSynchronizedStatement.getClass().getSimpleName() + ":expression"))
        iSynchronizedStatement.setCode(GeneralUtils.block(new VariableScope(), blackBoxTransformation.transformStatement(iSynchronizedStatement.getCode(), iSynchronizedStatement.getClass().getSimpleName() + ":code")))
    }

    @Override
    void visitThrowStatement(ThrowStatement iThrowStatement) {
        iThrowStatement.origCodeString = blackBoxTransformation.codeString(iThrowStatement)
        super.visitThrowStatement(iThrowStatement)
        iThrowStatement.setExpression(blackBoxTransformation.transformExpression(iThrowStatement.getExpression(), iThrowStatement.getClass().getSimpleName() + ":expression"))
    }

    @Override
    void visitMethodCallExpression(MethodCallExpression iMethodCallExpression) {
        iMethodCallExpression.origCodeString = blackBoxTransformation.codeString(iMethodCallExpression)
        super.visitMethodCallExpression(iMethodCallExpression)
        iMethodCallExpression.setObjectExpression(blackBoxTransformation.transformExpression(iMethodCallExpression.getObjectExpression(), iMethodCallExpression.getClass().getSimpleName() + ":objectExpression"))
        iMethodCallExpression.setArguments(blackBoxTransformation.transformExpression(iMethodCallExpression.getArguments(), iMethodCallExpression.getClass().getSimpleName() + ":arguments"))
    }

    @Override
    void visitStaticMethodCallExpression(StaticMethodCallExpression iStaticMethodCallExpression) {
        iStaticMethodCallExpression.origCodeString = blackBoxTransformation.codeString(iStaticMethodCallExpression)
        super.visitStaticMethodCallExpression(iStaticMethodCallExpression)
    }

    @Override
    void visitConstructorCallExpression(ConstructorCallExpression iConstructorCallExpression) {
        iConstructorCallExpression.origCodeString = blackBoxTransformation.codeString(iConstructorCallExpression)
        super.visitConstructorCallExpression(iConstructorCallExpression)
    }

    @Override
    void visitBinaryExpression(BinaryExpression iBinaryExpression) {
        iBinaryExpression.origCodeString = blackBoxTransformation.codeString(iBinaryExpression)
        super.visitBinaryExpression(iBinaryExpression)
    }

    @Override
    void visitTernaryExpression(TernaryExpression iTernaryExpression) {
        iTernaryExpression.origCodeString = blackBoxTransformation.codeString(iTernaryExpression)
        super.visitTernaryExpression(iTernaryExpression)
    }

    @Override
    void visitShortTernaryExpression(ElvisOperatorExpression iElvisOperatorExpression) {
        iElvisOperatorExpression.origCodeString = blackBoxTransformation.codeString(iElvisOperatorExpression)
        super.visitShortTernaryExpression(iElvisOperatorExpression)
    }

    @Override
    void visitPostfixExpression(PostfixExpression iPostfixExpression) {
        iPostfixExpression.origCodeString = blackBoxTransformation.codeString(iPostfixExpression)
        super.visitPostfixExpression(iPostfixExpression)
    }

    @Override
    void visitPrefixExpression(PrefixExpression iPrefixExpression) {
        iPrefixExpression.origCodeString = blackBoxTransformation.codeString(iPrefixExpression)
        super.visitPrefixExpression(iPrefixExpression)

    }

    @Override
    void visitBooleanExpression(BooleanExpression iBooleanExpression) {
        iBooleanExpression.origCodeString = blackBoxTransformation.codeString(iBooleanExpression)
        super.visitBooleanExpression(iBooleanExpression)
    }

    @Override
    void visitNotExpression(NotExpression iNotExpression) {
        iNotExpression.origCodeString = blackBoxTransformation.codeString(iNotExpression)
        super.visitNotExpression(iNotExpression)
    }

    @Override
    void visitClosureExpression(ClosureExpression iClosureExpression) {
        iClosureExpression.origCodeString = blackBoxTransformation.codeString(iClosureExpression)
        super.visitClosureExpression(iClosureExpression)
        iClosureExpression.setCode(GeneralUtils.block(new VariableScope(), blackBoxTransformation.transformStatement(iClosureExpression.getCode(), iClosureExpression.getClass().getSimpleName() + ":code")))
    }

    @Override
    void visitTupleExpression(TupleExpression iTupleExpression) {
        iTupleExpression.origCodeString = blackBoxTransformation.codeString(iTupleExpression)
        super.visitTupleExpression(iTupleExpression)
        transformExpressionList(iTupleExpression.getExpressions(), iTupleExpression.getClass().getSimpleName() + ":expressions")
    }

    @Override
    void visitListExpression(ListExpression iListExpression) {
        iListExpression.origCodeString = blackBoxTransformation.codeString(iListExpression)
        super.visitListExpression(iListExpression)
        transformExpressionList(iListExpression.getExpressions(), iListExpression.getClass().getSimpleName() + ":expressions")
    }

    @Override
    void visitArrayExpression(ArrayExpression iArrayExpression) {
        iArrayExpression.origCodeString = blackBoxTransformation.codeString(iArrayExpression)
        super.visitArrayExpression(iArrayExpression)
        transformExpressionList(iArrayExpression.getExpressions(), iArrayExpression.getClass().getSimpleName() + ":expressions")
        transformExpressionList(iArrayExpression.getSizeExpression(), iArrayExpression.getClass().getSimpleName() + ":sizeExpression")
    }

    @Override
    void visitMapExpression(MapExpression iMapExpression) {
        iMapExpression.origCodeString = blackBoxTransformation.codeString(iMapExpression)
        super.visitMapExpression(iMapExpression)
    }

    @Override
    void visitMapEntryExpression(MapEntryExpression iMapEntryExpression) {
        iMapEntryExpression.origCodeString = blackBoxTransformation.codeString(iMapEntryExpression)
        super.visitMapEntryExpression(iMapEntryExpression)
        iMapEntryExpression.setKeyExpression(blackBoxTransformation.transformExpression(iMapEntryExpression.getKeyExpression(), iMapEntryExpression.getClass().getSimpleName() + ":keyExpression"))
        iMapEntryExpression.setValueExpression(blackBoxTransformation.transformExpression(iMapEntryExpression.getValueExpression(), iMapEntryExpression.getClass().getSimpleName() + ":valueExpression"))
    }

    @Override
    void visitRangeExpression(RangeExpression iRangeExpression) {
        iRangeExpression.origCodeString = blackBoxTransformation.codeString(iRangeExpression)
        super.visitRangeExpression(iRangeExpression)
    }

    @Override
    void visitSpreadExpression(SpreadExpression iSpreadExpression) {
        iSpreadExpression.origCodeString = blackBoxTransformation.codeString(iSpreadExpression)
        super.visitSpreadExpression(iSpreadExpression)
    }


    @Override
    void visitSpreadMapExpression(SpreadMapExpression iSpreadMapExpression) {
        iSpreadMapExpression.origCodeString = blackBoxTransformation.codeString(iSpreadMapExpression)
        super.visitSpreadMapExpression(iSpreadMapExpression)
    }


    @Override
    void visitMethodPointerExpression(MethodPointerExpression iMethodPointerExpression) {
        iMethodPointerExpression.origCodeString = blackBoxTransformation.codeString(iMethodPointerExpression)
        super.visitMethodPointerExpression(iMethodPointerExpression)
    }

    @Override
    void visitUnaryMinusExpression(UnaryMinusExpression iUnaryMinusExpression) {
        iUnaryMinusExpression.origCodeString = blackBoxTransformation.codeString(iUnaryMinusExpression)
        super.visitUnaryMinusExpression(iUnaryMinusExpression)
    }


    @Override
    void visitUnaryPlusExpression(UnaryPlusExpression iUnaryPlusExpression) {
        iUnaryPlusExpression.origCodeString = blackBoxTransformation.codeString(iUnaryPlusExpression)
        super.visitUnaryPlusExpression(iUnaryPlusExpression)
    }

    @Override
    void visitBitwiseNegationExpression(BitwiseNegationExpression iBitwiseNegationExpression) {
        iBitwiseNegationExpression.origCodeString = blackBoxTransformation.codeString(iBitwiseNegationExpression)
        super.visitBitwiseNegationExpression(iBitwiseNegationExpression)
    }

    @Override
    void visitCastExpression(CastExpression iCastExpression) {
        iCastExpression.origCodeString = blackBoxTransformation.codeString(iCastExpression)
        super.visitCastExpression(iCastExpression)
    }

    @Override
    void visitConstantExpression(ConstantExpression iConstantExpression) {
        iConstantExpression.origCodeString = blackBoxTransformation.codeString(iConstantExpression)
        super.visitConstantExpression(iConstantExpression)
    }

    @Override
    void visitClassExpression(ClassExpression iClassExpression) {
        iClassExpression.origCodeString = blackBoxTransformation.codeString(iClassExpression)
        super.visitClassExpression(iClassExpression)
    }

    @Override
    void visitVariableExpression(VariableExpression iVariableExpression) {
        iVariableExpression.origCodeString = blackBoxTransformation.codeString(iVariableExpression)
        super.visitVariableExpression(iVariableExpression)
    }

    @Override
    void visitDeclarationExpression(DeclarationExpression iDeclarationExpression) {
        iDeclarationExpression.origCodeString = blackBoxTransformation.codeString(iDeclarationExpression)
        iDeclarationExpression.getRightExpression().visit(this)
    }

    @Override
    void visitPropertyExpression(PropertyExpression iPropertyExpression) {
        iPropertyExpression.origCodeString = blackBoxTransformation.codeString(iPropertyExpression)
        super.visitPropertyExpression(iPropertyExpression)
    }

    @Override
    void visitAttributeExpression(AttributeExpression iAttributeExpression) {
        iAttributeExpression.origCodeString = blackBoxTransformation.codeString(iAttributeExpression)
        super.visitAttributeExpression(iAttributeExpression)
    }

    @Override
    void visitFieldExpression(FieldExpression iFieldExpression) {
        iFieldExpression.origCodeString = blackBoxTransformation.codeString(iFieldExpression)
        super.visitFieldExpression(iFieldExpression)
    }

    @Override
    void visitGStringExpression(GStringExpression iGStringExpression) {
        iGStringExpression.origCodeString = blackBoxTransformation.codeString(iGStringExpression)
        super.visitGStringExpression(iGStringExpression)
        transformExpressionList(iGStringExpression.getValues(), iGStringExpression.getClass().getSimpleName() + ":values")
    }

    @Override
    void visitCatchStatement(CatchStatement iCatchStatement) {
        iCatchStatement.origCodeString = blackBoxTransformation.codeString(iCatchStatement)
        super.visitCatchStatement(iCatchStatement)
        iCatchStatement.setCode(GeneralUtils.block(new VariableScope(), blackBoxTransformation.transformStatement(iCatchStatement.getCode(), iCatchStatement.getClass().getSimpleName() + ":code")))
    }

    @Override
    void visitArgumentlistExpression(ArgumentListExpression iArgumentListExpression) {
        iArgumentListExpression.origCodeString = blackBoxTransformation.codeString(iArgumentListExpression)
        super.visitArgumentlistExpression(iArgumentListExpression)
        transformExpressionList(iArgumentListExpression.getExpressions(), iArgumentListExpression.getClass().getSimpleName() + ":expressions")
    }

    @Override
    void visitClosureListExpression(ClosureListExpression iClosureListExpression) {
        iClosureListExpression.origCodeString = blackBoxTransformation.codeString(iClosureListExpression)
        super.visitClosureListExpression(iClosureListExpression)
        transformExpressionList(iClosureListExpression.getExpressions(), iClosureListExpression.getClass().getSimpleName() + ":expressions")
    }

}
