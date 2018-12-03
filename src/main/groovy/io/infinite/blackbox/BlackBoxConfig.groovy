package io.infinite.blackbox

class BlackBoxConfig {

    class Compile {
        String defaultLevel = BlackBoxLevel.METHOD.name()
        String defaultStrategy = ErrorLoggingStrategy.FULL_THEN_REFERENCE.name()
    }

    class Runtime {
        String mode = BlackBoxMode.EMERGENCY.name()
        String strategy = ErrorLoggingStrategy.FULL_THEN_REFERENCE.name()
    }

    Compile compile = new Compile()

    Runtime runtime = new Runtime()

}