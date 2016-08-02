
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
 *         &lt;element name="ListOfCcTransactionSummary" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CcSummaryTransaction" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="SummaryType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="TxnType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="TxnTimeStamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                             &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="AccountCSN" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="OfferType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="ServiceCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="ServiceCategory" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="ProductLineCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="UsageType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="UserLogon" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="SubscriptionLevel" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="UnitType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;element name="UnitValue" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
    "listOfCcTransactionSummary"
})
@XmlRootElement(name = "GetCcTransactionSummaryResponse")
public class GetCcTransactionSummaryResponse {

    @XmlElement(name = "ListOfCcTransactionSummary")
    protected GetCcTransactionSummaryResponse.ListOfCcTransactionSummary listOfCcTransactionSummary;

    /**
     * Gets the value of the listOfCcTransactionSummary property.
     * 
     * @return
     *     possible object is
     *     {@link GetCcTransactionSummaryResponse.ListOfCcTransactionSummary }
     *     
     */
    public GetCcTransactionSummaryResponse.ListOfCcTransactionSummary getListOfCcTransactionSummary() {
        return listOfCcTransactionSummary;
    }

    /**
     * Sets the value of the listOfCcTransactionSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCcTransactionSummaryResponse.ListOfCcTransactionSummary }
     *     
     */
    public void setListOfCcTransactionSummary(GetCcTransactionSummaryResponse.ListOfCcTransactionSummary value) {
        this.listOfCcTransactionSummary = value;
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
     *         &lt;element name="CcSummaryTransaction" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="SummaryType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="TxnType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="TxnTimeStamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *                   &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="AccountCSN" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="OfferType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="ServiceCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="ServiceCategory" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="ProductLineCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="UsageType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="UserLogon" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="SubscriptionLevel" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="UnitType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
     *                   &lt;element name="UnitValue" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
        "ccSummaryTransaction"
    })
    public static class ListOfCcTransactionSummary {

        @XmlElement(name = "CcSummaryTransaction")
        protected List<GetCcTransactionSummaryResponse.ListOfCcTransactionSummary.CcSummaryTransaction> ccSummaryTransaction;

        /**
         * Gets the value of the ccSummaryTransaction property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ccSummaryTransaction property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCcSummaryTransaction().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link GetCcTransactionSummaryResponse.ListOfCcTransactionSummary.CcSummaryTransaction }
         * 
         * 
         */
        public List<GetCcTransactionSummaryResponse.ListOfCcTransactionSummary.CcSummaryTransaction> getCcSummaryTransaction() {
            if (ccSummaryTransaction == null) {
                ccSummaryTransaction = new ArrayList<GetCcTransactionSummaryResponse.ListOfCcTransactionSummary.CcSummaryTransaction>();
            }
            return this.ccSummaryTransaction;
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
         *         &lt;element name="SummaryType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="TxnType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="TxnTimeStamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
         *         &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="AccountCSN" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="OfferType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="ServiceCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="ServiceCategory" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="ProductLineCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="UsageType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="UserLogon" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="SubscriptionLevel" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="UnitType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
         *         &lt;element name="UnitValue" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
            "txnType",
            "txnTimeStamp",
            "guid",
            "accountCSN",
            "contractNumber",
            "offerType",
            "serviceCode",
            "serviceCategory",
            "productLineCode",
            "usageType",
            "userLogon",
            "subscriptionLevel",
            "unitType",
            "unitValue"
        })
        public static class CcSummaryTransaction {

            @XmlElement(name = "SummaryType")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String summaryType;
            @XmlElement(name = "TxnType")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String txnType;
            @XmlElement(name = "TxnTimeStamp")
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar txnTimeStamp;
            @XmlElement(name = "GUID")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String guid;
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
            @XmlElement(name = "UsageType")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String usageType;
            @XmlElement(name = "UserLogon")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String userLogon;
            @XmlElement(name = "SubscriptionLevel")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String subscriptionLevel;
            @XmlElement(name = "UnitType")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String unitType;
            @XmlElement(name = "UnitValue")
            protected BigInteger unitValue;

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
             * Gets the value of the unitValue property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getUnitValue() {
                return unitValue;
            }

            /**
             * Sets the value of the unitValue property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setUnitValue(BigInteger value) {
                this.unitValue = value;
            }

        }

    }

}
