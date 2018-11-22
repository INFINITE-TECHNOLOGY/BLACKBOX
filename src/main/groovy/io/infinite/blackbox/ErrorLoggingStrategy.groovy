package io.infinite.blackbox

/**
 * This enumeration contains BlackBox Logging Strategies.
 *
 */
enum ErrorLoggingStrategy {

    ALWAYS_REFERENCE("ALWAYS_REFERENCE"),
    ALWAYS_FULL("AWLAYS_FULL"),
    FULL_THEN_REFERENCE("FULL_THEN_REFERENCE"),
    FULL_THEN_NOTHING("FULL_THEN_NOTHING"),
    REFERENCE_THEN_NOTHING("REFERENCE_THEN_NOTHING"),
    NOTHING("REFERENCE_THEN_NOTHING"),
    UNDEFINED("UNDEFINED")

    private final String blackBoxStrategy

    ErrorLoggingStrategy(String iBlackBoxStrategy) {
        blackBoxStrategy = iBlackBoxStrategy
    }

    String value() {
        return blackBoxStrategy
    }

}