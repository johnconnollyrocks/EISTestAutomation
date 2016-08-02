
package com.autodesk.schemas.business.contractv1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import com.autodesk.schemas.business.commonv1.ListOfAttributeType;
import com.autodesk.schemas.business.partyv2.ContactRefDataType;


/**
 * <p>Java class for EntitlementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EntitlementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EntitlementAttributes" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}ListOfAttributeType" minOccurs="0"/>
 *         &lt;element name="EntitlementID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="EntitlementEndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="EntitlementName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="EntitlementStartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="EntitlementType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ListOfContact" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Contact" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactRefDataType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "EntitlementType", propOrder = {
    "entitlementAttributes",
    "entitlementID",
    "entitlementEndDate",
    "entitlementName",
    "entitlementStartDate",
    "entitlementType",
    "listOfContact"
})
public class EntitlementType {

    @XmlElement(name = "EntitlementAttributes")
    protected ListOfAttributeType entitlementAttributes;
    @XmlElement(name = "EntitlementID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String entitlementID;
    @XmlElement(name = "EntitlementEndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar entitlementEndDate;
    @XmlElement(name = "EntitlementName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String entitlementName;
    @XmlElement(name = "EntitlementStartDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar entitlementStartDate;
    @XmlElement(name = "EntitlementType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String entitlementType;
    @XmlElement(name = "ListOfContact")
    protected EntitlementType.ListOfContact listOfContact;

    /**
     * Gets the value of the entitlementAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfAttributeType }
     *     
     */
    public ListOfAttributeType getEntitlementAttributes() {
        return entitlementAttributes;
    }

    /**
     * Sets the value of the entitlementAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfAttributeType }
     *     
     */
    public void setEntitlementAttributes(ListOfAttributeType value) {
        this.entitlementAttributes = value;
    }

    /**
     * Gets the value of the entitlementID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntitlementID() {
        return entitlementID;
    }

    /**
     * Sets the value of the entitlementID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntitlementID(String value) {
        this.entitlementID = value;
    }

    /**
     * Gets the value of the entitlementEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEntitlementEndDate() {
        return entitlementEndDate;
    }

    /**
     * Sets the value of the entitlementEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEntitlementEndDate(XMLGregorianCalendar value) {
        this.entitlementEndDate = value;
    }

    /**
     * Gets the value of the entitlementName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntitlementName() {
        return entitlementName;
    }

    /**
     * Sets the value of the entitlementName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntitlementName(String value) {
        this.entitlementName = value;
    }

    /**
     * Gets the value of the entitlementStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEntitlementStartDate() {
        return entitlementStartDate;
    }

    /**
     * Sets the value of the entitlementStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEntitlementStartDate(XMLGregorianCalendar value) {
        this.entitlementStartDate = value;
    }

    /**
     * Gets the value of the entitlementType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntitlementType() {
        return entitlementType;
    }

    /**
     * Sets the value of the entitlementType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntitlementType(String value) {
        this.entitlementType = value;
    }

    /**
     * Gets the value of the listOfContact property.
     * 
     * @return
     *     possible object is
     *     {@link EntitlementType.ListOfContact }
     *     
     */
    public EntitlementType.ListOfContact getListOfContact() {
        return listOfContact;
    }

    /**
     * Sets the value of the listOfContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntitlementType.ListOfContact }
     *     
     */
    public void setListOfContact(EntitlementType.ListOfContact value) {
        this.listOfContact = value;
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
     *         &lt;element name="Contact" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactRefDataType" maxOccurs="unbounded" minOccurs="0"/>
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
        "contact"
    })
    public static class ListOfContact {

        @XmlElement(name = "Contact")
        protected List<ContactRefDataType> contact;

        /**
         * Gets the value of the contact property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the contact property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContact().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ContactRefDataType }
         * 
         * 
         */
        public List<ContactRefDataType> getContact() {
            if (contact == null) {
                contact = new ArrayList<ContactRefDataType>();
            }
            return this.contact;
        }

    }

}
