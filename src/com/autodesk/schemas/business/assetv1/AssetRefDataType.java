
package com.autodesk.schemas.business.assetv1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.autodesk.schemas.business.productv1.ProductAttributeType;


/**
 * Defines user keys that can be used to reference asset uniquely
 * 
 * <p>Java class for AssetRefDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AssetRefDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AssetNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ProductLine" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductAttributeType" minOccurs="0"/>
 *         &lt;element name="SerialNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssetRefDataType", propOrder = {
    "assetNumber",
    "id",
    "productLine",
    "serialNumber"
})
@XmlSeeAlso({
    AssetBaseDataType.class,
    AssetLiteDataType.class
})
public class AssetRefDataType {

    @XmlElement(name = "AssetNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String assetNumber;
    @XmlElement(name = "Id")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String id;
    @XmlElement(name = "ProductLine")
    protected ProductAttributeType productLine;
    @XmlElement(name = "SerialNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String serialNumber;

    /**
     * Gets the value of the assetNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssetNumber() {
        return assetNumber;
    }

    /**
     * Sets the value of the assetNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssetNumber(String value) {
        this.assetNumber = value;
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

}
