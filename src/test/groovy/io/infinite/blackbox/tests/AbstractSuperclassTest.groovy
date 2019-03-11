package io.infinite.blackbox.tests

import io.infinite.blackbox.AbstractTest

class AbstractSuperclassTest extends AbstractTest {

    @Override
    void executeTests() {
        def testInstance = getTestInstance("tests", "AbstractSuperclassTest.groovy")
        testInstance.run()
    }

}