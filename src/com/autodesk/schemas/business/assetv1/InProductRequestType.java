
package com.autodesk.schemas.business.assetv1;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InProductRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InProductRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Product">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SerialNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="LineCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MajorVersion" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *                   &lt;element name="MinorVersion" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="LanguageCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="User" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="IncludeELearningCourseList" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InProductRequestType", propOrder = {
    "product",
    "user",
    "includeELearningCourseList"
})
public class InProductRequestType {

    @XmlElement(name = "Product", required = true)
    protected InProductRequestType.Product product;
    @XmlElement(name = "User")
    protected InProductRequestType.User user;
    @XmlElement(name = "IncludeELearningCourseList", defaultValue = "false")
    protected Boolean includeELearningCourseList;

    /**
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link InProductRequestType.Product }
     *     
     */
    public InProductRequestType.Product getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link InProductRequestType.Product }
     *     
     */
    public void setProduct(InProductRequestType.Product value) {
        this.product = value;
    }

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link InProductRequestType.User }
     *     
     */
    public InProductRequestType.User getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link InProductRequestType.User }
     *     
     */
    public void setUser(InProductRequestType.User value) {
        this.user = value;
    }

    /**
     * Gets the value of the includeELearningCourseList property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeELearningCourseList() {
        return includeELearningCourseList;
    }

    /**
     * Sets the value of the includeELearningCourseList property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeELearningCourseList(Boolean value) {
        this.includeELearningCourseList = value;
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
     *         &lt;element name="SerialNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="LineCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="MajorVersion" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
     *         &lt;element name="MinorVersion" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *         &lt;element name="LanguageCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "serialNumber",
        "lineCode",
        "majorVersion",
        "minorVersion",
        "languageCode"
    })
    public static class Product {

        @XmlElement(name = "SerialNumber", required = true)
        protected String serialNumber;
        @XmlElement(name = "LineCode", required = true)
        protected String lineCode;
        @XmlElement(name = "MajorVersion")
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger majorVersion;
        @XmlElement(name = "MinorVersion")
        protected BigInteger minorVersion;
        @XmlElement(name = "LanguageCode")
        protected String languageCode;

        /**
         * Gets the value of the serialNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSerialNumber() {
            return serialNumber;
        }

        /**
         * Sets the value of the serialNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSerialNumber(String value) {
            this.serialNumber = value;
        }

        /**
         * Gets the value of the lineCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLineCode() {
            return lineCode;
        }

        /**
         * Sets the value of the lineCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLineCode(String value) {
            this.lineCode = value;
        }

        /**
         * Gets the value of the majorVersion property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMajorVersion() {
            return majorVersion;
        }

        /**
         * Sets the value of the majorVersion property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMajorVersion(BigInteger value) {
            this.majorVersion = value;
        }

        /**
         * Gets the value of the minorVersion property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMinorVersion() {
            return minorVersion;
        }

        /**
         * Sets the value of the minorVersion property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMinorVersion(BigInteger value) {
            this.minorVersion = value;
        }

        /**
         * Gets the value of the languageCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLanguageCode() {
            return languageCode;
        }

        /**
         * Sets the value of the languageCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLanguageCode(String value) {
            this.languageCode = value;
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
     *         &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    public static class User {

        @XmlElement(name = "GUID")
        protected String guid;

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

    }

}
