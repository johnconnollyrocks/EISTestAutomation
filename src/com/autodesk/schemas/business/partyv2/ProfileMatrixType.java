
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProfileMatrixType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProfileMatrixType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PartnerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PartnerPosition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BaseLine" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Sales" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Service" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Marketing" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Finance" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Operations" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IVAROperations" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfileMatrixType", propOrder = {
    "partnerType",
    "partnerPosition",
    "baseLine",
    "sales",
    "service",
    "marketing",
    "finance",
    "operations",
    "ivarOperations"
})
public class ProfileMatrixType {

    @XmlElement(name = "PartnerType")
    protected String partnerType;
    @XmlElement(name = "PartnerPosition")
    protected String partnerPosition;
    @XmlElement(name = "BaseLine")
    protected Boolean baseLine;
    @XmlElement(name = "Sales")
    protected Boolean sales;
    @XmlElement(name = "Service")
    protected Boolean service;
    @XmlElement(name = "Marketing")
    protected Boolean marketing;
    @XmlElement(name = "Finance")
    protected Boolean finance;
    @XmlElement(name = "Operations")
    protected Boolean operations;
    @XmlElement(name = "IVAROperations")
    protected Boolean ivarOperations;

    /**
     * Gets the value of the partnerType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerType() {
        return partnerType;
    }

    /**
     * Sets the value of the partnerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerType(String value) {
        this.partnerType = value;
    }

    /**
     * Gets the value of the partnerPosition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerPosition() {
        return partnerPosition;
    }

    /**
     * Sets the value of the partnerPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerPosition(String value) {
        this.partnerPosition = value;
    }

    /**
     * Gets the value of the baseLine property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBaseLine() {
        return baseLine;
    }

    /**
     * Sets the value of the baseLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBaseLine(Boolean value) {
        this.baseLine = value;
    }

    /**
     * Gets the value of the sales property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSales() {
        return sales;
    }

    /**
     * Sets the value of the sales property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSales(Boolean value) {
        this.sales = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setService(Boolean value) {
        this.service = value;
    }

    /**
     * Gets the value of the marketing property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMarketing() {
        return marketing;
    }

    /**
     * Sets the value of the marketing property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMarketing(Boolean value) {
        this.marketing = value;
    }

    /**
     * Gets the value of the finance property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFinance() {
        return finance;
    }

    /**
     * Sets the value of the finance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFinance(Boolean value) {
        this.finance = value;
    }

    /**
     * Gets the value of the operations property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOperations() {
        return operations;
    }

    /**
     * Sets the value of the operations property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOperations(Boolean value) {
        this.operations = value;
    }

    /**
     * Gets the value of the ivarOperations property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIVAROperations() {
        return ivarOperations;
    }

    /**
     * Sets the value of the ivarOperations property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIVAROperations(Boolean value) {
        this.ivarOperations = value;
    }

}
