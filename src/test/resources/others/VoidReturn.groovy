package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

class VoidReturn implements Runnable {

    @Override
    //@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
    void run() {
        return
    }

}