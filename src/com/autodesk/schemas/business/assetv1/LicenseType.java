
package com.autodesk.schemas.business.assetv1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.autodesk.schemas.business.commonv1.ListOfMessageType;


/**
 * <p>Java class for LicenseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LicenseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="AssetSubscriptionStartDate" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="AssetSubscriptionEndDate" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ContractEndDate" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ContractStartDate" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="GroupNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="EUAccountame" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="EUAccountNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Deployment" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GroupName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LicenseBehavior" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="LicenseType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="PartNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ProductKey" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ProductLine" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ProductLineCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="RegisteredDate" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Seats" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SerialNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="SubscriptionFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="UsageType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="UpgradableFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ListOfMessage" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}ListOfMessageType" minOccurs="0"/>
 *         &lt;element name="ListOfProductLine" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}ListOfProductLineType" minOccurs="0"/>
 *         &lt;element name="RelationshipFrom" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}ListOfRelationshipType" minOccurs="0"/>
 *         &lt;element name="RelationshipTo" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}ListOfRelationshipType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LicenseType", propOrder = {
    "contractNumber",
    "assetSubscriptionStartDate",
    "assetSubscriptionEndDate",
    "contractEndDate",
    "contractStartDate",
    "groupNumber",
    "euAccountame",
    "euAccountNumber",
    "deployment",
    "description",
    "groupName",
    "id",
    "licenseBehavior",
    "licenseType",
    "partNumber",
    "productKey",
    "productLine",
    "productLineCode",
    "registeredDate",
    "seats",
    "serialNumber",
    "status",
    "subscriptionFlag",
    "usageType",
    "upgradableFlag",
    "listOfMessage",
    "listOfProductLine",
    "relationshipFrom",
    "relationshipTo"
})
public class LicenseType {

    @XmlElement(name = "ContractNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String contractNumber;
    @XmlElement(name = "AssetSubscriptionStartDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String assetSubscriptionStartDate;
    @XmlElement(name = "AssetSubscriptionEndDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String assetSubscriptionEndDate;
    @XmlElement(name = "ContractEndDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String contractEndDate;
    @XmlElement(name = "ContractStartDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String contractStartDate;
    @XmlElement(name = "GroupNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String groupNumber;
    @XmlElement(name = "EUAccountame")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String euAccountame;
    @XmlElement(name = "EUAccountNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String euAccountNumber;
    @XmlElement(name = "Deployment")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String deployment;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "GroupName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String groupName;
    @XmlElement(name = "Id")
    protected String id;
    @XmlElement(name = "LicenseBehavior")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String licenseBehavior;
    @XmlElement(name = "LicenseType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String licenseType;
    @XmlElement(name = "PartNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String partNumber;
    @XmlElement(name = "ProductKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String productKey;
    @XmlElement(name = "ProductLine")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String productLine;
    @XmlElement(name = "ProductLineCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String productLineCode;
    @XmlElement(name = "RegisteredDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String registeredDate;
    @XmlElement(name = "Seats")
    protected Integer seats;
    @XmlElement(name = "SerialNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String serialNumber;
    @XmlElement(name = "Status")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String status;
    @XmlElement(name = "SubscriptionFlag", defaultValue = "false")
    protected Boolean subscriptionFlag;
    @XmlElement(name = "UsageType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String usageType;
    @XmlElement(name = "UpgradableFlag", defaultValue = "false")
    protected Boolean upgradableFlag;
    @XmlElement(name = "ListOfMessage")
    protected ListOfMessageType listOfMessage;
    @XmlElement(name = "ListOfProductLine")
    protected ListOfProductLineType listOfProductLine;
    @XmlElement(name = "RelationshipFrom")
    protected ListOfRelationshipType relationshipFrom;
    @XmlElement(name = "RelationshipTo")
    protected ListOfRelationshipType relationshipTo;

    /**
     * Gets the value of the contractNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * Sets the value of the contractNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNumber(String value) {
        this.contractNumber = value;
    }

    /**
     * Gets the value of the assetSubscriptionStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssetSubscriptionStartDate() {
        return assetSubscriptionStartDate;
    }

    /**
     * Sets the value of the assetSubscriptionStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssetSubscriptionStartDate(String value) {
        this.assetSubscriptionStartDate = value;
    }

    /**
     * Gets the value of the assetSubscriptionEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssetSubscriptionEndDate() {
        return assetSubscriptionEndDate;
    }

    /**
     * Sets the value of the assetSubscriptionEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssetSubscriptionEndDate(String value) {
        this.assetSubscriptionEndDate = value;
    }

    /**
     * Gets the value of the contractEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractEndDate() {
        return contractEndDate;
    }

    /**
     * Sets the value of the contractEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractEndDate(String value) {
        this.contractEndDate = value;
    }

    /**
     * Gets the value of the contractStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractStartDate() {
        return contractStartDate;
    }

    /**
     * Sets the value of the contractStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractStartDate(String value) {
        this.contractStartDate = value;
    }

    /**
     * Gets the value of the groupNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupNumber() {
        return groupNumber;
    }

    /**
     * Sets the value of the groupNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupNumber(String value) {
        this.groupNumber = value;
    }

    /**
     * Gets the value of the euAccountame property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEUAccountame() {
        return euAccountame;
    }

    /**
     * Sets the value of the euAccountame property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEUAccountame(String value) {
        this.euAccountame = value;
    }

    /**
     * Gets the value of the euAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEUAccountNumber() {
        return euAccountNumber;
    }

    /**
     * Sets the value of the euAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEUAccountNumber(String value) {
        this.euAccountNumber = value;
    }

    /**
     * Gets the value of the deployment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeployment() {
        return deployment;
    }

    /**
     * Sets the value of the deployment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeployment(String value) {
        this.deployment = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the groupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the value of the groupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupName(String value) {
        this.groupName = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the licenseBehavior property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseBehavior() {
        return licenseBehavior;
    }

    /**
     * Sets the value of the licenseBehavior property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseBehavior(String value) {
        this.licenseBehavior = value;
    }

    /**
     * Gets the value of the licenseType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseType() {
        return licenseType;
    }

    /**
     * Sets the value of the licenseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseType(String value) {
        this.licenseType = value;
    }

    /**
     * Gets the value of the partNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * Sets the value of the partNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartNumber(String value) {
        this.partNumber = value;
    }

    /**
     * Gets the value of the productKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductKey() {
        return productKey;
    }

    /**
     * Sets the value of the productKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductKey(String value) {
        this.productKey = value;
    }

    /**
     * Gets the value of the productLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductLine() {
        return productLine;
    }

    /**
     * Sets the value of the productLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductLine(String value) {
        this.productLine = value;
    }

    /**
     * Gets the value of the productLineCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductLineCode() {
        return productLineCode;
    }

    /**
     * Sets the value of the productLineCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductLineCode(String value) {
        this.productLineCode = value;
    }

    /**
     * Gets the value of the registeredDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegisteredDate() {
        return registeredDate;
    }

    /**
     * Sets the value of the registeredDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegisteredDate(String value) {
        this.registeredDate = value;
    }

    /**
     * Gets the value of the seats property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSeats() {
        return seats;
    }

    /**
     * Sets the value of the seats property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSeats(Integer value) {
        this.seats = value;
    }

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the subscriptionFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSubscriptionFlag() {
        return subscriptionFlag;
    }

    /**
     * Sets the value of the subscriptionFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSubscriptionFlag(Boolean value) {
        this.subscriptionFlag = value;
    }

    /**
     * Gets the value of the usageType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsageType() {
        return usageType;
    }

    /**
     * Sets the value of the usageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsageType(String value) {
        this.usageType = value;
    }

    /**
     * Gets the value of the upgradableFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUpgradableFlag() {
        return upgradableFlag;
    }

    /**
     * Sets the value of the upgradableFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUpgradableFlag(Boolean value) {
        this.upgradableFlag = value;
    }

    /**
     * Gets the value of the listOfMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfMessageType }
     *     
     */
    public ListOfMessageType getListOfMessage() {
        return listOfMessage;
    }

    /**
     * Sets the value of the listOfMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfMessageType }
     *     
     */
    public void setListOfMessage(ListOfMessageType value) {
        this.listOfMessage = value;
    }

    /**
     * Gets the value of the listOfProductLine property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfProductLineType }
     *     
     */
    public ListOfProductLineType getListOfProductLine() {
        return listOfProductLine;
    }

    /**
     * Sets the value of the listOfProductLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfProductLineType }
     *     
     */
    public void setListOfProductLine(ListOfProductLineType value) {
        this.listOfProductLine = value;
    }

    /**
     * Gets the value of the relationshipFrom property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfRelationshipType }
     *     
     */
    public ListOfRelationshipType getRelationshipFrom() {
        return relationshipFrom;
    }

    /**
     * Sets the value of the relationshipFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfRelationshipType }
     *     
     */
    public void setRelationshipFrom(ListOfRelationshipType value) {
        this.relationshipFrom = value;
    }

    /**
     * Gets the value of the relationshipTo property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfRelationshipType }
     *     
     */
    public ListOfRelationshipType getRelationshipTo() {
        return relationshipTo;
    }

    /**
     * Sets the value of the relationshipTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfRelationshipType }
     *     
     */
    public void setRelationshipTo(ListOfRelationshipType value) {
        this.relationshipTo = value;
    }

}
