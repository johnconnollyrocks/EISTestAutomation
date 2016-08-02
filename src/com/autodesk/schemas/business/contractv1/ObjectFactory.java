
package com.autodesk.schemas.business.contractv1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.autodesk.schemas.business.contractv1 package. 
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

    private final static QName _ListOfContract_QNAME = new QName("http://www.autodesk.com/schemas/Business/ContractV1.0", "ListOfContract");
    private final static QName _Contract_QNAME = new QName("http://www.autodesk.com/schemas/Business/ContractV1.0", "Contract");
    private final static QName _ContractRefDataTypeContractNumber_QNAME = new QName("http://www.autodesk.com/schemas/Business/ContractV1.0", "ContractNumber");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.autodesk.schemas.business.contractv1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ContractCoreDataType }
     * 
     */
    public ContractCoreDataType createContractCoreDataType() {
        return new ContractCoreDataType();
    }

    /**
     * Create an instance of {@link ContractCoreDataType.ListOfPermission }
     * 
     */
    public ContractCoreDataType.ListOfPermission createContractCoreDataTypeListOfPermission() {
        return new ContractCoreDataType.ListOfPermission();
    }

    /**
     * Create an instance of {@link ContractLiteType }
     * 
     */
    public ContractLiteType createContractLiteType() {
        return new ContractLiteType();
    }

    /**
     * Create an instance of {@link ContractItemType }
     * 
     */
    public ContractItemType createContractItemType() {
        return new ContractItemType();
    }

    /**
     * Create an instance of {@link EntitlementType }
     * 
     */
    public EntitlementType createEntitlementType() {
        return new EntitlementType();
    }

    /**
     * Create an instance of {@link ListOfContractType }
     * 
     */
    public ListOfContractType createListOfContractType() {
        return new ListOfContractType();
    }

    /**
     * Create an instance of {@link ContractType }
     * 
     */
    public ContractType createContractType() {
        return new ContractType();
    }

    /**
     * Create an instance of {@link EntitlementRefDataType }
     * 
     */
    public EntitlementRefDataType createEntitlementRefDataType() {
        return new EntitlementRefDataType();
    }

    /**
     * Create an instance of {@link ContractAttributeType }
     * 
     */
    public ContractAttributeType createContractAttributeType() {
        return new ContractAttributeType();
    }

    /**
     * Create an instance of {@link RefDocumentType }
     * 
     */
    public RefDocumentType createRefDocumentType() {
        return new RefDocumentType();
    }

    /**
     * Create an instance of {@link ContractRefDataType }
     * 
     */
    public ContractRefDataType createContractRefDataType() {
        return new ContractRefDataType();
    }

    /**
     * Create an instance of {@link ListOfRefDocumentType }
     * 
     */
    public ListOfRefDocumentType createListOfRefDocumentType() {
        return new ListOfRefDocumentType();
    }

    /**
     * Create an instance of {@link ContractBaseDataType }
     * 
     */
    public ContractBaseDataType createContractBaseDataType() {
        return new ContractBaseDataType();
    }

    /**
     * Create an instance of {@link ContractCoreDataType.ListOfContractItem }
     * 
     */
    public ContractCoreDataType.ListOfContractItem createContractCoreDataTypeListOfContractItem() {
        return new ContractCoreDataType.ListOfContractItem();
    }

    /**
     * Create an instance of {@link ContractCoreDataType.ListOfPermission.Permission }
     * 
     */
    public ContractCoreDataType.ListOfPermission.Permission createContractCoreDataTypeListOfPermissionPermission() {
        return new ContractCoreDataType.ListOfPermission.Permission();
    }

    /**
     * Create an instance of {@link ContractLiteType.ListOfContractItem }
     * 
     */
    public ContractLiteType.ListOfContractItem createContractLiteTypeListOfContractItem() {
        return new ContractLiteType.ListOfContractItem();
    }

    /**
     * Create an instance of {@link ContractItemType.ListOfAsset }
     * 
     */
    public ContractItemType.ListOfAsset createContractItemTypeListOfAsset() {
        return new ContractItemType.ListOfAsset();
    }

    /**
     * Create an instance of {@link ContractItemType.ListOfEntitlement }
     * 
     */
    public ContractItemType.ListOfEntitlement createContractItemTypeListOfEntitlement() {
        return new ContractItemType.ListOfEntitlement();
    }

    /**
     * Create an instance of {@link EntitlementType.ListOfContact }
     * 
     */
    public EntitlementType.ListOfContact createEntitlementTypeListOfContact() {
        return new EntitlementType.ListOfContact();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfContractType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/ContractV1.0", name = "ListOfContract")
    public JAXBElement<ListOfContractType> createListOfContract(ListOfContractType value) {
        return new JAXBElement<ListOfContractType>(_ListOfContract_QNAME, ListOfContractType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContractType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/ContractV1.0", name = "Contract")
    public JAXBElement<ContractType> createContract(ContractType value) {
        return new JAXBElement<ContractType>(_Contract_QNAME, ContractType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/ContractV1.0", name = "ContractNumber", scope = ContractRefDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContractRefDataTypeContractNumber(String value) {
        return new JAXBElement<String>(_ContractRefDataTypeContractNumber_QNAME, String.class, ContractRefDataType.class, value);
    }

}
