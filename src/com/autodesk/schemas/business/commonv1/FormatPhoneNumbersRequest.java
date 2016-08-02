
package com.autodesk.schemas.business.commonv1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="ListOfRequestedAttributes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Attribute" type="{http://www.w3.org/2001/XMLSchema}normalizedString" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ListOfPhoneNumbers" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence minOccurs="0">
 *                   &lt;element name="PhoneNumber" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}PhoneNumberRefType" maxOccurs="unbounded" minOccurs="0"/>
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
    "listOfRequestedAttributes",
    "listOfPhoneNumbers"
})
@XmlRootElement(name = "FormatPhoneNumbersRequest")
public class FormatPhoneNumbersRequest {

    @XmlElement(name = "ListOfRequestedAttributes")
    protected FormatPhoneNumbersRequest.ListOfRequestedAttributes listOfRequestedAttributes;
    @XmlElement(name = "ListOfPhoneNumbers")
    protected FormatPhoneNumbersRequest.ListOfPhoneNumbers listOfPhoneNumbers;

    /**
     * Gets the value of the listOfRequestedAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link FormatPhoneNumbersRequest.ListOfRequestedAttributes }
     *     
     */
    public FormatPhoneNumbersRequest.ListOfRequestedAttributes getListOfRequestedAttributes() {
        return listOfRequestedAttributes;
    }

    /**
     * Sets the value of the listOfRequestedAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormatPhoneNumbersRequest.ListOfRequestedAttributes }
     *     
     */
    public void setListOfRequestedAttributes(FormatPhoneNumbersRequest.ListOfRequestedAttributes value) {
        this.listOfRequestedAttributes = value;
    }

    /**
     * Gets the value of the listOfPhoneNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link FormatPhoneNumbersRequest.ListOfPhoneNumbers }
     *     
     */
    public FormatPhoneNumbersRequest.ListOfPhoneNumbers getListOfPhoneNumbers() {
        return listOfPhoneNumbers;
    }

    /**
     * Sets the value of the listOfPhoneNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormatPhoneNumbersRequest.ListOfPhoneNumbers }
     *     
     */
    public void setListOfPhoneNumbers(FormatPhoneNumbersRequest.ListOfPhoneNumbers value) {
        this.listOfPhoneNumbers = value;
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
     *       &lt;sequence minOccurs="0">
     *         &lt;element name="PhoneNumber" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}PhoneNumberRefType" maxOccurs="unbounded" minOccurs="0"/>
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
        "phoneNumber"
    })
    public static class ListOfPhoneNumbers {

        @XmlElement(name = "PhoneNumber")
        protected List<PhoneNumberRefType> phoneNumber;

        /**
         * Gets the value of the phoneNumber property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the phoneNumber property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPhoneNumber().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PhoneNumberRefType }
         * 
         * 
         */
        public List<PhoneNumberRefType> getPhoneNumber() {
            if (phoneNumber == null) {
                phoneNumber = new ArrayList<PhoneNumberRefType>();
            }
            return this.phoneNumber;
        }

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
     *         &lt;element name="Attribute" type="{http://www.w3.org/2001/XMLSchema}normalizedString" maxOccurs="unbounded" minOccurs="0"/>
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
        "attribute"
    })
    public static class ListOfRequestedAttributes {

        @XmlElement(name = "Attribute")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected List<String> attribute;

        /**
         * Gets the value of the attribute property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the attribute property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAttribute().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getAttribute() {
            if (attribute == null) {
                attribute = new ArrayList<String>();
            }
            return this.attribute;
        }

    }

}
