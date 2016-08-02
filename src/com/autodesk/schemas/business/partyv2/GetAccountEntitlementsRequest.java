
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
 *         &lt;element ref="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountRefData"/>
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
    "accountRefData"
})
@XmlRootElement(name = "GetAccountEntitlementsRequest")
public class GetAccountEntitlementsRequest {

    @XmlElement(name = "AccountRefData", required = true)
    protected AccountRefDataType accountRefData;

    /**
     * Gets the value of the accountRefData property.
     * 
     * @return
     *     possible object is
     *     {@link AccountRefDataType }
     *     
     */
    public AccountRefDataType getAccountRefData() {
        return accountRefData;
    }

    /**
     * Sets the value of the accountRefData property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountRefDataType }
     *     
     */
    public void setAccountRefData(AccountRefDataType value) {
        this.accountRefData = value;
    }

}
