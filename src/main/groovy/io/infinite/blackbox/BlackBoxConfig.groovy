package io.infinite.blackbox

class BlackBoxConfig {

    class Compile {
        String defaultLevel = "METHOD"
    }

    class Runtime {
        String mode = "EMERGENCY"
    }

    Compile compile = new Compile()

    Runtime runtime = new Runtime()

}
