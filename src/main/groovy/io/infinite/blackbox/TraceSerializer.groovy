package io.infinite.blackbox

import groovy.transform.CompileStatic

/**
 * See also: https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/issues/17
 */
@CompileStatic
class TraceSerializer {

    static String toString(Object object) {
        return object.toString()
    }

}
