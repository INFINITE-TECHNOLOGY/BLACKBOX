package io.infinite.blackbox

class BlackBoxConfig {

    class BlackBoxConfigCompile {
        String defaultLevel = "METHOD"
    }

    class BlackBoxConfigRuntime {
        String mode = "EMERGENCY"
    }

    BlackBoxConfigCompile compile

    BlackBoxConfigRuntime runtime

}
