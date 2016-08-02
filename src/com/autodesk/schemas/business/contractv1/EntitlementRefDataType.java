
package com.autodesk.schemas.business.contractv1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for EntitlementRefDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EntitlementRefDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ContractItemName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ContractReference" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractRefDataType" minOccurs="0"/>
 *         &lt;element name="EntitlementID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="EntitlementName" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntitlementRefDataType", propOrder = {
    "contractItemName",
    "contractReference",
    "entitlementID",
    "entitlementName"
})
public class EntitlementRefDataType {

    @XmlElement(name = "ContractItemName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String contractItemName;
    @XmlElement(name = "ContractReference")
    protected ContractRefDataType contractReference;
    @XmlElement(name = "EntitlementID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String entitlementID;
    @XmlElement(name = "EntitlementName")
    protected Object entitlementName;

    /**
     * Gets the value of the contractItemName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractItemName() {
        return contractItemName;
    }

    /**
     * Sets the value of the contractItemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractItemName(String value) {
        this.contractItemName = value;
    }

    /**
     * Gets the value of the contractReference property.
     * 
     * @return
     *     possible object is
     *     {@link ContractRefDataType }
     *     
     */
    public ContractRefDataType getContractReference() {
        return contractReference;
    }

    /**
     * Sets the value of the contractReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractRefDataType }
     *     
     */
    public void setContractReference(ContractRefDataType value) {
        this.contractReference = value;
    }

    /**
     * Gets the value of the entitlementID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntitlementID() {
        return entitlementID;
    }

    /**
     * Sets the value of the entitlementID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntitlementID(String value) {
        this.entitlementID = value;
    }

    /**
     * Gets the value of the entitlementName property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getEntitlementName() {
        return entitlementName;
    }

    /**
     * Sets the value of the entitlementName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setEntitlementName(Object value) {
        this.entitlementName = value;
    }

}
