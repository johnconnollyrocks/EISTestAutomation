
package com.autodesk.schemas.business.contractv1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.autodesk.schemas.business.partyv2.AccountBaseDataType;
import com.autodesk.schemas.business.partyv2.ContactBaseDataType;


/**
 * <p>Java class for ContractCoreDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractCoreDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractBaseDataType">
 *       &lt;sequence>
 *         &lt;element name="EndUserContact" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactBaseDataType" minOccurs="0"/>
 *         &lt;element name="EndUserParty" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountBaseDataType" minOccurs="0"/>
 *         &lt;element name="NumberOfAdditionalNamedCallers" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="ListOfContractItem" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ContractItem" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractItemType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ListOfPermission" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Permission" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>normalizedString">
 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="RefDocument" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ListOfRefDocumentType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractCoreDataType", propOrder = {
    "endUserContact",
    "endUserParty",
    "numberOfAdditionalNamedCallers",
    "listOfContractItem",
    "listOfPermission",
    "refDocument"
})
@XmlSeeAlso({
    ContractType.class
})
public class ContractCoreDataType
    extends ContractBaseDataType
{

    @XmlElement(name = "EndUserContact")
    protected ContactBaseDataType endUserContact;
    @XmlElement(name = "EndUserParty")
    protected AccountBaseDataType endUserParty;
    @XmlElement(name = "NumberOfAdditionalNamedCallers")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger numberOfAdditionalNamedCallers;
    @XmlElement(name = "ListOfContractItem")
    protected ContractCoreDataType.ListOfContractItem listOfContractItem;
    @XmlElement(name = "ListOfPermission")
    protected ContractCoreDataType.ListOfPermission listOfPermission;
    @XmlElement(name = "RefDocument")
    protected ListOfRefDocumentType refDocument;

    /**
     * Gets the value of the endUserContact property.
     * 
     * @return
     *     possible object is
     *     {@link ContactBaseDataType }
     *     
     */
    public ContactBaseDataType getEndUserContact() {
        return endUserContact;
    }

    /**
     * Sets the value of the endUserContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactBaseDataType }
     *     
     */
    public void setEndUserContact(ContactBaseDataType value) {
        this.endUserContact = value;
    }

    /**
     * Gets the value of the endUserParty property.
     * 
     * @return
     *     possible object is
     *     {@link AccountBaseDataType }
     *     
     */
    public AccountBaseDataType getEndUserParty() {
        return endUserParty;
    }

    /**
     * Sets the value of the endUserParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountBaseDataType }
     *     
     */
    public void setEndUserParty(AccountBaseDataType value) {
        this.endUserParty = value;
    }

    /**
     * Gets the value of the numberOfAdditionalNamedCallers property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfAdditionalNamedCallers() {
        return numberOfAdditionalNamedCallers;
    }

    /**
     * Sets the value of the numberOfAdditionalNamedCallers property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfAdditionalNamedCallers(BigInteger value) {
        this.numberOfAdditionalNamedCallers = value;
    }

    /**
     * Gets the value of the listOfContractItem property.
     * 
     * @return
     *     possible object is
     *     {@link ContractCoreDataType.ListOfContractItem }
     *     
     */
    public ContractCoreDataType.ListOfContractItem getListOfContractItem() {
        return listOfContractItem;
    }

    /**
     * Sets the value of the listOfContractItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractCoreDataType.ListOfContractItem }
     *     
     */
    public void setListOfContractItem(ContractCoreDataType.ListOfContractItem value) {
        this.listOfContractItem = value;
    }

    /**
     * Gets the value of the listOfPermission property.
     * 
     * @return
     *     possible object is
     *     {@link ContractCoreDataType.ListOfPermission }
     *     
     */
    public ContractCoreDataType.ListOfPermission getListOfPermission() {
        return listOfPermission;
    }

    /**
     * Sets the value of the listOfPermission property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractCoreDataType.ListOfPermission }
     *     
     */
    public void setListOfPermission(ContractCoreDataType.ListOfPermission value) {
        this.listOfPermission = value;
    }

    /**
     * Gets the value of the refDocument property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfRefDocumentType }
     *     
     */
    public ListOfRefDocumentType getRefDocument() {
        return refDocument;
    }

    /**
     * Sets the value of the refDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfRefDocumentType }
     *     
     */
    public void setRefDocument(ListOfRefDocumentType value) {
        this.refDocument = value;
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
     *         &lt;element name="ContractItem" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractItemType" maxOccurs="unbounded" minOccurs="0"/>
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
        "contractItem"
    })
    public static class ListOfContractItem {

        @XmlElement(name = "ContractItem")
        protected List<ContractItemType> contractItem;

        /**
         * Gets the value of the contractItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the contractItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContractItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ContractItemType }
         * 
         * 
         */
        public List<ContractItemType> getContractItem() {
            if (contractItem == null) {
                contractItem = new ArrayList<ContractItemType>();
            }
            return this.contractItem;
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
     *         &lt;element name="Permission" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>normalizedString">
     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
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
        "permission"
    })
    public static class ListOfPermission {

        @XmlElement(name = "Permission")
        protected List<ContractCoreDataType.ListOfPermission.Permission> permission;

        /**
         * Gets the value of the permission property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the permission property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPermission().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ContractCoreDataType.ListOfPermission.Permission }
         * 
         * 
         */
        public List<ContractCoreDataType.ListOfPermission.Permission> getPermission() {
            if (permission == null) {
                permission = new ArrayList<ContractCoreDataType.ListOfPermission.Permission>();
            }
            return this.permission;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>normalizedString">
         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Permission {

            @XmlValue
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String value;
            @XmlAttribute(name = "type")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String type;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the type property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setType(String value) {
                this.type = value;
            }

        }

    }

}
