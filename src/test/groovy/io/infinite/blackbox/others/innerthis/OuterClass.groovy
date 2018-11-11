package io.infinite.blackbox.others.innerthis

class OuterClass implements Runnable{

    class InnerClass {

        String bar() {
            Closure closureOne = {
                Closure closureTwo = {
                    OuterClass outerClassViaQualifiedThisReference = OuterClass.this
                    return outerClassViaQualifiedThisReference
                }
                return closureTwo.call()
            }
            closureOne.call()
        }

    }

    @Override
    String toString() {
        return "OuterClass"
    }

    @Override
    void run() {
        System.out.println(new InnerClass().bar())
    }
}
