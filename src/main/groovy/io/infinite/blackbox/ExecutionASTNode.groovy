package io.infinite.blackbox

import io.infinite.supplies.ast.metadata.MetaDataASTNode

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
