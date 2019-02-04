package io.infinite.blackbox

class BlackBoxEngineFactory {

    static {
        Exception.getMetaClass().isLoggedByBlackBox = null
        Exception.getMetaClass().uuid = null
        Exception.getMetaClass().runtimeException = null
    }

    BlackBoxEngineSequential getInstance() {
        BlackBoxEngineSequential blackBoxEngine = BlackBoxThreadLocal.get() as BlackBoxEngineSequential
        if (blackBoxEngine == null) {
            blackBoxEngine = new BlackBoxEngineSequential()
            BlackBoxThreadLocal.set(blackBoxEngine)
        }
        return blackBoxEngine
    }

}
