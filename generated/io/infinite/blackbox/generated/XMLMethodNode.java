//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.10.24 at 05:38:02 PM GST 
//


package io.infinite.blackbox.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MethodNode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MethodNode">
 *   &lt;complexContent>
 *     &lt;extension base="{https://i-t.io/blackbox/groovy/2_x_x/BlackBox.xsd}ASTNode">
 *       &lt;sequence>
 *         &lt;element name="argumentList" type="{https://i-t.io/blackbox/groovy/2_x_x/BlackBox.xsd}ArgumentList" minOccurs="0"/>
 *         &lt;element name="exception" type="{https://i-t.io/blackbox/groovy/2_x_x/BlackBox.xsd}Exception" minOccurs="0"/>
 *         &lt;element name="methodResult" type="{https://i-t.io/blackbox/groovy/2_x_x/BlackBox.xsd}Object" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="methodName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="className" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MethodNode", propOrder = {
    "argumentList",
    "exception",
    "methodResult"
})
public class XMLMethodNode
    extends XMLASTNode
{

    protected XMLArgumentList argumentList;
    protected XMLException exception;
    protected XMLObject methodResult;
    @XmlAttribute(name = "methodName", required = true)
    protected String methodName;
    @XmlAttribute(name = "className", required = true)
    protected String className;

    /**
     * Gets the value of the argumentList property.
     * 
     * @return
     *     possible object is
     *     {@link XMLArgumentList }
     *     
     */
    public XMLArgumentList getArgumentList() {
        return argumentList;
    }

    /**
     * Sets the value of the argumentList property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLArgumentList }
     *     
     */
    public void setArgumentList(XMLArgumentList value) {
        this.argumentList = value;
    }

    /**
     * Gets the value of the exception property.
     * 
     * @return
     *     possible object is
     *     {@link XMLException }
     *     
     */
    public XMLException getException() {
        return exception;
    }

    /**
     * Sets the value of the exception property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLException }
     *     
     */
    public void setException(XMLException value) {
        this.exception = value;
    }

    /**
     * Gets the value of the methodResult property.
     * 
     * @return
     *     possible object is
     *     {@link XMLObject }
     *     
     */
    public XMLObject getMethodResult() {
        return methodResult;
    }

    /**
     * Sets the value of the methodResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLObject }
     *     
     */
    public void setMethodResult(XMLObject value) {
        this.methodResult = value;
    }

    /**
     * Gets the value of the methodName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Sets the value of the methodName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethodName(String value) {
        this.methodName = value;
    }

    /**
     * Gets the value of the className property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the value of the className property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassName(String value) {
        this.className = value;
    }

}
