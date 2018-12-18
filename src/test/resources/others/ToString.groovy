package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

class ToString {

    @BlackBox(level = CarburetorLevel.EXPRESSION)
    @Override
    String toString() {
        return super.toString()
    }

}
