
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="ListOfAccount" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ListOfAccountType" minOccurs="0"/>
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
    "listOfAccount"
})
@XmlRootElement(name = "GetAccountResponse")
public class GetAccountResponse {

    @XmlElement(name = "ListOfAccount")
    protected ListOfAccountType listOfAccount;

    /**
     * Gets the value of the listOfAccount property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfAccountType }
     *     
     */
    public ListOfAccountType getListOfAccount() {
        return listOfAccount;
    }

    /**
     * Sets the value of the listOfAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfAccountType }
     *     
     */
    public void setListOfAccount(ListOfAccountType value) {
        this.listOfAccount = value;
    }

}
