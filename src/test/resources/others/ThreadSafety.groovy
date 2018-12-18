package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox
import io.infinite.carburetor.CarburetorLevel

class ThreadSafety extends Thread{

    @Override
    void run() {
        runWithLogging()
    }

    @BlackBox(level = CarburetorLevel.EXPRESSION)
    void runWithLogging() {
        [1..5].each {
            sleep(10)
        }
    }

}
