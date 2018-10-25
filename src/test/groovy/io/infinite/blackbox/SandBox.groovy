package io.infinite.blackbox

class SandBox implements Runnable {

    static void main2(String[] args) {
        //System.setProperty("blackbox.mode", BlackBoxMode.EMERGENCY.value())
        //System.setProperty("blackbox.mode", BlackBoxMode.SEQUENTIAL.value())
        System.setProperty("blackbox.mode", BlackBoxMode.HIERARCHICAL.value())
        new SandBox().visitArgumentlistExpressionExpressionLevel(1,2,3)
        //new SandBox().visitThrowStatementExpressionLevel()
    }


    //@BlackBox(blackBoxLevel = BlackBoxLevel.EXPRESSION)
    String visitArgumentlistExpressionExpressionLevel(def a, def b, def c) {
        tst(a,b,c)
        //1/0
        return "zzzz"
    }

    void tst(def a, def b, def c) {
        System.out.println(a+b+c)
    }

    void z() {

    }

    @BlackBox(blackBoxLevel = BlackBoxLevel.STATEMENT)
    String foo(String bar) {
        if (bar == "foobar") {
            throw new Exception("Bar can not be foobar")
        }
        return bar
    }

    @Override
    void run() {
        System.setProperty("blackbox.mode", BlackBoxMode.EMERGENCY.value())
        System.out.println(foo("foobar"))
    }
}
