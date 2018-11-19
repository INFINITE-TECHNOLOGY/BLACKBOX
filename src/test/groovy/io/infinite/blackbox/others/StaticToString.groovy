package io.infinite.blackbox.others

class StaticToString {

    static String toString() {
        throw new Exception("should not be called!")
    }

}
