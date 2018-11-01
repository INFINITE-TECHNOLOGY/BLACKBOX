package io.infinite.blackbox

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
