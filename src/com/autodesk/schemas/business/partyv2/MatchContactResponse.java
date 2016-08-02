
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
 *         &lt;element ref="{http://www.autodesk.com/schemas/Business/PartyV2.0}ListOfContact"/>
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
    "listOfContact"
})
@XmlRootElement(name = "MatchContactResponse")
public class MatchContactResponse {

    @XmlElement(name = "ListOfContact", required = true)
    protected ListOfContactType listOfContact;

    /**
     * Gets the value of the listOfContact property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfContactType }
     *     
     */
    public ListOfContactType getListOfContact() {
        return listOfContact;
    }

    /**
     * Sets the value of the listOfContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfContactType }
     *     
     */
    public void setListOfContact(ListOfContactType value) {
        this.listOfContact = value;
    }

}
