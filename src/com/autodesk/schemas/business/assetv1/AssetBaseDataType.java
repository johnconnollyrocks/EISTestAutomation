
package com.autodesk.schemas.business.assetv1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import com.autodesk.schemas.business.contractv1.EntitlementType;
import com.autodesk.schemas.business.productv1.ProductCoreDataType;


/**
 * Extends AssetRefDataType and adds base asset attributes
 * 
 * <p>Java class for AssetBaseDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AssetBaseDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/AssetV1.0}AssetRefDataType">
 *       &lt;sequence>
 *         &lt;element name="AssetEndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="AssetProduct" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductCoreDataType" minOccurs="0"/>
 *         &lt;element name="AssetRegisteredDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="Deployment" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="GroupName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="LicensingModel" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="RenewOnlineEligible" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="RenewOnlineStatus" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="RenewOnlineURL" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Seats" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="SubscriptionLevel" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="SubscriptionEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SubscriptionStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ETR" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ETRStartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ETREndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="NFRUse" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ListOfEntitlement" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Entitlement" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}EntitlementType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="isUnderSubscription" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="isUpgradeable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssetBaseDataType", propOrder = {
    "assetEndDate",
    "assetProduct",
    "assetRegisteredDate",
    "deployment",
    "groupName",
    "licensingModel",
    "renewOnlineEligible",
    "renewOnlineStatus",
    "renewOnlineURL",
    "seats",
    "status",
    "subscriptionLevel",
    "subscriptionEndDate",
    "subscriptionStartDate",
    "etr",
    "etrStartDate",
    "etrEndDate",
    "nfrUse",
    "listOfEntitlement"
})
@XmlSeeAlso({
    AssetExtDataType.class,
    AssetCoreDataType.class
})
public class AssetBaseDataType
    extends AssetRefDataType
{

    @XmlElement(name = "AssetEndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar assetEndDate;
    @XmlElement(name = "AssetProduct")
    protected ProductCoreDataType assetProduct;
    @XmlElement(name = "AssetRegisteredDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar assetRegisteredDate;
    @XmlElement(name = "Deployment")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String deployment;
    @XmlElement(name = "GroupName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String groupName;
    @XmlElement(name = "LicensingModel")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String licensingModel;
    @XmlElement(name = "RenewOnlineEligible")
    protected Boolean renewOnlineEligible;
    @XmlElement(name = "RenewOnlineStatus")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String renewOnlineStatus;
    @XmlElement(name = "RenewOnlineURL")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String renewOnlineURL;
    @XmlElement(name = "Seats")
    protected Integer seats;
    @XmlElement(name = "Status")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String status;
    @XmlElement(name = "SubscriptionLevel")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String subscriptionLevel;
    @XmlElement(name = "SubscriptionEndDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar subscriptionEndDate;
    @XmlElement(name = "SubscriptionStartDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar subscriptionStartDate;
    @XmlElement(name = "ETR")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String etr;
    @XmlElement(name = "ETRStartDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar etrStartDate;
    @XmlElement(name = "ETREndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar etrEndDate;
    @XmlElement(name = "NFRUse")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String nfrUse;
    @XmlElement(name = "ListOfEntitlement")
    protected AssetBaseDataType.ListOfEntitlement listOfEntitlement;
    @XmlAttribute(name = "isUnderSubscription")
    protected Boolean isUnderSubscription;
    @XmlAttribute(name = "isUpgradeable")
    protected Boolean isUpgradeable;

    /**
     * Gets the value of the assetEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAssetEndDate() {
        return assetEndDate;
    }

    /**
     * Sets the value of the assetEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAssetEndDate(XMLGregorianCalendar value) {
        this.assetEndDate = value;
    }

    /**
     * Gets the value of the assetProduct property.
     * 
     * @return
     *     possible object is
     *     {@link ProductCoreDataType }
     *     
     */
    public ProductCoreDataType getAssetProduct() {
        return assetProduct;
    }

    /**
     * Sets the value of the assetProduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductCoreDataType }
     *     
     */
    public void setAssetProduct(ProductCoreDataType value) {
        this.assetProduct = value;
    }

    /**
     * Gets the value of the assetRegisteredDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAssetRegisteredDate() {
        return assetRegisteredDate;
    }

    /**
     * Sets the value of the assetRegisteredDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAssetRegisteredDate(XMLGregorianCalendar value) {
        this.assetRegisteredDate = value;
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
     * Gets the value of the licensingModel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicensingModel() {
        return licensingModel;
    }

    /**
     * Sets the value of the licensingModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicensingModel(String value) {
        this.licensingModel = value;
    }

    /**
     * Gets the value of the renewOnlineEligible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRenewOnlineEligible() {
        return renewOnlineEligible;
    }

    /**
     * Sets the value of the renewOnlineEligible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRenewOnlineEligible(Boolean value) {
        this.renewOnlineEligible = value;
    }

    /**
     * Gets the value of the renewOnlineStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRenewOnlineStatus() {
        return renewOnlineStatus;
    }

    /**
     * Sets the value of the renewOnlineStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRenewOnlineStatus(String value) {
        this.renewOnlineStatus = value;
    }

    /**
     * Gets the value of the renewOnlineURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRenewOnlineURL() {
        return renewOnlineURL;
    }

    /**
     * Sets the value of the renewOnlineURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRenewOnlineURL(String value) {
        this.renewOnlineURL = value;
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
     * Gets the value of the subscriptionLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscriptionLevel() {
        return subscriptionLevel;
    }

    /**
     * Sets the value of the subscriptionLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscriptionLevel(String value) {
        this.subscriptionLevel = value;
    }

    /**
     * Gets the value of the subscriptionEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    /**
     * Sets the value of the subscriptionEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSubscriptionEndDate(XMLGregorianCalendar value) {
        this.subscriptionEndDate = value;
    }

    /**
     * Gets the value of the subscriptionStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    /**
     * Sets the value of the subscriptionStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSubscriptionStartDate(XMLGregorianCalendar value) {
        this.subscriptionStartDate = value;
    }

    /**
     * Gets the value of the etr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getETR() {
        return etr;
    }

    /**
     * Sets the value of the etr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setETR(String value) {
        this.etr = value;
    }

    /**
     * Gets the value of the etrStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getETRStartDate() {
        return etrStartDate;
    }

    /**
     * Sets the value of the etrStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setETRStartDate(XMLGregorianCalendar value) {
        this.etrStartDate = value;
    }

    /**
     * Gets the value of the etrEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getETREndDate() {
        return etrEndDate;
    }

    /**
     * Sets the value of the etrEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setETREndDate(XMLGregorianCalendar value) {
        this.etrEndDate = value;
    }

    /**
     * Gets the value of the nfrUse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNFRUse() {
        return nfrUse;
    }

    /**
     * Sets the value of the nfrUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNFRUse(String value) {
        this.nfrUse = value;
    }

    /**
     * Gets the value of the listOfEntitlement property.
     * 
     * @return
     *     possible object is
     *     {@link AssetBaseDataType.ListOfEntitlement }
     *     
     */
    public AssetBaseDataType.ListOfEntitlement getListOfEntitlement() {
        return listOfEntitlement;
    }

    /**
     * Sets the value of the listOfEntitlement property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssetBaseDataType.ListOfEntitlement }
     *     
     */
    public void setListOfEntitlement(AssetBaseDataType.ListOfEntitlement value) {
        this.listOfEntitlement = value;
    }

    /**
     * Gets the value of the isUnderSubscription property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUnderSubscription() {
        return isUnderSubscription;
    }

    /**
     * Sets the value of the isUnderSubscription property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUnderSubscription(Boolean value) {
        this.isUnderSubscription = value;
    }

    /**
     * Gets the value of the isUpgradeable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUpgradeable() {
        return isUpgradeable;
    }

    /**
     * Sets the value of the isUpgradeable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUpgradeable(Boolean value) {
        this.isUpgradeable = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Entitlement" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}EntitlementType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entitlement"
    })
    public static class ListOfEntitlement {

        @XmlElement(name = "Entitlement")
        protected List<EntitlementType> entitlement;

        /**
         * Gets the value of the entitlement property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entitlement property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntitlement().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EntitlementType }
         * 
         * 
         */
        public List<EntitlementType> getEntitlement() {
            if (entitlement == null) {
                entitlement = new ArrayList<EntitlementType>();
            }
            return this.entitlement;
        }

    }

}
