package io.infinite.blackbox

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

class VoidReturn implements Runnable {

    @Override
    //@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
    void run() {
        return
    }

}