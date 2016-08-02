
package com.autodesk.schemas.business.productv1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.autodesk.schemas.business.commonv1.ListOfAttributeType;


/**
 * <p>Java class for ProductCoreDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductCoreDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductBaseDataType">
 *       &lt;sequence>
 *         &lt;element name="AuthorizationRequired" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Behavior" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="EndUserType" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="LockingMechanism" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="Media" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="MinorRelease" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="ProductUpgradeFrom" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="ProductIndustry" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="ProgramType" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="PromotionCode" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="RegistrationMethod" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="ReleaseType" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="SpecialSalesProgram" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="Upgradeable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="UPI" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="ListOfOtherAttribute" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}ListOfAttributeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductCoreDataType", propOrder = {
    "authorizationRequired",
    "behavior",
    "endUserType",
    "lockingMechanism",
    "media",
    "minorRelease",
    "productUpgradeFrom",
    "productIndustry",
    "programType",
    "promotionCode",
    "registrationMethod",
    "releaseType",
    "specialSalesProgram",
    "upgradeable",
    "upi",
    "listOfOtherAttribute"
})
@XmlSeeAlso({
    ProductType.class
})
public class ProductCoreDataType
    extends ProductBaseDataType
{

    @XmlElement(name = "AuthorizationRequired")
    protected Boolean authorizationRequired;
    @XmlElement(name = "Behavior")
    protected ProductAttributeType behavior;
    @XmlElement(name = "EndUserType")
    protected ProductAttributeType endUserType;
    @XmlElement(name = "LockingMechanism")
    protected ProductAttributeType lockingMechanism;
    @XmlElement(name = "Media")
    protected ProductAttributeType media;
    @XmlElement(name = "MinorRelease")
    protected ProductAttributeType minorRelease;
    @XmlElement(name = "ProductUpgradeFrom")
    protected ProductAttributeType productUpgradeFrom;
    @XmlElement(name = "ProductIndustry")
    protected Object productIndustry;
    @XmlElement(name = "ProgramType")
    protected ProductAttributeType programType;
    @XmlElement(name = "PromotionCode")
    protected ProductAttributeType promotionCode;
    @XmlElement(name = "RegistrationMethod")
    protected ProductAttributeType registrationMethod;
    @XmlElement(name = "ReleaseType")
    protected ProductAttributeType releaseType;
    @XmlElement(name = "SpecialSalesProgram")
    protected ProductAttributeType specialSalesProgram;
    @XmlElement(name = "Upgradeable")
    protected Boolean upgradeable;
    @XmlElement(name = "UPI")
    protected Object upi;
    @XmlElement(name = "ListOfOtherAttribute")
    protected ListOfAttributeType listOfOtherAttribute;

    /**
     * Gets the value of the authorizationRequired property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAuthorizationRequired() {
        return authorizationRequired;
    }

    /**
     * Sets the value of the authorizationRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAuthorizationRequired(Boolean value) {
        this.authorizationRequired = value;
    }

    /**
     * Gets the value of the behavior property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getBehavior() {
        return behavior;
    }

    /**
     * Sets the value of the behavior property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setBehavior(ProductAttributeType value) {
        this.behavior = value;
    }

    /**
     * Gets the value of the endUserType property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getEndUserType() {
        return endUserType;
    }

    /**
     * Sets the value of the endUserType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setEndUserType(ProductAttributeType value) {
        this.endUserType = value;
    }

    /**
     * Gets the value of the lockingMechanism property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getLockingMechanism() {
        return lockingMechanism;
    }

    /**
     * Sets the value of the lockingMechanism property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setLockingMechanism(ProductAttributeType value) {
        this.lockingMechanism = value;
    }

    /**
     * Gets the value of the media property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getMedia() {
        return media;
    }

    /**
     * Sets the value of the media property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setMedia(ProductAttributeType value) {
        this.media = value;
    }

    /**
     * Gets the value of the minorRelease property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getMinorRelease() {
        return minorRelease;
    }

    /**
     * Sets the value of the minorRelease property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setMinorRelease(ProductAttributeType value) {
        this.minorRelease = value;
    }

    /**
     * Gets the value of the productUpgradeFrom property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getProductUpgradeFrom() {
        return productUpgradeFrom;
    }

    /**
     * Sets the value of the productUpgradeFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setProductUpgradeFrom(ProductAttributeType value) {
        this.productUpgradeFrom = value;
    }

    /**
     * Gets the value of the productIndustry property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getProductIndustry() {
        return productIndustry;
    }

    /**
     * Sets the value of the productIndustry property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setProductIndustry(Object value) {
        this.productIndustry = value;
    }

    /**
     * Gets the value of the programType property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getProgramType() {
        return programType;
    }

    /**
     * Sets the value of the programType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setProgramType(ProductAttributeType value) {
        this.programType = value;
    }

    /**
     * Gets the value of the promotionCode property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getPromotionCode() {
        return promotionCode;
    }

    /**
     * Sets the value of the promotionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setPromotionCode(ProductAttributeType value) {
        this.promotionCode = value;
    }

    /**
     * Gets the value of the registrationMethod property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getRegistrationMethod() {
        return registrationMethod;
    }

    /**
     * Sets the value of the registrationMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setRegistrationMethod(ProductAttributeType value) {
        this.registrationMethod = value;
    }

    /**
     * Gets the value of the releaseType property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getReleaseType() {
        return releaseType;
    }

    /**
     * Sets the value of the releaseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setReleaseType(ProductAttributeType value) {
        this.releaseType = value;
    }

    /**
     * Gets the value of the specialSalesProgram property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getSpecialSalesProgram() {
        return specialSalesProgram;
    }

    /**
     * Sets the value of the specialSalesProgram property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setSpecialSalesProgram(ProductAttributeType value) {
        this.specialSalesProgram = value;
    }

    /**
     * Gets the value of the upgradeable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUpgradeable() {
        return upgradeable;
    }

    /**
     * Sets the value of the upgradeable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUpgradeable(Boolean value) {
        this.upgradeable = value;
    }

    /**
     * Gets the value of the upi property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUPI() {
        return upi;
    }

    /**
     * Sets the value of the upi property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUPI(Object value) {
        this.upi = value;
    }

    /**
     * Gets the value of the listOfOtherAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfAttributeType }
     *     
     */
    public ListOfAttributeType getListOfOtherAttribute() {
        return listOfOtherAttribute;
    }

    /**
     * Sets the value of the listOfOtherAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfAttributeType }
     *     
     */
    public void setListOfOtherAttribute(ListOfAttributeType value) {
        this.listOfOtherAttribute = value;
    }

}
