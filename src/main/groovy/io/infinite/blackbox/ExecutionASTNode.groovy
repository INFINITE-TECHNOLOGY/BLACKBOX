package io.infinite.blackbox

import io.infinite.carburetor.ast.MetaDataASTNode
import io.infinite.carburetor.ast.MetaDataExpression
import io.infinite.carburetor.ast.MetaDataMethodNode
import io.infinite.carburetor.ast.MetaDataStatement

class ExecutionASTNode {

    ExecutionASTNode parentExecutionASTNode

    ExecutionASTNode parentMethodASTNode

    MetaDataASTNode metaDataASTNode

    ExecutionASTNode(ExecutionASTNode parentExecutionASTNode, MetaDataASTNode metaDataASTNode, ExecutionASTNode parentMethodASTNode) {
        this.parentMethodASTNode = parentMethodASTNode
        this.parentExecutionASTNode = parentExecutionASTNode
        this.metaDataASTNode = metaDataASTNode
    }
}
