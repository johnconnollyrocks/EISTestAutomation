
package com.autodesk.schemas.business.productv1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ProductBaseDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductBaseDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductRefDataType">
 *       &lt;sequence>
 *         &lt;element name="AssetModifier" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="AssetType" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="Deployment" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="Language" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ProductLine" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="ProductLineDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductType" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="ProductUsage" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="ProductKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Release" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="ServiceSaleType" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="ServiceType" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="ShortName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="SubRelease" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="SubscriptionLevel" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="LicensingModel" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductBaseDataType", propOrder = {
    "assetModifier",
    "assetType",
    "deployment",
    "language",
    "name",
    "productLine",
    "productLineDescription",
    "productType",
    "productUsage",
    "productKey",
    "release",
    "serviceSaleType",
    "serviceType",
    "shortName",
    "subRelease",
    "subscriptionLevel",
    "licensingModel"
})
@XmlSeeAlso({
    ProductCoreDataType.class
})
public class ProductBaseDataType
    extends ProductRefDataType
{

    @XmlElement(name = "AssetModifier")
    protected ProductAttributeType assetModifier;
    @XmlElement(name = "AssetType")
    protected ProductAttributeType assetType;
    @XmlElement(name = "Deployment")
    protected ProductAttributeType deployment;
    @XmlElement(name = "Language")
    protected ProductAttributeType language;
    @XmlElement(name = "Name")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String name;
    @XmlElement(name = "ProductLine")
    protected ProductAttributeType productLine;
    @XmlElement(name = "ProductLineDescription")
    protected String productLineDescription;
    @XmlElement(name = "ProductType")
    protected ProductAttributeType productType;
    @XmlElement(name = "ProductUsage")
    protected ProductAttributeType productUsage;
    @XmlElement(name = "ProductKey")
    protected String productKey;
    @XmlElement(name = "Release")
    protected ProductAttributeType release;
    @XmlElement(name = "ServiceSaleType")
    protected ProductAttributeType serviceSaleType;
    @XmlElement(name = "ServiceType")
    protected ProductAttributeType serviceType;
    @XmlElement(name = "ShortName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String shortName;
    @XmlElement(name = "SubRelease")
    protected ProductAttributeType subRelease;
    @XmlElement(name = "SubscriptionLevel")
    protected ProductAttributeType subscriptionLevel;
    @XmlElement(name = "LicensingModel")
    protected ProductAttributeType licensingModel;

    /**
     * Gets the value of the assetModifier property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getAssetModifier() {
        return assetModifier;
    }

    /**
     * Sets the value of the assetModifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setAssetModifier(ProductAttributeType value) {
        this.assetModifier = value;
    }

    /**
     * Gets the value of the assetType property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getAssetType() {
        return assetType;
    }

    /**
     * Sets the value of the assetType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setAssetType(ProductAttributeType value) {
        this.assetType = value;
    }

    /**
     * Gets the value of the deployment property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getDeployment() {
        return deployment;
    }

    /**
     * Sets the value of the deployment property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setDeployment(ProductAttributeType value) {
        this.deployment = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setLanguage(ProductAttributeType value) {
        this.language = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the productLine property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getProductLine() {
        return productLine;
    }

    /**
     * Sets the value of the productLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setProductLine(ProductAttributeType value) {
        this.productLine = value;
    }

    /**
     * Gets the value of the productLineDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductLineDescription() {
        return productLineDescription;
    }

    /**
     * Sets the value of the productLineDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductLineDescription(String value) {
        this.productLineDescription = value;
    }

    /**
     * Gets the value of the productType property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getProductType() {
        return productType;
    }

    /**
     * Sets the value of the productType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setProductType(ProductAttributeType value) {
        this.productType = value;
    }

    /**
     * Gets the value of the productUsage property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getProductUsage() {
        return productUsage;
    }

    /**
     * Sets the value of the productUsage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setProductUsage(ProductAttributeType value) {
        this.productUsage = value;
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
     * Gets the value of the release property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getRelease() {
        return release;
    }

    /**
     * Sets the value of the release property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setRelease(ProductAttributeType value) {
        this.release = value;
    }

    /**
     * Gets the value of the serviceSaleType property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getServiceSaleType() {
        return serviceSaleType;
    }

    /**
     * Sets the value of the serviceSaleType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setServiceSaleType(ProductAttributeType value) {
        this.serviceSaleType = value;
    }

    /**
     * Gets the value of the serviceType property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getServiceType() {
        return serviceType;
    }

    /**
     * Sets the value of the serviceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setServiceType(ProductAttributeType value) {
        this.serviceType = value;
    }

    /**
     * Gets the value of the shortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Sets the value of the shortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortName(String value) {
        this.shortName = value;
    }

    /**
     * Gets the value of the subRelease property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getSubRelease() {
        return subRelease;
    }

    /**
     * Sets the value of the subRelease property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setSubRelease(ProductAttributeType value) {
        this.subRelease = value;
    }

    /**
     * Gets the value of the subscriptionLevel property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getSubscriptionLevel() {
        return subscriptionLevel;
    }

    /**
     * Sets the value of the subscriptionLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setSubscriptionLevel(ProductAttributeType value) {
        this.subscriptionLevel = value;
    }

    /**
     * Gets the value of the licensingModel property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAttributeType }
     *     
     */
    public ProductAttributeType getLicensingModel() {
        return licensingModel;
    }

    /**
     * Sets the value of the licensingModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAttributeType }
     *     
     */
    public void setLicensingModel(ProductAttributeType value) {
        this.licensingModel = value;
    }

}
