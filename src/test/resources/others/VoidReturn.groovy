package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

class VoidReturn implements Runnable {

    @Override
    //@BlackBox(level = CarburetorLevel.EXPRESSION)
    void run() {
        return
    }

}