package io.infinite.blackbox

class BlackBoxConfig {

    class Compile {
        String defaultLevel = BlackBoxLevel.METHOD.value()
        String defaultStrategy = ErrorLoggingStrategy.FULL_THEN_REFERENCE.value()
    }

    class Runtime {
        String mode = BlackBoxMode.EMERGENCY.value()
        String strategy = ErrorLoggingStrategy.FULL_THEN_REFERENCE.value()
    }

    Compile compile = new Compile()

    Runtime runtime = new Runtime()

}