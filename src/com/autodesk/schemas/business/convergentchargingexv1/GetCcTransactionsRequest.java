
package com.autodesk.schemas.business.convergentchargingexv1;

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
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ListOfContractNumbers" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ListOfAccountCSN" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AccountCSN" type="{http://www.w3.org/2001/XMLSchema}normalizedString" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ListOfGuids" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ListOfJobUids" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="JobUid" type="{http://www.w3.org/2001/XMLSchema}normalizedString" maxOccurs="unbounded" minOccurs="0"/>
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
    "startDate",
    "endDate",
    "listOfContractNumbers",
    "listOfAccountCSN",
    "listOfGuids",
    "listOfJobUids"
})
@XmlRootElement(name = "GetCcTransactionsRequest")
public class GetCcTransactionsRequest {

    @XmlElement(name = "StartDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "EndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "ListOfContractNumbers")
    protected GetCcTransactionsRequest.ListOfContractNumbers listOfContractNumbers;
    @XmlElement(name = "ListOfAccountCSN")
    protected GetCcTransactionsRequest.ListOfAccountCSN listOfAccountCSN;
    @XmlElement(name = "ListOfGuids")
    protected GetCcTransactionsRequest.ListOfGuids listOfGuids;
    @XmlElement(name = "ListOfJobUids")
    protected GetCcTransactionsRequest.ListOfJobUids listOfJobUids;

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the listOfContractNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link GetCcTransactionsRequest.ListOfContractNumbers }
     *     
     */
    public GetCcTransactionsRequest.ListOfContractNumbers getListOfContractNumbers() {
        return listOfContractNumbers;
    }

    /**
     * Sets the value of the listOfContractNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCcTransactionsRequest.ListOfContractNumbers }
     *     
     */
    public void setListOfContractNumbers(GetCcTransactionsRequest.ListOfContractNumbers value) {
        this.listOfContractNumbers = value;
    }

    /**
     * Gets the value of the listOfAccountCSN property.
     * 
     * @return
     *     possible object is
     *     {@link GetCcTransactionsRequest.ListOfAccountCSN }
     *     
     */
    public GetCcTransactionsRequest.ListOfAccountCSN getListOfAccountCSN() {
        return listOfAccountCSN;
    }

    /**
     * Sets the value of the listOfAccountCSN property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCcTransactionsRequest.ListOfAccountCSN }
     *     
     */
    public void setListOfAccountCSN(GetCcTransactionsRequest.ListOfAccountCSN value) {
        this.listOfAccountCSN = value;
    }

    /**
     * Gets the value of the listOfGuids property.
     * 
     * @return
     *     possible object is
     *     {@link GetCcTransactionsRequest.ListOfGuids }
     *     
     */
    public GetCcTransactionsRequest.ListOfGuids getListOfGuids() {
        return listOfGuids;
    }

    /**
     * Sets the value of the listOfGuids property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCcTransactionsRequest.ListOfGuids }
     *     
     */
    public void setListOfGuids(GetCcTransactionsRequest.ListOfGuids value) {
        this.listOfGuids = value;
    }

    /**
     * Gets the value of the listOfJobUids property.
     * 
     * @return
     *     possible object is
     *     {@link GetCcTransactionsRequest.ListOfJobUids }
     *     
     */
    public GetCcTransactionsRequest.ListOfJobUids getListOfJobUids() {
        return listOfJobUids;
    }

    /**
     * Sets the value of the listOfJobUids property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCcTransactionsRequest.ListOfJobUids }
     *     
     */
    public void setListOfJobUids(GetCcTransactionsRequest.ListOfJobUids value) {
        this.listOfJobUids = value;
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
     *         &lt;element name="AccountCSN" type="{http://www.w3.org/2001/XMLSchema}normalizedString" maxOccurs="unbounded" minOccurs="0"/>
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
        "accountCSN"
    })
    public static class ListOfAccountCSN {

        @XmlElement(name = "AccountCSN")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected List<String> accountCSN;

        /**
         * Gets the value of the accountCSN property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the accountCSN property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAccountCSN().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getAccountCSN() {
            if (accountCSN == null) {
                accountCSN = new ArrayList<String>();
            }
            return this.accountCSN;
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
     *         &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" maxOccurs="unbounded" minOccurs="0"/>
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
        "contractNumber"
    })
    public static class ListOfContractNumbers {

        @XmlElement(name = "ContractNumber")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected List<String> contractNumber;

        /**
         * Gets the value of the contractNumber property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the contractNumber property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContractNumber().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getContractNumber() {
            if (contractNumber == null) {
                contractNumber = new ArrayList<String>();
            }
            return this.contractNumber;
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
     *         &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" maxOccurs="unbounded" minOccurs="0"/>
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
        "guid"
    })
    public static class ListOfGuids {

        @XmlElement(name = "GUID")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected List<String> guid;

        /**
         * Gets the value of the guid property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the guid property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGUID().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getGUID() {
            if (guid == null) {
                guid = new ArrayList<String>();
            }
            return this.guid;
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
     *         &lt;element name="JobUid" type="{http://www.w3.org/2001/XMLSchema}normalizedString" maxOccurs="unbounded" minOccurs="0"/>
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
        "jobUid"
    })
    public static class ListOfJobUids {

        @XmlElement(name = "JobUid")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected List<String> jobUid;

        /**
         * Gets the value of the jobUid property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the jobUid property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getJobUid().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getJobUid() {
            if (jobUid == null) {
                jobUid = new ArrayList<String>();
            }
            return this.jobUid;
        }

    }

}
