package io.infinite.blackbox

/**
 * This enumeration contains BlackBox levels.
 *
 * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#4-blackbox-levels">BlackBox Blueprint - BlackBox Levels</a>
 */
enum BlackBoxLevel {

    /**
     * Enables Expression transformation in addition to BlackBoxLevel.METHOD and BlackBoxLevel.STATEMENT
     */
    EXPRESSION(5),
    /**
     * Enables Statement transformation in addition to BlackBoxLevel.METHOD
     */
    STATEMENT(4),
    /**
     * Corresponds to Method transformation
     */
    METHOD(3),
    /**
     * Corresponds to Method Exception handling transformation
     */
    METHOD_ERROR(2),
    /**
     * Method is unmodified
     */
    NONE(1);

    private final int blackBoxLevel

    BlackBoxLevel(int iBlackBoxLevel) {
        blackBoxLevel = iBlackBoxLevel
    }

    int value() {
        return blackBoxLevel
    }

}