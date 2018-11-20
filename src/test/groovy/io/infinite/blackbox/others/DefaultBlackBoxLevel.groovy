package io.infinite.blackbox.others

import io.infinite.blackbox.BlackBox

class DefaultBlackBoxLevel {

    @BlackBox
    void foo() {
        System.out.println("Expression")
    }

}
