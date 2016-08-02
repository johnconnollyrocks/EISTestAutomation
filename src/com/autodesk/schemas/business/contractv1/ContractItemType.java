
package com.autodesk.schemas.business.contractv1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.autodesk.schemas.business.assetv1.AssetLiteDataType;
import com.autodesk.schemas.business.productv1.ProductSupportProgramType;


/**
 * <p>Java class for ContractItemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ItemId" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="SupportProgram" type="{http://www.autodesk.com/schemas/Business/ProductV1.0}ProductSupportProgramType" minOccurs="0"/>
 *         &lt;element name="ListOfAsset" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Asset" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}AssetLiteDataType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractItemType", propOrder = {
    "itemId",
    "name",
    "supportProgram",
    "listOfAsset",
    "listOfEntitlement"
})
public class ContractItemType {

    @XmlElement(name = "ItemId")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String itemId;
    @XmlElement(name = "Name")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String name;
    @XmlElement(name = "SupportProgram")
    protected ProductSupportProgramType supportProgram;
    @XmlElement(name = "ListOfAsset")
    protected ContractItemType.ListOfAsset listOfAsset;
    @XmlElement(name = "ListOfEntitlement")
    protected ContractItemType.ListOfEntitlement listOfEntitlement;

    /**
     * Gets the value of the itemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Sets the value of the itemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemId(String value) {
        this.itemId = value;
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
     * Gets the value of the supportProgram property.
     * 
     * @return
     *     possible object is
     *     {@link ProductSupportProgramType }
     *     
     */
    public ProductSupportProgramType getSupportProgram() {
        return supportProgram;
    }

    /**
     * Sets the value of the supportProgram property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductSupportProgramType }
     *     
     */
    public void setSupportProgram(ProductSupportProgramType value) {
        this.supportProgram = value;
    }

    /**
     * Gets the value of the listOfAsset property.
     * 
     * @return
     *     possible object is
     *     {@link ContractItemType.ListOfAsset }
     *     
     */
    public ContractItemType.ListOfAsset getListOfAsset() {
        return listOfAsset;
    }

    /**
     * Sets the value of the listOfAsset property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractItemType.ListOfAsset }
     *     
     */
    public void setListOfAsset(ContractItemType.ListOfAsset value) {
        this.listOfAsset = value;
    }

    /**
     * Gets the value of the listOfEntitlement property.
     * 
     * @return
     *     possible object is
     *     {@link ContractItemType.ListOfEntitlement }
     *     
     */
    public ContractItemType.ListOfEntitlement getListOfEntitlement() {
        return listOfEntitlement;
    }

    /**
     * Sets the value of the listOfEntitlement property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractItemType.ListOfEntitlement }
     *     
     */
    public void setListOfEntitlement(ContractItemType.ListOfEntitlement value) {
        this.listOfEntitlement = value;
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
     *         &lt;element name="Asset" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}AssetLiteDataType" maxOccurs="unbounded" minOccurs="0"/>
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
        "asset"
    })
    public static class ListOfAsset {

        @XmlElement(name = "Asset")
        protected List<AssetLiteDataType> asset;

        /**
         * Gets the value of the asset property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the asset property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAsset().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AssetLiteDataType }
         * 
         * 
         */
        public List<AssetLiteDataType> getAsset() {
            if (asset == null) {
                asset = new ArrayList<AssetLiteDataType>();
            }
            return this.asset;
        }

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
