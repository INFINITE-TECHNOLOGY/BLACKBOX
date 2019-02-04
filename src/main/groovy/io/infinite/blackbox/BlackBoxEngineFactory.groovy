package io.infinite.blackbox

import io.infinite.blackbox.generated.XMLASTNode

class BlackBoxEngineFactory {

    static {
        XMLASTNode.getMetaClass().parentAstNode = null
        Exception.getMetaClass().isLoggedByBlackBox = null
        Exception.getMetaClass().uuid = null
        Exception.getMetaClass().runtimeException = null
    }

    BlackBoxEngine getInstance() {
        BlackBoxEngine blackBoxEngine = BlackBoxThreadLocal.get() as BlackBoxEngine
        if (blackBoxEngine == null) {
            blackBoxEngine = new BlackBoxEngineSequential()
            BlackBoxThreadLocal.set(blackBoxEngine)
        }
        return blackBoxEngine
    }

}
