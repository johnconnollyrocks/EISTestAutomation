
package com.autodesk.schemas.business.commonv1;

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
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="ListOfPhoneNumbers" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence minOccurs="0">
 *                   &lt;element name="PhoneNumber" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}PhoneNumberBaseType" maxOccurs="unbounded" minOccurs="0"/>
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
    "listOfPhoneNumbers"
})
@XmlRootElement(name = "FormatPhoneNumbersResponse")
public class FormatPhoneNumbersResponse {

    @XmlElement(name = "ListOfPhoneNumbers")
    protected FormatPhoneNumbersResponse.ListOfPhoneNumbers listOfPhoneNumbers;

    /**
     * Gets the value of the listOfPhoneNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link FormatPhoneNumbersResponse.ListOfPhoneNumbers }
     *     
     */
    public FormatPhoneNumbersResponse.ListOfPhoneNumbers getListOfPhoneNumbers() {
        return listOfPhoneNumbers;
    }

    /**
     * Sets the value of the listOfPhoneNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormatPhoneNumbersResponse.ListOfPhoneNumbers }
     *     
     */
    public void setListOfPhoneNumbers(FormatPhoneNumbersResponse.ListOfPhoneNumbers value) {
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
     *         &lt;element name="PhoneNumber" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}PhoneNumberBaseType" maxOccurs="unbounded" minOccurs="0"/>
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
        protected List<PhoneNumberBaseType> phoneNumber;

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
         * {@link PhoneNumberBaseType }
         * 
         * 
         */
        public List<PhoneNumberBaseType> getPhoneNumber() {
            if (phoneNumber == null) {
                phoneNumber = new ArrayList<PhoneNumberBaseType>();
            }
            return this.phoneNumber;
        }

    }

}
