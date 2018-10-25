package io.infinite.blackbox

/**
 * This enumeration contains BlackBox Operating Modes.
 *
 * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#operating-modes">BlackBox Blueprint - Operating Modes</a>
 */
enum BlackBoxMode {

    /**
     * Sequential real-time - an alternative to using Debugger. There are many situations when it is easier to run a program - and then to read the hierarchical execution output like in above example to identify bugs or understand why specific branch of AST has been run. With the Debugger it will take a lot of time by manually putting Breakpoints and Watches.
     */
    SEQUENTIAL("SEQUENTIAL"),
    /**
     * Emergency - suitable for using on Production - when Unhandled exception happens - the whole AST route with all runtime data (method Arguments, method results, expression values and associated meta data (line numbers, etc)) - is printed up to the place where exception has been encountered. This acts an alternative to Memory dump, with a difference that such trace has retrospective data (not only that last memory snapshot).
     */
    EMERGENCY("EMERGENCY"),
    /**
     * Use only for BlackBox solution testing itself.
     */
    HIERARCHICAL("HIERARCHICAL");

    private final String blackBoxMode

    BlackBoxMode(String iBlackBoxLevel) {
        blackBoxMode = iBlackBoxLevel
    }

    String value() {
        return blackBoxMode
    }

}