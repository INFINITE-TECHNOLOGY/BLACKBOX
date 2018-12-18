package io.infinite.blackbox

class BlackBoxConfig {

    class Runtime {
        String mode = BlackBoxMode.EMERGENCY.name()
        String strategy = ErrorLoggingStrategy.FULL_THEN_REFERENCE.name()
    }

    Runtime runtime = new Runtime()

}