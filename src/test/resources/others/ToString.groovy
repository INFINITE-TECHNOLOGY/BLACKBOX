package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

class ToString {

    @BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
    @Override
    String toString() {
        return super.toString()
    }

}
