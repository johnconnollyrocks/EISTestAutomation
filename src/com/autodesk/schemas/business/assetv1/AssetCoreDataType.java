
package com.autodesk.schemas.business.assetv1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.autodesk.schemas.business.contractv1.ContractRefDataType;
import com.autodesk.schemas.business.partyv2.AccountCoreDataType;
import com.autodesk.schemas.business.partyv2.AccountRefDataType;
import com.autodesk.schemas.business.partyv2.ContactCoreDataType;


/**
 * Extends AssetBaseDataType and adds core end user details
 * 
 * <p>Java class for AssetCoreDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AssetCoreDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/AssetV1.0}AssetBaseDataType">
 *       &lt;sequence>
 *         &lt;element name="RelationshipAfter" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}ListOfAssetRelationship" minOccurs="0"/>
 *         &lt;element name="RelationshipBefore" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}ListOfAssetRelationship" minOccurs="0"/>
 *         &lt;element name="AssetContractRef" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractRefDataType" minOccurs="0"/>
 *         &lt;element name="EndUserContact" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactCoreDataType" minOccurs="0"/>
 *         &lt;element name="EndUserParty" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountCoreDataType" minOccurs="0"/>
 *         &lt;element name="ResellerPartyRef" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountRefDataType" minOccurs="0"/>
 *         &lt;element name="SoldToPartyRef" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountRefDataType" minOccurs="0"/>
 *         &lt;element name="UnderExportControlReview" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="UnderLegalReview" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssetCoreDataType", propOrder = {
    "relationshipAfter",
    "relationshipBefore",
    "assetContractRef",
    "endUserContact",
    "endUserParty",
    "resellerPartyRef",
    "soldToPartyRef",
    "underExportControlReview",
    "underLegalReview"
})
@XmlSeeAlso({
    AssetType.class
})
public class AssetCoreDataType
    extends AssetBaseDataType
{

    @XmlElement(name = "RelationshipAfter")
    protected ListOfAssetRelationship relationshipAfter;
    @XmlElement(name = "RelationshipBefore")
    protected ListOfAssetRelationship relationshipBefore;
    @XmlElement(name = "AssetContractRef")
    protected ContractRefDataType assetContractRef;
    @XmlElement(name = "EndUserContact")
    protected ContactCoreDataType endUserContact;
    @XmlElement(name = "EndUserParty")
    protected AccountCoreDataType endUserParty;
    @XmlElement(name = "ResellerPartyRef")
    protected AccountRefDataType resellerPartyRef;
    @XmlElement(name = "SoldToPartyRef")
    protected AccountRefDataType soldToPartyRef;
    @XmlElement(name = "UnderExportControlReview")
    protected Boolean underExportControlReview;
    @XmlElement(name = "UnderLegalReview")
    protected Boolean underLegalReview;

    /**
     * Gets the value of the relationshipAfter property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfAssetRelationship }
     *     
     */
    public ListOfAssetRelationship getRelationshipAfter() {
        return relationshipAfter;
    }

    /**
     * Sets the value of the relationshipAfter property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfAssetRelationship }
     *     
     */
    public void setRelationshipAfter(ListOfAssetRelationship value) {
        this.relationshipAfter = value;
    }

    /**
     * Gets the value of the relationshipBefore property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfAssetRelationship }
     *     
     */
    public ListOfAssetRelationship getRelationshipBefore() {
        return relationshipBefore;
    }

    /**
     * Sets the value of the relationshipBefore property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfAssetRelationship }
     *     
     */
    public void setRelationshipBefore(ListOfAssetRelationship value) {
        this.relationshipBefore = value;
    }

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
     *     {@link ContactCoreDataType }
     *     
     */
    public ContactCoreDataType getEndUserContact() {
        return endUserContact;
    }

    /**
     * Sets the value of the endUserContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactCoreDataType }
     *     
     */
    public void setEndUserContact(ContactCoreDataType value) {
        this.endUserContact = value;
    }

    /**
     * Gets the value of the endUserParty property.
     * 
     * @return
     *     possible object is
     *     {@link AccountCoreDataType }
     *     
     */
    public AccountCoreDataType getEndUserParty() {
        return endUserParty;
    }

    /**
     * Sets the value of the endUserParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountCoreDataType }
     *     
     */
    public void setEndUserParty(AccountCoreDataType value) {
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

}
