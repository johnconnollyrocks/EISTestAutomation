
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
 *         &lt;element name="SummaryType" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ListOfContracts" minOccurs="0">
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
    "summaryType",
    "startDate",
    "endDate",
    "listOfContracts",
    "listOfGuids"
})
@XmlRootElement(name = "GetCcUsageSummaryRequest")
public class GetCcUsageSummaryRequest {

    @XmlElement(name = "SummaryType", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String summaryType;
    @XmlElement(name = "StartDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "EndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "ListOfContracts")
    protected GetCcUsageSummaryRequest.ListOfContracts listOfContracts;
    @XmlElement(name = "ListOfGuids")
    protected GetCcUsageSummaryRequest.ListOfGuids listOfGuids;

    /**
     * Gets the value of the summaryType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummaryType() {
        return summaryType;
    }

    /**
     * Sets the value of the summaryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummaryType(String value) {
        this.summaryType = value;
    }

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
     * Gets the value of the listOfContracts property.
     * 
     * @return
     *     possible object is
     *     {@link GetCcUsageSummaryRequest.ListOfContracts }
     *     
     */
    public GetCcUsageSummaryRequest.ListOfContracts getListOfContracts() {
        return listOfContracts;
    }

    /**
     * Sets the value of the listOfContracts property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCcUsageSummaryRequest.ListOfContracts }
     *     
     */
    public void setListOfContracts(GetCcUsageSummaryRequest.ListOfContracts value) {
        this.listOfContracts = value;
    }

    /**
     * Gets the value of the listOfGuids property.
     * 
     * @return
     *     possible object is
     *     {@link GetCcUsageSummaryRequest.ListOfGuids }
     *     
     */
    public GetCcUsageSummaryRequest.ListOfGuids getListOfGuids() {
        return listOfGuids;
    }

    /**
     * Sets the value of the listOfGuids property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCcUsageSummaryRequest.ListOfGuids }
     *     
     */
    public void setListOfGuids(GetCcUsageSummaryRequest.ListOfGuids value) {
        this.listOfGuids = value;
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
    public static class ListOfContracts {

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

}
