
package com.autodesk.schemas.business.partyv2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.autodesk.schemas.business.assetv1.AssetRefDataType;
import com.autodesk.schemas.business.assetv1.ListOfAssetType;
import com.autodesk.schemas.business.contractv1.ContractLiteType;
import com.autodesk.schemas.business.contractv1.ContractRefDataType;
import com.autodesk.schemas.business.contractv1.EntitlementRefDataType;


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
 *         &lt;element name="Contact" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactExtDataType" minOccurs="0"/>
 *         &lt;element name="ListOfContract" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Contract" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractLiteType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ListOfContractRole" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ContractRole" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://www.autodesk.com/schemas/Business/PartyV2.0}RoleType">
 *                           &lt;choice>
 *                             &lt;element name="ListOfAssociatedAsset" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="AssociatedAsset" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}AssetRefDataType" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="ListOfAssociatedContract" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="AssociatedContract" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractRefDataType" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="ListOfAssociatedEntitlement" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="AssociatedEntitlement" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}EntitlementRefDataType" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/choice>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ListOfAsset" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}ListOfAssetType" minOccurs="0"/>
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
    "contact",
    "listOfContract",
    "listOfContractRole",
    "listOfAsset"
})
@XmlRootElement(name = "ContactEntitlements")
public class ContactEntitlements {

    @XmlElement(name = "Contact")
    protected ContactExtDataType contact;
    @XmlElementRef(name = "ListOfContract", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<ContactEntitlements.ListOfContract> listOfContract;
    @XmlElement(name = "ListOfContractRole")
    protected ContactEntitlements.ListOfContractRole listOfContractRole;
    @XmlElement(name = "ListOfAsset")
    protected ListOfAssetType listOfAsset;

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link ContactExtDataType }
     *     
     */
    public ContactExtDataType getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactExtDataType }
     *     
     */
    public void setContact(ContactExtDataType value) {
        this.contact = value;
    }

    /**
     * Gets the value of the listOfContract property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ContactEntitlements.ListOfContract }{@code >}
     *     
     */
    public JAXBElement<ContactEntitlements.ListOfContract> getListOfContract() {
        return listOfContract;
    }

    /**
     * Sets the value of the listOfContract property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ContactEntitlements.ListOfContract }{@code >}
     *     
     */
    public void setListOfContract(JAXBElement<ContactEntitlements.ListOfContract> value) {
        this.listOfContract = value;
    }

    /**
     * Gets the value of the listOfContractRole property.
     * 
     * @return
     *     possible object is
     *     {@link ContactEntitlements.ListOfContractRole }
     *     
     */
    public ContactEntitlements.ListOfContractRole getListOfContractRole() {
        return listOfContractRole;
    }

    /**
     * Sets the value of the listOfContractRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactEntitlements.ListOfContractRole }
     *     
     */
    public void setListOfContractRole(ContactEntitlements.ListOfContractRole value) {
        this.listOfContractRole = value;
    }

    /**
     * Gets the value of the listOfAsset property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfAssetType }
     *     
     */
    public ListOfAssetType getListOfAsset() {
        return listOfAsset;
    }

    /**
     * Sets the value of the listOfAsset property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfAssetType }
     *     
     */
    public void setListOfAsset(ListOfAssetType value) {
        this.listOfAsset = value;
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
     *         &lt;element name="Contract" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractLiteType" maxOccurs="unbounded" minOccurs="0"/>
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
    public static class ListOfContract {

        @XmlElement(name = "Contract")
        protected List<ContractLiteType> contract;

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
         * {@link ContractLiteType }
         * 
         * 
         */
        public List<ContractLiteType> getContract() {
            if (contract == null) {
                contract = new ArrayList<ContractLiteType>();
            }
            return this.contract;
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
     *         &lt;element name="ContractRole" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://www.autodesk.com/schemas/Business/PartyV2.0}RoleType">
     *                 &lt;choice>
     *                   &lt;element name="ListOfAssociatedAsset" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="AssociatedAsset" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}AssetRefDataType" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="ListOfAssociatedContract" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="AssociatedContract" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractRefDataType" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="ListOfAssociatedEntitlement" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="AssociatedEntitlement" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}EntitlementRefDataType" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/choice>
     *               &lt;/extension>
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
        "contractRole"
    })
    public static class ListOfContractRole {

        @XmlElement(name = "ContractRole")
        protected List<ContactEntitlements.ListOfContractRole.ContractRole> contractRole;

        /**
         * Gets the value of the contractRole property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the contractRole property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContractRole().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ContactEntitlements.ListOfContractRole.ContractRole }
         * 
         * 
         */
        public List<ContactEntitlements.ListOfContractRole.ContractRole> getContractRole() {
            if (contractRole == null) {
                contractRole = new ArrayList<ContactEntitlements.ListOfContractRole.ContractRole>();
            }
            return this.contractRole;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://www.autodesk.com/schemas/Business/PartyV2.0}RoleType">
         *       &lt;choice>
         *         &lt;element name="ListOfAssociatedAsset" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="AssociatedAsset" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}AssetRefDataType" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="ListOfAssociatedContract" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="AssociatedContract" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractRefDataType" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="ListOfAssociatedEntitlement" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="AssociatedEntitlement" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}EntitlementRefDataType" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/choice>
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "listOfAssociatedAsset",
            "listOfAssociatedContract",
            "listOfAssociatedEntitlement"
        })
        public static class ContractRole
            extends RoleType
        {

            @XmlElement(name = "ListOfAssociatedAsset")
            protected ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedAsset listOfAssociatedAsset;
            @XmlElement(name = "ListOfAssociatedContract")
            protected ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedContract listOfAssociatedContract;
            @XmlElement(name = "ListOfAssociatedEntitlement")
            protected ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedEntitlement listOfAssociatedEntitlement;

            /**
             * Gets the value of the listOfAssociatedAsset property.
             * 
             * @return
             *     possible object is
             *     {@link ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedAsset }
             *     
             */
            public ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedAsset getListOfAssociatedAsset() {
                return listOfAssociatedAsset;
            }

            /**
             * Sets the value of the listOfAssociatedAsset property.
             * 
             * @param value
             *     allowed object is
             *     {@link ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedAsset }
             *     
             */
            public void setListOfAssociatedAsset(ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedAsset value) {
                this.listOfAssociatedAsset = value;
            }

            /**
             * Gets the value of the listOfAssociatedContract property.
             * 
             * @return
             *     possible object is
             *     {@link ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedContract }
             *     
             */
            public ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedContract getListOfAssociatedContract() {
                return listOfAssociatedContract;
            }

            /**
             * Sets the value of the listOfAssociatedContract property.
             * 
             * @param value
             *     allowed object is
             *     {@link ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedContract }
             *     
             */
            public void setListOfAssociatedContract(ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedContract value) {
                this.listOfAssociatedContract = value;
            }

            /**
             * Gets the value of the listOfAssociatedEntitlement property.
             * 
             * @return
             *     possible object is
             *     {@link ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedEntitlement }
             *     
             */
            public ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedEntitlement getListOfAssociatedEntitlement() {
                return listOfAssociatedEntitlement;
            }

            /**
             * Sets the value of the listOfAssociatedEntitlement property.
             * 
             * @param value
             *     allowed object is
             *     {@link ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedEntitlement }
             *     
             */
            public void setListOfAssociatedEntitlement(ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedEntitlement value) {
                this.listOfAssociatedEntitlement = value;
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
             *         &lt;element name="AssociatedAsset" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}AssetRefDataType" maxOccurs="unbounded" minOccurs="0"/>
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
                "associatedAsset"
            })
            public static class ListOfAssociatedAsset {

                @XmlElement(name = "AssociatedAsset")
                protected List<AssetRefDataType> associatedAsset;

                /**
                 * Gets the value of the associatedAsset property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the associatedAsset property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getAssociatedAsset().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link AssetRefDataType }
                 * 
                 * 
                 */
                public List<AssetRefDataType> getAssociatedAsset() {
                    if (associatedAsset == null) {
                        associatedAsset = new ArrayList<AssetRefDataType>();
                    }
                    return this.associatedAsset;
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
             *         &lt;element name="AssociatedContract" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}ContractRefDataType" maxOccurs="unbounded" minOccurs="0"/>
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
                "associatedContract"
            })
            public static class ListOfAssociatedContract {

                @XmlElement(name = "AssociatedContract")
                protected List<ContractRefDataType> associatedContract;

                /**
                 * Gets the value of the associatedContract property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the associatedContract property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getAssociatedContract().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link ContractRefDataType }
                 * 
                 * 
                 */
                public List<ContractRefDataType> getAssociatedContract() {
                    if (associatedContract == null) {
                        associatedContract = new ArrayList<ContractRefDataType>();
                    }
                    return this.associatedContract;
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
             *         &lt;element name="AssociatedEntitlement" type="{http://www.autodesk.com/schemas/Business/ContractV1.0}EntitlementRefDataType" maxOccurs="unbounded" minOccurs="0"/>
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
                "associatedEntitlement"
            })
            public static class ListOfAssociatedEntitlement {

                @XmlElement(name = "AssociatedEntitlement")
                protected List<EntitlementRefDataType> associatedEntitlement;

                /**
                 * Gets the value of the associatedEntitlement property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the associatedEntitlement property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getAssociatedEntitlement().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link EntitlementRefDataType }
                 * 
                 * 
                 */
                public List<EntitlementRefDataType> getAssociatedEntitlement() {
                    if (associatedEntitlement == null) {
                        associatedEntitlement = new ArrayList<EntitlementRefDataType>();
                    }
                    return this.associatedEntitlement;
                }

            }

        }

    }

}
