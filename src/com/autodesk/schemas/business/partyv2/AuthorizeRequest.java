
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
 *         &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{http://www.autodesk.com/schemas/Business/PartyV2.0}ListOfClaimRequests" minOccurs="0"/>
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
    "guid",
    "listOfClaimRequests"
})
@XmlRootElement(name = "AuthorizeRequest")
public class AuthorizeRequest {

    @XmlElement(name = "GUID", required = true)
    protected String guid;
    @XmlElement(name = "ListOfClaimRequests")
    protected ListOfClaimRequests listOfClaimRequests;

    /**
     * Gets the value of the guid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGUID() {
        return guid;
    }

    /**
     * Sets the value of the guid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGUID(String value) {
        this.guid = value;
    }

    /**
     * Gets the value of the listOfClaimRequests property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfClaimRequests }
     *     
     */
    public ListOfClaimRequests getListOfClaimRequests() {
        return listOfClaimRequests;
    }

    /**
     * Sets the value of the listOfClaimRequests property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfClaimRequests }
     *     
     */
    public void setListOfClaimRequests(ListOfClaimRequests value) {
        this.listOfClaimRequests = value;
    }

}
