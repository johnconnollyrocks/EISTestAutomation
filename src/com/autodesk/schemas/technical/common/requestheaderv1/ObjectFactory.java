
package com.autodesk.schemas.technical.common.requestheaderv1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.autodesk.schemas.technical.common.requestheaderv1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Header_QNAME = new QName("http://www.autodesk.com/schemas/Technical/Common/RequestHeaderV1.0", "Header");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.autodesk.schemas.technical.common.requestheaderv1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HeaderType }
     * 
     */
    public HeaderType createHeaderType() {
        return new HeaderType();
    }

    /**
     * Create an instance of {@link HeaderType.MessageIdentifier }
     * 
     */
    public HeaderType.MessageIdentifier createHeaderTypeMessageIdentifier() {
        return new HeaderType.MessageIdentifier();
    }

    /**
     * Create an instance of {@link HeaderType.Profile }
     * 
     */
    public HeaderType.Profile createHeaderTypeProfile() {
        return new HeaderType.Profile();
    }

    /**
     * Create an instance of {@link HeaderType.Transaction }
     * 
     */
    public HeaderType.Transaction createHeaderTypeTransaction() {
        return new HeaderType.Transaction();
    }

    /**
     * Create an instance of {@link HeaderType.RequestingSystem }
     * 
     */
    public HeaderType.RequestingSystem createHeaderTypeRequestingSystem() {
        return new HeaderType.RequestingSystem();
    }

    /**
     * Create an instance of {@link HeaderType.Properties }
     * 
     */
    public HeaderType.Properties createHeaderTypeProperties() {
        return new HeaderType.Properties();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HeaderType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/Common/RequestHeaderV1.0", name = "Header")
    public JAXBElement<HeaderType> createHeader(HeaderType value) {
        return new JAXBElement<HeaderType>(_Header_QNAME, HeaderType.class, null, value);
    }

}
