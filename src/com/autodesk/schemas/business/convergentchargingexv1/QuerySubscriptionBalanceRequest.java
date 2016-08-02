
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
 *         &lt;element name="ListOfContracts" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Contract" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
    "listOfContracts"
})
@XmlRootElement(name = "QuerySubscriptionBalanceRequest")
public class QuerySubscriptionBalanceRequest {

    @XmlElement(name = "ListOfContracts")
    protected QuerySubscriptionBalanceRequest.ListOfContracts listOfContracts;

    /**
     * Gets the value of the listOfContracts property.
     * 
     * @return
     *     possible object is
     *     {@link QuerySubscriptionBalanceRequest.ListOfContracts }
     *     
     */
    public QuerySubscriptionBalanceRequest.ListOfContracts getListOfContracts() {
        return listOfContracts;
    }

    /**
     * Sets the value of the listOfContracts property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuerySubscriptionBalanceRequest.ListOfContracts }
     *     
     */
    public void setListOfContracts(QuerySubscriptionBalanceRequest.ListOfContracts value) {
        this.listOfContracts = value;
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
     *         &lt;element name="Contract" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
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
        "contract"
    })
    public static class ListOfContracts {

        @XmlElement(name = "Contract")
        protected List<QuerySubscriptionBalanceRequest.ListOfContracts.Contract> contract;

        /**
         * Gets the value of the contract property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the contract property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContract().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link QuerySubscriptionBalanceRequest.ListOfContracts.Contract }
         * 
         * 
         */
        public List<QuerySubscriptionBalanceRequest.ListOfContracts.Contract> getContract() {
            if (contract == null) {
                contract = new ArrayList<QuerySubscriptionBalanceRequest.ListOfContracts.Contract>();
            }
            return this.contract;
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
         *         &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
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
        public static class Contract {

            @XmlElement(name = "ContractNumber")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String contractNumber;

            /**
             * Gets the value of the contractNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getContractNumber() {
                return contractNumber;
            }

            /**
             * Sets the value of the contractNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setContractNumber(String value) {
                this.contractNumber = value;
            }

        }

    }

}
