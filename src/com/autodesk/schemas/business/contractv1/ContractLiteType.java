
package com.autodesk.schemas.business.contractv1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.autodesk.schemas.business.partyv2.AccountBaseDataType;
import com.autodesk.schemas.business.partyv2.ContactRefDataType;


/**
 * This type contains all contract details with key references to other entities
 * 
 * <p>Java class for ContractLiteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractLiteType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractBaseDataType">
 *       &lt;sequence>
 *         &lt;element name="EndUserContactRef" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactRefDataType" minOccurs="0"/>
 *         &lt;element name="EndUserPartyRef" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountBaseDataType" minOccurs="0"/>
 *         &lt;element name="NumberOfAdditionalNamedCallers" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="TechSupportCoordContactRef" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactRefDataType" minOccurs="0"/>
 *         &lt;element name="ListOfContractItem" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ContractItem" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractItemType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractLiteType", propOrder = {
    "endUserContactRef",
    "endUserPartyRef",
    "numberOfAdditionalNamedCallers",
    "techSupportCoordContactRef",
    "listOfContractItem"
})
public class ContractLiteType
    extends ContractBaseDataType
{

    @XmlElement(name = "EndUserContactRef")
    protected ContactRefDataType endUserContactRef;
    @XmlElement(name = "EndUserPartyRef")
    protected AccountBaseDataType endUserPartyRef;
    @XmlElement(name = "NumberOfAdditionalNamedCallers")
    protected BigInteger numberOfAdditionalNamedCallers;
    @XmlElement(name = "TechSupportCoordContactRef")
    protected ContactRefDataType techSupportCoordContactRef;
    @XmlElement(name = "ListOfContractItem")
    protected ContractLiteType.ListOfContractItem listOfContractItem;

    /**
     * Gets the value of the endUserContactRef property.
     * 
     * @return
     *     possible object is
     *     {@link ContactRefDataType }
     *     
     */
    public ContactRefDataType getEndUserContactRef() {
        return endUserContactRef;
    }

    /**
     * Sets the value of the endUserContactRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactRefDataType }
     *     
     */
    public void setEndUserContactRef(ContactRefDataType value) {
        this.endUserContactRef = value;
    }

    /**
     * Gets the value of the endUserPartyRef property.
     * 
     * @return
     *     possible object is
     *     {@link AccountBaseDataType }
     *     
     */
    public AccountBaseDataType getEndUserPartyRef() {
        return endUserPartyRef;
    }

    /**
     * Sets the value of the endUserPartyRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountBaseDataType }
     *     
     */
    public void setEndUserPartyRef(AccountBaseDataType value) {
        this.endUserPartyRef = value;
    }

    /**
     * Gets the value of the numberOfAdditionalNamedCallers property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfAdditionalNamedCallers() {
        return numberOfAdditionalNamedCallers;
    }

    /**
     * Sets the value of the numberOfAdditionalNamedCallers property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfAdditionalNamedCallers(BigInteger value) {
        this.numberOfAdditionalNamedCallers = value;
    }

    /**
     * Gets the value of the techSupportCoordContactRef property.
     * 
     * @return
     *     possible object is
     *     {@link ContactRefDataType }
     *     
     */
    public ContactRefDataType getTechSupportCoordContactRef() {
        return techSupportCoordContactRef;
    }

    /**
     * Sets the value of the techSupportCoordContactRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactRefDataType }
     *     
     */
    public void setTechSupportCoordContactRef(ContactRefDataType value) {
        this.techSupportCoordContactRef = value;
    }

    /**
     * Gets the value of the listOfContractItem property.
     * 
     * @return
     *     possible object is
     *     {@link ContractLiteType.ListOfContractItem }
     *     
     */
    public ContractLiteType.ListOfContractItem getListOfContractItem() {
        return listOfContractItem;
    }

    /**
     * Sets the value of the listOfContractItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractLiteType.ListOfContractItem }
     *     
     */
    public void setListOfContractItem(ContractLiteType.ListOfContractItem value) {
        this.listOfContractItem = value;
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
     *         &lt;element name="ContractItem" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractItemType" maxOccurs="unbounded" minOccurs="0"/>
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
        "contractItem"
    })
    public static class ListOfContractItem {

        @XmlElement(name = "ContractItem")
        protected List<ContractItemType> contractItem;

        /**
         * Gets the value of the contractItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the contractItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContractItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ContractItemType }
         * 
         * 
         */
        public List<ContractItemType> getContractItem() {
            if (contractItem == null) {
                contractItem = new ArrayList<ContractItemType>();
            }
            return this.contractItem;
        }

    }

}
