package io.infinite.blackbox


import java.util.concurrent.ConcurrentHashMap

/**
 * Application servers interfere with normal Java BlackBoxThreadLocal
 * Thus we have to use custom class.
 *
 * */
class BlackBoxThreadLocal {

    public static ConcurrentHashMap<Thread, BlackBoxEngine> blackBoxEnginesByThread = new ConcurrentHashMap<Thread, BlackBoxEngine>()

    static void set(BlackBoxEngine blackBoxEngine) {
        blackBoxEnginesByThread.put(Thread.currentThread(), blackBoxEngine)
    }

    static Object get() {
        blackBoxEnginesByThread.get(Thread.currentThread())
    }

    static void clear() {
        blackBoxEnginesByThread.clear()
    }

}