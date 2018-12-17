package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.blackbox.BlackBoxLevel

class ThreadSafety extends Thread{

    @Override
    void run() {
        runWithLogging()
    }

    @BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
    void runWithLogging() {
        [1..5].each {
            sleep(10)
        }
    }

}
