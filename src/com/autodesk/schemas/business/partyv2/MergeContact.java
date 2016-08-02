
package com.autodesk.schemas.business.partyv2;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="SurvivorContact" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactType"/>
 *         &lt;element name="VictimContact" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactType" maxOccurs="unbounded"/>
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
    "survivorContact",
    "victimContact"
})
@XmlRootElement(name = "MergeContact")
public class MergeContact {

    @XmlElement(name = "SurvivorContact", required = true)
    protected ContactType survivorContact;
    @XmlElement(name = "VictimContact", required = true)
    protected List<ContactType> victimContact;

    /**
     * Gets the value of the survivorContact property.
     * 
     * @return
     *     possible object is
     *     {@link ContactType }
     *     
     */
    public ContactType getSurvivorContact() {
        return survivorContact;
    }

    /**
     * Sets the value of the survivorContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactType }
     *     
     */
    public void setSurvivorContact(ContactType value) {
        this.survivorContact = value;
    }

    /**
     * Gets the value of the victimContact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the victimContact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVictimContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactType }
     * 
     * 
     */
    public List<ContactType> getVictimContact() {
        if (victimContact == null) {
            victimContact = new ArrayList<ContactType>();
        }
        return this.victimContact;
    }

}
