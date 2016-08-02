
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
 *                             &lt;element name="ContractType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="ListOfServicePrivileges" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ServicePrivilege" type="{http://www.autodesk.com/schemas/Business/ConvergentChargingExV1.0}ServicePrivilege" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
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
@XmlRootElement(name = "QuerySubscriptionBalanceResponse")
public class QuerySubscriptionBalanceResponse {

    @XmlElement(name = "ListOfContracts")
    protected QuerySubscriptionBalanceResponse.ListOfContracts listOfContracts;

    /**
     * Gets the value of the listOfContracts property.
     * 
     * @return
     *     possible object is
     *     {@link QuerySubscriptionBalanceResponse.ListOfContracts }
     *     
     */
    public QuerySubscriptionBalanceResponse.ListOfContracts getListOfContracts() {
        return listOfContracts;
    }

    /**
     * Sets the value of the listOfContracts property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuerySubscriptionBalanceResponse.ListOfContracts }
     *     
     */
    public void setListOfContracts(QuerySubscriptionBalanceResponse.ListOfContracts value) {
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
     *                   &lt;element name="ContractType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="ListOfServicePrivileges" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ServicePrivilege" type="{http://www.autodesk.com/schemas/Business/ConvergentChargingExV1.0}ServicePrivilege" maxOccurs="unbounded" minOccurs="0"/>
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
        "contract"
    })
    public static class ListOfContracts {

        @XmlElement(name = "Contract")
        protected List<QuerySubscriptionBalanceResponse.ListOfContracts.Contract> contract;

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
         * {@link QuerySubscriptionBalanceResponse.ListOfContracts.Contract }
         * 
         * 
         */
        public List<QuerySubscriptionBalanceResponse.ListOfContracts.Contract> getContract() {
            if (contract == null) {
                contract = new ArrayList<QuerySubscriptionBalanceResponse.ListOfContracts.Contract>();
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
         *         &lt;element name="ContractType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="ListOfServicePrivileges" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="ServicePrivilege" type="{http://www.autodesk.com/schemas/Business/ConvergentChargingExV1.0}ServicePrivilege" maxOccurs="unbounded" minOccurs="0"/>
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
            "contractNumber",
            "contractType",
            "status",
            "listOfServicePrivileges"
        })
        public static class Contract {

            @XmlElement(name = "ContractNumber")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String contractNumber;
            @XmlElement(name = "ContractType")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String contractType;
            @XmlElement(name = "Status")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String status;
            @XmlElement(name = "ListOfServicePrivileges")
            protected QuerySubscriptionBalanceResponse.ListOfContracts.Contract.ListOfServicePrivileges listOfServicePrivileges;

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

            /**
             * Gets the value of the contractType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getContractType() {
                return contractType;
            }

            /**
             * Sets the value of the contractType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setContractType(String value) {
                this.contractType = value;
            }

            /**
             * Gets the value of the status property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStatus() {
                return status;
            }

            /**
             * Sets the value of the status property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStatus(String value) {
                this.status = value;
            }

            /**
             * Gets the value of the listOfServicePrivileges property.
             * 
             * @return
             *     possible object is
             *     {@link QuerySubscriptionBalanceResponse.ListOfContracts.Contract.ListOfServicePrivileges }
             *     
             */
            public QuerySubscriptionBalanceResponse.ListOfContracts.Contract.ListOfServicePrivileges getListOfServicePrivileges() {
                return listOfServicePrivileges;
            }

            /**
             * Sets the value of the listOfServicePrivileges property.
             * 
             * @param value
             *     allowed object is
             *     {@link QuerySubscriptionBalanceResponse.ListOfContracts.Contract.ListOfServicePrivileges }
             *     
             */
            public void setListOfServicePrivileges(QuerySubscriptionBalanceResponse.ListOfContracts.Contract.ListOfServicePrivileges value) {
                this.listOfServicePrivileges = value;
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
             *         &lt;element name="ServicePrivilege" type="{http://www.autodesk.com/schemas/Business/ConvergentChargingExV1.0}ServicePrivilege" maxOccurs="unbounded" minOccurs="0"/>
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
                "servicePrivilege"
            })
            public static class ListOfServicePrivileges {

                @XmlElement(name = "ServicePrivilege")
                protected List<ServicePrivilege> servicePrivilege;

                /**
                 * Gets the value of the servicePrivilege property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the servicePrivilege property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getServicePrivilege().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link ServicePrivilege }
                 * 
                 * 
                 */
                public List<ServicePrivilege> getServicePrivilege() {
                    if (servicePrivilege == null) {
                        servicePrivilege = new ArrayList<ServicePrivilege>();
                    }
                    return this.servicePrivilege;
                }

            }

        }

    }

}
