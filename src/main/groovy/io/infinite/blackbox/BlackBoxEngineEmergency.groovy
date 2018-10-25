package io.infinite.blackbox

import groovy.util.logging.Slf4j
import io.infinite.blackbox.generated.ObjectFactory

import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

/**
 * This class implements BlackBox Runtime API - Emergency Mode functionality
 * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#runtime-engine-api">BlackBox Blueprint - Runtime Engine API</a>
 * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#emergency">BlackBox Blueprint - Emergency Operating Mode</a>
 * <br>
 * This class removes successful AST Nodes from BlackBox runtime stack.
 * <br>
 * When exception is occured - this class prints whole runtime stack.
 *
 */
@Slf4j
class BlackBoxEngineEmergency extends BlackBoxEngine {

    /**
     * In addition to base behavior of BlackBoxEngine, removes the successful AST Node from log to save memory.
     * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#memory-leakage-prevention">BlackBox Blueprint - Memory Leakage Prevention</a>
     */
    @Override
    void executionClose() {
        if (astNode.parentAstNode != null) {
            astNode.parentAstNode.getAstNodeList().getAstNode().remove(astNode)
        }
        super.executionClose()
    }

    /**
     * In addition to base behavior of BlackBoxEngine, Exception causes actual printing of the Log (whole failed branch of AST Log is printed).<br/>
     * Since the AST tree is finite at this point of time - hierarchical XML marshaller is used.
     * @param iThrowable
     */
    @Override
    void exception(Throwable iThrowable) {
        super.exception(iThrowable)
        if (iThrowable.isLoggedByBlackBox != true) {
            JAXBContext lJAXBContext = JAXBContext.newInstance(astNode.getClass())
            Marshaller marshaller = lJAXBContext.createMarshaller()
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE)
            StringWriter stringWriter = new StringWriter()
            marshaller.marshal(new ObjectFactory().createRootAstNode(astNode), stringWriter)
            String xmlString = stringWriter.toString()
            log.error(xmlString)
            iThrowable.isLoggedByBlackBox = true
        }
    }
}
