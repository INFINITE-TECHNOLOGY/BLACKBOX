package io.infinite.blackbox

import com.fasterxml.jackson.databind.ObjectMapper
import io.infinite.blackbox.generated.XMLASTNode
import io.infinite.blackbox.generated.XMLMethodNode

class BlackBoxEngineFactory {

    static ThreadLocal engineThreadLocal = new ThreadLocal()

    static {
        XMLASTNode.getMetaClass().parentAstNode = null
        Exception.getMetaClass().isLoggedByBlackBox = null
        Exception.getMetaClass().uuid = null
        XMLMethodNode.getMetaClass().standaloneException = null
    }

    BlackBoxEngine getInstance() {
        BlackBoxEngine blackBoxEngine = engineThreadLocal.get() as BlackBoxEngine
        if (blackBoxEngine == null) {
            BlackBoxConfig blackBoxConfig = initBlackBoxConfig()
            if (blackBoxConfig.runtime.mode == BlackBoxMode.SEQUENTIAL.value()) {
                blackBoxEngine = new BlackBoxEngineSequential()
            } else if (blackBoxConfig.runtime.mode == BlackBoxMode.HIERARCHICAL.value()) {
                blackBoxEngine = new BlackBoxEngineHierarchical()
            } else if (blackBoxConfig.runtime.mode == BlackBoxMode.PLAINTEXT.value()) {
                blackBoxEngine = new BlackBoxEnginePlainText()
            } else {
                blackBoxEngine = new BlackBoxEngineEmergency()
            }
            blackBoxEngine.setBlackBoxConfig(blackBoxConfig)
            engineThreadLocal.set(blackBoxEngine)
        }
        return blackBoxEngine
    }

    BlackBoxConfig initBlackBoxConfig() {
        BlackBoxConfig blackBoxConfig
        if (new File("./BlackBox.json").exists()) {
            blackBoxConfig = new ObjectMapper().readValue(new File("./BlackBox.json").getText(), BlackBoxConfig.class)
        } else {
            blackBoxConfig = new BlackBoxConfig()
        }
        return blackBoxConfig
    }

}
