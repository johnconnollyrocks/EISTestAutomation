
package com.autodesk.schemas.business.convergentchargingexv1;

import java.math.BigInteger;
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
 *         &lt;element name="ListOfCcTransactions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CcTransaction" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ChargedItemId" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="TxnType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="EmailID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="AccountCSN" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="OfferType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="ServiceCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="ServiceCategory" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="ProductLineCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="ProductLineDescription" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="UsageType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="UserLogon" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="SubscriptionLevel" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="OrderNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="UnitType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *                             &lt;element name="TxnUnits" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                             &lt;element name="UnitsBalance" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                             &lt;element name="UnitsOverage" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                             &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                             &lt;element name="AnnualValueBalance" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                             &lt;element name="TxnTimeStamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                             &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                             &lt;element name="EndTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
    "listOfCcTransactions"
})
@XmlRootElement(name = "GetCcTransactionsResponse")
public class GetCcTransactionsResponse {

    @XmlElement(name = "ListOfCcTransactions")
    protected GetCcTransactionsResponse.ListOfCcTransactions listOfCcTransactions;

    /**
     * Gets the value of the listOfCcTransactions property.
     * 
     * @return
     *     possible object is
     *     {@link GetCcTransactionsResponse.ListOfCcTransactions }
     *     
     */
    public GetCcTransactionsResponse.ListOfCcTransactions getListOfCcTransactions() {
        return listOfCcTransactions;
    }

    /**
     * Sets the value of the listOfCcTransactions property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCcTransactionsResponse.ListOfCcTransactions }
     *     
     */
    public void setListOfCcTransactions(GetCcTransactionsResponse.ListOfCcTransactions value) {
        this.listOfCcTransactions = value;
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
     *         &lt;element name="CcTransaction" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ChargedItemId" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="TxnType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="EmailID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="AccountCSN" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="OfferType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="ServiceCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="ServiceCategory" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="ProductLineCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="ProductLineDescription" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="UsageType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="UserLogon" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="SubscriptionLevel" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="OrderNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="UnitType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
     *                   &lt;element name="TxnUnits" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                   &lt;element name="UnitsBalance" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                   &lt;element name="UnitsOverage" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                   &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                   &lt;element name="AnnualValueBalance" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                   &lt;element name="TxnTimeStamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *                   &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *                   &lt;element name="EndTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
        "ccTransaction"
    })
    public static class ListOfCcTransactions {

        @XmlElement(name = "CcTransaction")
        protected List<GetCcTransactionsResponse.ListOfCcTransactions.CcTransaction> ccTransaction;

        /**
         * Gets the value of the ccTransaction property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ccTransaction property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCcTransaction().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link GetCcTransactionsResponse.ListOfCcTransactions.CcTransaction }
         * 
         * 
         */
        public List<GetCcTransactionsResponse.ListOfCcTransactions.CcTransaction> getCcTransaction() {
            if (ccTransaction == null) {
                ccTransaction = new ArrayList<GetCcTransactionsResponse.ListOfCcTransactions.CcTransaction>();
            }
            return this.ccTransaction;
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
         *         &lt;element name="ChargedItemId" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="TxnType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="EmailID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="AccountCSN" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="OfferType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="ServiceCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="ServiceCategory" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="ProductLineCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="ProductLineDescription" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="UsageType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="UserLogon" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="SubscriptionLevel" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="OrderNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="UnitType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
         *         &lt;element name="TxnUnits" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
         *         &lt;element name="UnitsBalance" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
         *         &lt;element name="UnitsOverage" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
         *         &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
         *         &lt;element name="AnnualValueBalance" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
         *         &lt;element name="TxnTimeStamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
         *         &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
         *         &lt;element name="EndTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
            "chargedItemId",
            "txnType",
            "guid",
            "emailID",
            "accountCSN",
            "contractNumber",
            "offerType",
            "serviceCode",
            "serviceCategory",
            "productLineCode",
            "productLineDescription",
            "usageType",
            "userLogon",
            "status",
            "subscriptionLevel",
            "orderNumber",
            "unitType",
            "comment",
            "quantity",
            "txnUnits",
            "unitsBalance",
            "unitsOverage",
            "amount",
            "annualValueBalance",
            "txnTimeStamp",
            "startTime",
            "endTime"
        })
        public static class CcTransaction {

            @XmlElement(name = "ChargedItemId")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String chargedItemId;
            @XmlElement(name = "TxnType")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String txnType;
            @XmlElement(name = "GUID")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String guid;
            @XmlElement(name = "EmailID")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String emailID;
            @XmlElement(name = "AccountCSN")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String accountCSN;
            @XmlElement(name = "ContractNumber")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String contractNumber;
            @XmlElement(name = "OfferType")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String offerType;
            @XmlElement(name = "ServiceCode")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String serviceCode;
            @XmlElement(name = "ServiceCategory")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String serviceCategory;
            @XmlElement(name = "ProductLineCode")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String productLineCode;
            @XmlElement(name = "ProductLineDescription")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String productLineDescription;
            @XmlElement(name = "UsageType")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String usageType;
            @XmlElement(name = "UserLogon")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String userLogon;
            @XmlElement(name = "Status")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String status;
            @XmlElement(name = "SubscriptionLevel")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String subscriptionLevel;
            @XmlElement(name = "OrderNumber")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String orderNumber;
            @XmlElement(name = "UnitType")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String unitType;
            @XmlElement(name = "Comment")
            protected String comment;
            @XmlElement(name = "Quantity")
            protected Double quantity;
            @XmlElement(name = "TxnUnits")
            protected BigInteger txnUnits;
            @XmlElement(name = "UnitsBalance")
            protected BigInteger unitsBalance;
            @XmlElement(name = "UnitsOverage")
            protected BigInteger unitsOverage;
            @XmlElement(name = "Amount")
            protected BigInteger amount;
            @XmlElement(name = "AnnualValueBalance")
            protected BigInteger annualValueBalance;
            @XmlElement(name = "TxnTimeStamp")
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar txnTimeStamp;
            @XmlElement(name = "StartTime")
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar startTime;
            @XmlElement(name = "EndTime")
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar endTime;

            /**
             * Gets the value of the chargedItemId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getChargedItemId() {
                return chargedItemId;
            }

            /**
             * Sets the value of the chargedItemId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setChargedItemId(String value) {
                this.chargedItemId = value;
            }

            /**
             * Gets the value of the txnType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTxnType() {
                return txnType;
            }

            /**
             * Sets the value of the txnType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTxnType(String value) {
                this.txnType = value;
            }

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
             * Gets the value of the emailID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEmailID() {
                return emailID;
            }

            /**
             * Sets the value of the emailID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEmailID(String value) {
                this.emailID = value;
            }

            /**
             * Gets the value of the accountCSN property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAccountCSN() {
                return accountCSN;
            }

            /**
             * Sets the value of the accountCSN property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAccountCSN(String value) {
                this.accountCSN = value;
            }

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
             * Gets the value of the offerType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOfferType() {
                return offerType;
            }

            /**
             * Sets the value of the offerType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOfferType(String value) {
                this.offerType = value;
            }

            /**
             * Gets the value of the serviceCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getServiceCode() {
                return serviceCode;
            }

            /**
             * Sets the value of the serviceCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setServiceCode(String value) {
                this.serviceCode = value;
            }

            /**
             * Gets the value of the serviceCategory property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getServiceCategory() {
                return serviceCategory;
            }

            /**
             * Sets the value of the serviceCategory property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setServiceCategory(String value) {
                this.serviceCategory = value;
            }

            /**
             * Gets the value of the productLineCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getProductLineCode() {
                return productLineCode;
            }

            /**
             * Sets the value of the productLineCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setProductLineCode(String value) {
                this.productLineCode = value;
            }

            /**
             * Gets the value of the productLineDescription property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getProductLineDescription() {
                return productLineDescription;
            }

            /**
             * Sets the value of the productLineDescription property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setProductLineDescription(String value) {
                this.productLineDescription = value;
            }

            /**
             * Gets the value of the usageType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUsageType() {
                return usageType;
            }

            /**
             * Sets the value of the usageType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUsageType(String value) {
                this.usageType = value;
            }

            /**
             * Gets the value of the userLogon property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUserLogon() {
                return userLogon;
            }

            /**
             * Sets the value of the userLogon property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUserLogon(String value) {
                this.userLogon = value;
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
             * Gets the value of the subscriptionLevel property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSubscriptionLevel() {
                return subscriptionLevel;
            }

            /**
             * Sets the value of the subscriptionLevel property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSubscriptionLevel(String value) {
                this.subscriptionLevel = value;
            }

            /**
             * Gets the value of the orderNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOrderNumber() {
                return orderNumber;
            }

            /**
             * Sets the value of the orderNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOrderNumber(String value) {
                this.orderNumber = value;
            }

            /**
             * Gets the value of the unitType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUnitType() {
                return unitType;
            }

            /**
             * Sets the value of the unitType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUnitType(String value) {
                this.unitType = value;
            }

            /**
             * Gets the value of the comment property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getComment() {
                return comment;
            }

            /**
             * Sets the value of the comment property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setComment(String value) {
                this.comment = value;
            }

            /**
             * Gets the value of the quantity property.
             * 
             * @return
             *     possible object is
             *     {@link Double }
             *     
             */
            public Double getQuantity() {
                return quantity;
            }

            /**
             * Sets the value of the quantity property.
             * 
             * @param value
             *     allowed object is
             *     {@link Double }
             *     
             */
            public void setQuantity(Double value) {
                this.quantity = value;
            }

            /**
             * Gets the value of the txnUnits property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getTxnUnits() {
                return txnUnits;
            }

            /**
             * Sets the value of the txnUnits property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setTxnUnits(BigInteger value) {
                this.txnUnits = value;
            }

            /**
             * Gets the value of the unitsBalance property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getUnitsBalance() {
                return unitsBalance;
            }

            /**
             * Sets the value of the unitsBalance property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setUnitsBalance(BigInteger value) {
                this.unitsBalance = value;
            }

            /**
             * Gets the value of the unitsOverage property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getUnitsOverage() {
                return unitsOverage;
            }

            /**
             * Sets the value of the unitsOverage property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setUnitsOverage(BigInteger value) {
                this.unitsOverage = value;
            }

            /**
             * Gets the value of the amount property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getAmount() {
                return amount;
            }

            /**
             * Sets the value of the amount property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setAmount(BigInteger value) {
                this.amount = value;
            }

            /**
             * Gets the value of the annualValueBalance property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getAnnualValueBalance() {
                return annualValueBalance;
            }

            /**
             * Sets the value of the annualValueBalance property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setAnnualValueBalance(BigInteger value) {
                this.annualValueBalance = value;
            }

            /**
             * Gets the value of the txnTimeStamp property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getTxnTimeStamp() {
                return txnTimeStamp;
            }

            /**
             * Sets the value of the txnTimeStamp property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setTxnTimeStamp(XMLGregorianCalendar value) {
                this.txnTimeStamp = value;
            }

            /**
             * Gets the value of the startTime property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getStartTime() {
                return startTime;
            }

            /**
             * Sets the value of the startTime property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setStartTime(XMLGregorianCalendar value) {
                this.startTime = value;
            }

            /**
             * Gets the value of the endTime property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getEndTime() {
                return endTime;
            }

            /**
             * Sets the value of the endTime property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setEndTime(XMLGregorianCalendar value) {
                this.endTime = value;
            }

        }

    }

}
