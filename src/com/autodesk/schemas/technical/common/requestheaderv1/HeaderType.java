
package com.autodesk.schemas.technical.common.requestheaderv1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for HeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MessageIdentifier">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MessageName" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="1"/>
 *                         &lt;maxLength value="40"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="MessageVersion">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Profile" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="EffectiveUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="InitiatingUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="InitiatingUserRole" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Transaction" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TransactionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="TransactionDomain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="RequestingSystem" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CorrelationID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RequestingApplicationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RequestingServer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RequestingProcessId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="responseLanguage" type="{http://www.w3.org/2001/XMLSchema}language" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Properties" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CachedDataAccess" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "HeaderType", propOrder = {
    "messageIdentifier",
    "profile",
    "transaction",
    "requestingSystem",
    "properties"
})
public class HeaderType {

    @XmlElement(name = "MessageIdentifier", required = true)
    protected HeaderType.MessageIdentifier messageIdentifier;
    @XmlElement(name = "Profile")
    protected HeaderType.Profile profile;
    @XmlElement(name = "Transaction")
    protected List<HeaderType.Transaction> transaction;
    @XmlElement(name = "RequestingSystem")
    protected List<HeaderType.RequestingSystem> requestingSystem;
    @XmlElement(name = "Properties")
    protected HeaderType.Properties properties;

    /**
     * Gets the value of the messageIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderType.MessageIdentifier }
     *     
     */
    public HeaderType.MessageIdentifier getMessageIdentifier() {
        return messageIdentifier;
    }

    /**
     * Sets the value of the messageIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderType.MessageIdentifier }
     *     
     */
    public void setMessageIdentifier(HeaderType.MessageIdentifier value) {
        this.messageIdentifier = value;
    }

    /**
     * Gets the value of the profile property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderType.Profile }
     *     
     */
    public HeaderType.Profile getProfile() {
        return profile;
    }

    /**
     * Sets the value of the profile property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderType.Profile }
     *     
     */
    public void setProfile(HeaderType.Profile value) {
        this.profile = value;
    }

    /**
     * Gets the value of the transaction property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transaction property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransaction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HeaderType.Transaction }
     * 
     * 
     */
    public List<HeaderType.Transaction> getTransaction() {
        if (transaction == null) {
            transaction = new ArrayList<HeaderType.Transaction>();
        }
        return this.transaction;
    }

    /**
     * Gets the value of the requestingSystem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestingSystem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestingSystem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HeaderType.RequestingSystem }
     * 
     * 
     */
    public List<HeaderType.RequestingSystem> getRequestingSystem() {
        if (requestingSystem == null) {
            requestingSystem = new ArrayList<HeaderType.RequestingSystem>();
        }
        return this.requestingSystem;
    }

    /**
     * Gets the value of the properties property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderType.Properties }
     *     
     */
    public HeaderType.Properties getProperties() {
        return properties;
    }

    /**
     * Sets the value of the properties property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderType.Properties }
     *     
     */
    public void setProperties(HeaderType.Properties value) {
        this.properties = value;
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
     *         &lt;element name="MessageName" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="1"/>
     *               &lt;maxLength value="40"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="MessageVersion">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *             &lt;/restriction>
     *           &lt;/simpleType>
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
        "messageName",
        "messageVersion"
    })
    public static class MessageIdentifier {

        @XmlElement(name = "MessageName")
        protected String messageName;
        @XmlElement(name = "MessageVersion")
        protected int messageVersion;

        /**
         * Gets the value of the messageName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMessageName() {
            return messageName;
        }

        /**
         * Sets the value of the messageName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMessageName(String value) {
            this.messageName = value;
        }

        /**
         * Gets the value of the messageVersion property.
         * 
         */
        public int getMessageVersion() {
            return messageVersion;
        }

        /**
         * Sets the value of the messageVersion property.
         * 
         */
        public void setMessageVersion(int value) {
            this.messageVersion = value;
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
     *         &lt;element name="AccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="EffectiveUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="InitiatingUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="InitiatingUserRole" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "accountNumber",
        "effectiveUserId",
        "initiatingUserId",
        "initiatingUserRole"
    })
    public static class Profile {

        @XmlElement(name = "AccountNumber")
        protected String accountNumber;
        @XmlElement(name = "EffectiveUserId")
        protected String effectiveUserId;
        @XmlElement(name = "InitiatingUserId")
        protected String initiatingUserId;
        @XmlElement(name = "InitiatingUserRole")
        protected String initiatingUserRole;

        /**
         * Gets the value of the accountNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAccountNumber() {
            return accountNumber;
        }

        /**
         * Sets the value of the accountNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAccountNumber(String value) {
            this.accountNumber = value;
        }

        /**
         * Gets the value of the effectiveUserId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEffectiveUserId() {
            return effectiveUserId;
        }

        /**
         * Sets the value of the effectiveUserId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEffectiveUserId(String value) {
            this.effectiveUserId = value;
        }

        /**
         * Gets the value of the initiatingUserId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInitiatingUserId() {
            return initiatingUserId;
        }

        /**
         * Sets the value of the initiatingUserId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInitiatingUserId(String value) {
            this.initiatingUserId = value;
        }

        /**
         * Gets the value of the initiatingUserRole property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInitiatingUserRole() {
            return initiatingUserRole;
        }

        /**
         * Sets the value of the initiatingUserRole property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInitiatingUserRole(String value) {
            this.initiatingUserRole = value;
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
     *         &lt;element name="CachedDataAccess" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
        "cachedDataAccess"
    })
    public static class Properties {

        @XmlElement(name = "CachedDataAccess", defaultValue = "true")
        protected boolean cachedDataAccess;

        /**
         * Gets the value of the cachedDataAccess property.
         * 
         */
        public boolean isCachedDataAccess() {
            return cachedDataAccess;
        }

        /**
         * Sets the value of the cachedDataAccess property.
         * 
         */
        public void setCachedDataAccess(boolean value) {
            this.cachedDataAccess = value;
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
     *         &lt;element name="CorrelationID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RequestingApplicationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RequestingServer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RequestingProcessId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="responseLanguage" type="{http://www.w3.org/2001/XMLSchema}language" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "correlationID",
        "requestingApplicationName",
        "requestingServer",
        "requestingProcessId"
    })
    public static class RequestingSystem {

        @XmlElement(name = "CorrelationID")
        protected String correlationID;
        @XmlElement(name = "RequestingApplicationName")
        protected String requestingApplicationName;
        @XmlElement(name = "RequestingServer")
        protected String requestingServer;
        @XmlElement(name = "RequestingProcessId")
        protected String requestingProcessId;
        @XmlAttribute(name = "responseLanguage")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "language")
        protected String responseLanguage;

        /**
         * Gets the value of the correlationID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCorrelationID() {
            return correlationID;
        }

        /**
         * Sets the value of the correlationID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCorrelationID(String value) {
            this.correlationID = value;
        }

        /**
         * Gets the value of the requestingApplicationName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRequestingApplicationName() {
            return requestingApplicationName;
        }

        /**
         * Sets the value of the requestingApplicationName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRequestingApplicationName(String value) {
            this.requestingApplicationName = value;
        }

        /**
         * Gets the value of the requestingServer property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRequestingServer() {
            return requestingServer;
        }

        /**
         * Sets the value of the requestingServer property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRequestingServer(String value) {
            this.requestingServer = value;
        }

        /**
         * Gets the value of the requestingProcessId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRequestingProcessId() {
            return requestingProcessId;
        }

        /**
         * Sets the value of the requestingProcessId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRequestingProcessId(String value) {
            this.requestingProcessId = value;
        }

        /**
         * Gets the value of the responseLanguage property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getResponseLanguage() {
            return responseLanguage;
        }

        /**
         * Sets the value of the responseLanguage property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setResponseLanguage(String value) {
            this.responseLanguage = value;
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
     *         &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TransactionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="TransactionDomain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "transactionID",
        "transactionType",
        "transactionDomain"
    })
    public static class Transaction {

        @XmlElement(name = "TransactionID")
        protected String transactionID;
        @XmlElement(name = "TransactionType")
        protected String transactionType;
        @XmlElement(name = "TransactionDomain")
        protected String transactionDomain;

        /**
         * Gets the value of the transactionID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTransactionID() {
            return transactionID;
        }

        /**
         * Sets the value of the transactionID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTransactionID(String value) {
            this.transactionID = value;
        }

        /**
         * Gets the value of the transactionType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTransactionType() {
            return transactionType;
        }

        /**
         * Sets the value of the transactionType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTransactionType(String value) {
            this.transactionType = value;
        }

        /**
         * Gets the value of the transactionDomain property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTransactionDomain() {
            return transactionDomain;
        }

        /**
         * Sets the value of the transactionDomain property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTransactionDomain(String value) {
            this.transactionDomain = value;
        }

    }

}
