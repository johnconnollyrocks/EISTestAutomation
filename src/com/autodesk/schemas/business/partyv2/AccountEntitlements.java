
package com.autodesk.schemas.business.partyv2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.autodesk.schemas.business.contractv1.ContractLiteType;


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
 *         &lt;element name="Account" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountType" minOccurs="0"/>
 *         &lt;element name="ListOfContract" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Contract" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractLiteType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "", propOrder = {
    "account",
    "listOfContract"
})
@XmlRootElement(name = "AccountEntitlements")
public class AccountEntitlements {

    @XmlElement(name = "Account")
    protected AccountType account;
    @XmlElementRef(name = "ListOfContract", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<AccountEntitlements.ListOfContract> listOfContract;

    /**
     * Gets the value of the account property.
     * 
     * @return
     *     possible object is
     *     {@link AccountType }
     *     
     */
    public AccountType getAccount() {
        return account;
    }

    /**
     * Sets the value of the account property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountType }
     *     
     */
    public void setAccount(AccountType value) {
        this.account = value;
    }

    /**
     * Gets the value of the listOfContract property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link AccountEntitlements.ListOfContract }{@code >}
     *     
     */
    public JAXBElement<AccountEntitlements.ListOfContract> getListOfContract() {
        return listOfContract;
    }

    /**
     * Sets the value of the listOfContract property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link AccountEntitlements.ListOfContract }{@code >}
     *     
     */
    public void setListOfContract(JAXBElement<AccountEntitlements.ListOfContract> value) {
        this.listOfContract = value;
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
     *         &lt;element name="Contract" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractLiteType" maxOccurs="unbounded" minOccurs="0"/>
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
        "contract"
    })
    public static class ListOfContract {

        @XmlElement(name = "Contract")
        protected List<ContractLiteType> contract;

        /**
         * Gets the value of the contract property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the contract property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContract().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ContractLiteType }
         * 
         * 
         */
        public List<ContractLiteType> getContract() {
            if (contract == null) {
                contract = new ArrayList<ContractLiteType>();
            }
            return this.contract;
        }

    }

}
