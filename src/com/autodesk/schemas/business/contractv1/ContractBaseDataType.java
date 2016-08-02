
package com.autodesk.schemas.business.contractv1;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import com.autodesk.schemas.business.commonv1.UsageTypeEnum;


/**
 * <p>Java class for ContractBaseDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractBaseDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractRefDataType">
 *       &lt;sequence>
 *         &lt;element name="SiebelRowID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ContractEndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ContractStartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ContractStatus" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ContractSubType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ContractTerm" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="ContractType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ContractOrderType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ContractUsageType" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}UsageTypeEnum" minOccurs="0"/>
 *         &lt;element name="ContractOfferingType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsNewContract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsRenewContract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RecurringType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RecurringTerm" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractAttributeType" minOccurs="0"/>
 *         &lt;element name="BillingBehavior" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractAttributeType" minOccurs="0"/>
 *         &lt;element name="ContractTermUOM" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractBaseDataType", propOrder = {
    "siebelRowID",
    "contractEndDate",
    "contractStartDate",
    "contractStatus",
    "contractSubType",
    "contractTerm",
    "contractType",
    "contractOrderType",
    "contractUsageType",
    "contractOfferingType",
    "isNewContract",
    "isRenewContract",
    "recurringType",
    "recurringTerm",
    "billingBehavior",
    "contractTermUOM"
})
@XmlSeeAlso({
    ContractLiteType.class,
    ContractCoreDataType.class
})
public class ContractBaseDataType
    extends ContractRefDataType
{

    @XmlElement(name = "SiebelRowID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String siebelRowID;
    @XmlElement(name = "ContractEndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar contractEndDate;
    @XmlElement(name = "ContractStartDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar contractStartDate;
    @XmlElement(name = "ContractStatus")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String contractStatus;
    @XmlElement(name = "ContractSubType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String contractSubType;
    @XmlElement(name = "ContractTerm")
    protected BigInteger contractTerm;
    @XmlElement(name = "ContractType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String contractType;
    @XmlElement(name = "ContractOrderType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String contractOrderType;
    @XmlElement(name = "ContractUsageType")
    protected UsageTypeEnum contractUsageType;
    @XmlElement(name = "ContractOfferingType")
    protected String contractOfferingType;
    @XmlElement(name = "IsNewContract")
    protected String isNewContract;
    @XmlElement(name = "IsRenewContract")
    protected String isRenewContract;
    @XmlElement(name = "RecurringType")
    protected String recurringType;
    @XmlElement(name = "RecurringTerm")
    protected ContractAttributeType recurringTerm;
    @XmlElement(name = "BillingBehavior")
    protected ContractAttributeType billingBehavior;
    @XmlElement(name = "ContractTermUOM")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String contractTermUOM;

    /**
     * Gets the value of the siebelRowID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiebelRowID() {
        return siebelRowID;
    }

    /**
     * Sets the value of the siebelRowID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiebelRowID(String value) {
        this.siebelRowID = value;
    }

    /**
     * Gets the value of the contractEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getContractEndDate() {
        return contractEndDate;
    }

    /**
     * Sets the value of the contractEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setContractEndDate(XMLGregorianCalendar value) {
        this.contractEndDate = value;
    }

    /**
     * Gets the value of the contractStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getContractStartDate() {
        return contractStartDate;
    }

    /**
     * Sets the value of the contractStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setContractStartDate(XMLGregorianCalendar value) {
        this.contractStartDate = value;
    }

    /**
     * Gets the value of the contractStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractStatus() {
        return contractStatus;
    }

    /**
     * Sets the value of the contractStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractStatus(String value) {
        this.contractStatus = value;
    }

    /**
     * Gets the value of the contractSubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractSubType() {
        return contractSubType;
    }

    /**
     * Sets the value of the contractSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractSubType(String value) {
        this.contractSubType = value;
    }

    /**
     * Gets the value of the contractTerm property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getContractTerm() {
        return contractTerm;
    }

    /**
     * Sets the value of the contractTerm property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setContractTerm(BigInteger value) {
        this.contractTerm = value;
    }

    /**
     * Gets the value of the contractType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * Sets the value of the contractType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractType(String value) {
        this.contractType = value;
    }

    /**
     * Gets the value of the contractOrderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractOrderType() {
        return contractOrderType;
    }

    /**
     * Sets the value of the contractOrderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractOrderType(String value) {
        this.contractOrderType = value;
    }

    /**
     * Gets the value of the contractUsageType property.
     * 
     * @return
     *     possible object is
     *     {@link UsageTypeEnum }
     *     
     */
    public UsageTypeEnum getContractUsageType() {
        return contractUsageType;
    }

    /**
     * Sets the value of the contractUsageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link UsageTypeEnum }
     *     
     */
    public void setContractUsageType(UsageTypeEnum value) {
        this.contractUsageType = value;
    }

    /**
     * Gets the value of the contractOfferingType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractOfferingType() {
        return contractOfferingType;
    }

    /**
     * Sets the value of the contractOfferingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractOfferingType(String value) {
        this.contractOfferingType = value;
    }

    /**
     * Gets the value of the isNewContract property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsNewContract() {
        return isNewContract;
    }

    /**
     * Sets the value of the isNewContract property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsNewContract(String value) {
        this.isNewContract = value;
    }

    /**
     * Gets the value of the isRenewContract property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsRenewContract() {
        return isRenewContract;
    }

    /**
     * Sets the value of the isRenewContract property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsRenewContract(String value) {
        this.isRenewContract = value;
    }

    /**
     * Gets the value of the recurringType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecurringType() {
        return recurringType;
    }

    /**
     * Sets the value of the recurringType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecurringType(String value) {
        this.recurringType = value;
    }

    /**
     * Gets the value of the recurringTerm property.
     * 
     * @return
     *     possible object is
     *     {@link ContractAttributeType }
     *     
     */
    public ContractAttributeType getRecurringTerm() {
        return recurringTerm;
    }

    /**
     * Sets the value of the recurringTerm property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractAttributeType }
     *     
     */
    public void setRecurringTerm(ContractAttributeType value) {
        this.recurringTerm = value;
    }

    /**
     * Gets the value of the billingBehavior property.
     * 
     * @return
     *     possible object is
     *     {@link ContractAttributeType }
     *     
     */
    public ContractAttributeType getBillingBehavior() {
        return billingBehavior;
    }

    /**
     * Sets the value of the billingBehavior property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractAttributeType }
     *     
     */
    public void setBillingBehavior(ContractAttributeType value) {
        this.billingBehavior = value;
    }

    /**
     * Gets the value of the contractTermUOM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractTermUOM() {
        return contractTermUOM;
    }

    /**
     * Sets the value of the contractTermUOM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractTermUOM(String value) {
        this.contractTermUOM = value;
    }

}
