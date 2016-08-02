
package com.autodesk.schemas.business.assetv1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.autodesk.schemas.business.commonv1.ListOfActivityType;
import com.autodesk.schemas.business.contractv1.ContractRefDataType;
import com.autodesk.schemas.business.partyv2.AccountExtDataType;
import com.autodesk.schemas.business.partyv2.AccountRefDataType;
import com.autodesk.schemas.business.partyv2.ContactExtDataType;


/**
 * Extends AssetBaseDataType and adds full end user details
 * 
 * <p>Java class for AssetExtDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AssetExtDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/AssetV1.0}AssetBaseDataType">
 *       &lt;sequence>
 *         &lt;element name="AssetContractRef" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractRefDataType" minOccurs="0"/>
 *         &lt;element name="EndUserContact" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactExtDataType" minOccurs="0"/>
 *         &lt;element name="EndUserParty" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountExtDataType" minOccurs="0"/>
 *         &lt;element name="ResellerPartyRef" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountRefDataType" minOccurs="0"/>
 *         &lt;element name="SoldToPartyRef" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountRefDataType" minOccurs="0"/>
 *         &lt;element name="UnderExportControlReview" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="UnderLegalReview" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ListOfActivity" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}ListOfActivityType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssetExtDataType", propOrder = {
    "assetContractRef",
    "endUserContact",
    "endUserParty",
    "resellerPartyRef",
    "soldToPartyRef",
    "underExportControlReview",
    "underLegalReview",
    "listOfActivity"
})
public class AssetExtDataType
    extends AssetBaseDataType
{

    @XmlElement(name = "AssetContractRef")
    protected ContractRefDataType assetContractRef;
    @XmlElement(name = "EndUserContact")
    protected ContactExtDataType endUserContact;
    @XmlElement(name = "EndUserParty")
    protected AccountExtDataType endUserParty;
    @XmlElement(name = "ResellerPartyRef")
    protected AccountRefDataType resellerPartyRef;
    @XmlElement(name = "SoldToPartyRef")
    protected AccountRefDataType soldToPartyRef;
    @XmlElement(name = "UnderExportControlReview")
    protected Boolean underExportControlReview;
    @XmlElement(name = "UnderLegalReview")
    protected Boolean underLegalReview;
    @XmlElement(name = "ListOfActivity")
    protected ListOfActivityType listOfActivity;

    /**
     * Gets the value of the assetContractRef property.
     * 
     * @return
     *     possible object is
     *     {@link ContractRefDataType }
     *     
     */
    public ContractRefDataType getAssetContractRef() {
        return assetContractRef;
    }

    /**
     * Sets the value of the assetContractRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractRefDataType }
     *     
     */
    public void setAssetContractRef(ContractRefDataType value) {
        this.assetContractRef = value;
    }

    /**
     * Gets the value of the endUserContact property.
     * 
     * @return
     *     possible object is
     *     {@link ContactExtDataType }
     *     
     */
    public ContactExtDataType getEndUserContact() {
        return endUserContact;
    }

    /**
     * Sets the value of the endUserContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactExtDataType }
     *     
     */
    public void setEndUserContact(ContactExtDataType value) {
        this.endUserContact = value;
    }

    /**
     * Gets the value of the endUserParty property.
     * 
     * @return
     *     possible object is
     *     {@link AccountExtDataType }
     *     
     */
    public AccountExtDataType getEndUserParty() {
        return endUserParty;
    }

    /**
     * Sets the value of the endUserParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountExtDataType }
     *     
     */
    public void setEndUserParty(AccountExtDataType value) {
        this.endUserParty = value;
    }

    /**
     * Gets the value of the resellerPartyRef property.
     * 
     * @return
     *     possible object is
     *     {@link AccountRefDataType }
     *     
     */
    public AccountRefDataType getResellerPartyRef() {
        return resellerPartyRef;
    }

    /**
     * Sets the value of the resellerPartyRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountRefDataType }
     *     
     */
    public void setResellerPartyRef(AccountRefDataType value) {
        this.resellerPartyRef = value;
    }

    /**
     * Gets the value of the soldToPartyRef property.
     * 
     * @return
     *     possible object is
     *     {@link AccountRefDataType }
     *     
     */
    public AccountRefDataType getSoldToPartyRef() {
        return soldToPartyRef;
    }

    /**
     * Sets the value of the soldToPartyRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountRefDataType }
     *     
     */
    public void setSoldToPartyRef(AccountRefDataType value) {
        this.soldToPartyRef = value;
    }

    /**
     * Gets the value of the underExportControlReview property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUnderExportControlReview() {
        return underExportControlReview;
    }

    /**
     * Sets the value of the underExportControlReview property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUnderExportControlReview(Boolean value) {
        this.underExportControlReview = value;
    }

    /**
     * Gets the value of the underLegalReview property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUnderLegalReview() {
        return underLegalReview;
    }

    /**
     * Sets the value of the underLegalReview property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUnderLegalReview(Boolean value) {
        this.underLegalReview = value;
    }

    /**
     * Gets the value of the listOfActivity property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfActivityType }
     *     
     */
    public ListOfActivityType getListOfActivity() {
        return listOfActivity;
    }

    /**
     * Sets the value of the listOfActivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfActivityType }
     *     
     */
    public void setListOfActivity(ListOfActivityType value) {
        this.listOfActivity = value;
    }

}
