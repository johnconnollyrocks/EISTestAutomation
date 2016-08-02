
package com.autodesk.schemas.business.assetv1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.autodesk.schemas.business.contractv1.ContractCoreDataType;
import com.autodesk.schemas.business.partyv2.ContactCoreDataType;


/**
 * Asset, Contract, User data used for In Product web service interface
 * 
 * <p>Java class for InProductResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InProductResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AssetData" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}AssetBaseDataType" minOccurs="0"/>
 *         &lt;element name="AssetContract" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractCoreDataType" minOccurs="0"/>
 *         &lt;element name="AssetUser" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactCoreDataType" minOccurs="0"/>
 *         &lt;element name="ListOfCourse" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}ListOfCourseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InProductResponseType", propOrder = {
    "assetData",
    "assetContract",
    "assetUser",
    "listOfCourse"
})
public class InProductResponseType {

    @XmlElement(name = "AssetData")
    protected AssetBaseDataType assetData;
    @XmlElement(name = "AssetContract")
    protected ContractCoreDataType assetContract;
    @XmlElement(name = "AssetUser")
    protected ContactCoreDataType assetUser;
    @XmlElement(name = "ListOfCourse")
    protected ListOfCourseType listOfCourse;

    /**
     * Gets the value of the assetData property.
     * 
     * @return
     *     possible object is
     *     {@link AssetBaseDataType }
     *     
     */
    public AssetBaseDataType getAssetData() {
        return assetData;
    }

    /**
     * Sets the value of the assetData property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssetBaseDataType }
     *     
     */
    public void setAssetData(AssetBaseDataType value) {
        this.assetData = value;
    }

    /**
     * Gets the value of the assetContract property.
     * 
     * @return
     *     possible object is
     *     {@link ContractCoreDataType }
     *     
     */
    public ContractCoreDataType getAssetContract() {
        return assetContract;
    }

    /**
     * Sets the value of the assetContract property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractCoreDataType }
     *     
     */
    public void setAssetContract(ContractCoreDataType value) {
        this.assetContract = value;
    }

    /**
     * Gets the value of the assetUser property.
     * 
     * @return
     *     possible object is
     *     {@link ContactCoreDataType }
     *     
     */
    public ContactCoreDataType getAssetUser() {
        return assetUser;
    }

    /**
     * Sets the value of the assetUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactCoreDataType }
     *     
     */
    public void setAssetUser(ContactCoreDataType value) {
        this.assetUser = value;
    }

    /**
     * Gets the value of the listOfCourse property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfCourseType }
     *     
     */
    public ListOfCourseType getListOfCourse() {
        return listOfCourse;
    }

    /**
     * Sets the value of the listOfCourse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfCourseType }
     *     
     */
    public void setListOfCourse(ListOfCourseType value) {
        this.listOfCourse = value;
    }

}
