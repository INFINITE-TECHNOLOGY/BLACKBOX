package io.infinite.blackbox

import groovy.util.logging.Slf4j
import io.infinite.blackbox.generated.ObjectFactory

import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

/**
 * This class implements BlackBox Runtime API - Hierarchical Mode functionality.
 * This class/mode should be used only for BlackBox testing purposes as it consumes RAM indefinitely.
 * @see <a href="https://github.com/INFINITE-TECHNOLOGY/BLACKBOX/wiki/Blueprint#runtime-engine-api">BlackBox Blueprint - Runtime Engine API</a>
 */
@Slf4j
class BlackBoxEngineHierarchical extends BlackBoxEngine{

    @Override
    void executionClose() {
        if (astNode.parentAstNode == null) {
            JAXBContext lJAXBContext = JAXBContext.newInstance(astNode.getClass())
            Marshaller marshaller = lJAXBContext.createMarshaller()
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE)
            StringWriter stringWriter = new StringWriter()
            marshaller.marshal(new ObjectFactory().createRootAstNode(astNode), stringWriter)
            String xmlString = stringWriter.toString()
            log.debug(xmlString)
        }
        super.executionClose()
    }
}
