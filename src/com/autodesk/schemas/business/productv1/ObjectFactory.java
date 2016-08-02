
package com.autodesk.schemas.business.productv1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.autodesk.schemas.business.productv1 package. 
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

    private final static QName _ProductBaseData_QNAME = new QName("http://www.autodesk.com/schemas/Business/ProductV1.0", "ProductBaseData");
    private final static QName _ListOfProduct_QNAME = new QName("http://www.autodesk.com/schemas/Business/ProductV1.0", "ListOfProduct");
    private final static QName _Product_QNAME = new QName("http://www.autodesk.com/schemas/Business/ProductV1.0", "Product");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.autodesk.schemas.business.productv1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProductCoreDataType }
     * 
     */
    public ProductCoreDataType createProductCoreDataType() {
        return new ProductCoreDataType();
    }

    /**
     * Create an instance of {@link ProductBaseDataType }
     * 
     */
    public ProductBaseDataType createProductBaseDataType() {
        return new ProductBaseDataType();
    }

    /**
     * Create an instance of {@link ListOfProductType }
     * 
     */
    public ListOfProductType createListOfProductType() {
        return new ListOfProductType();
    }

    /**
     * Create an instance of {@link ListOfProductAttributeType }
     * 
     */
    public ListOfProductAttributeType createListOfProductAttributeType() {
        return new ListOfProductAttributeType();
    }

    /**
     * Create an instance of {@link ProductType }
     * 
     */
    public ProductType createProductType() {
        return new ProductType();
    }

    /**
     * Create an instance of {@link ProductSupportProgramType }
     * 
     */
    public ProductSupportProgramType createProductSupportProgramType() {
        return new ProductSupportProgramType();
    }

    /**
     * Create an instance of {@link ProductRefDataType }
     * 
     */
    public ProductRefDataType createProductRefDataType() {
        return new ProductRefDataType();
    }

    /**
     * Create an instance of {@link ProductAttributeType }
     * 
     */
    public ProductAttributeType createProductAttributeType() {
        return new ProductAttributeType();
    }

    /**
     * Create an instance of {@link ServiceProductBaseDataType }
     * 
     */
    public ServiceProductBaseDataType createServiceProductBaseDataType() {
        return new ServiceProductBaseDataType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProductBaseDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/ProductV1.0", name = "ProductBaseData")
    public JAXBElement<ProductBaseDataType> createProductBaseData(ProductBaseDataType value) {
        return new JAXBElement<ProductBaseDataType>(_ProductBaseData_QNAME, ProductBaseDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfProductType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/ProductV1.0", name = "ListOfProduct")
    public JAXBElement<ListOfProductType> createListOfProduct(ListOfProductType value) {
        return new JAXBElement<ListOfProductType>(_ListOfProduct_QNAME, ListOfProductType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProductCoreDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/ProductV1.0", name = "Product")
    public JAXBElement<ProductCoreDataType> createProduct(ProductCoreDataType value) {
        return new JAXBElement<ProductCoreDataType>(_Product_QNAME, ProductCoreDataType.class, null, value);
    }

}
