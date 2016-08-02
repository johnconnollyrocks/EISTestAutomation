
package com.autodesk.schemas.technical.commonv2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ObjectTableMapType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectTableMapType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ObjectName" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="ObjectKeyName" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;sequence>
 *           &lt;element name="ListOfTables">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Table" maxOccurs="unbounded">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="TableName" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *                               &lt;element name="SQL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                               &lt;element name="PKName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectTableMapType", propOrder = {
    "objectName",
    "objectKeyName",
    "description",
    "listOfTables"
})
public class ObjectTableMapType {

    @XmlElement(name = "ObjectName", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String objectName;
    @XmlElement(name = "ObjectKeyName", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String objectKeyName;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "ListOfTables", required = true)
    protected ObjectTableMapType.ListOfTables listOfTables;

    /**
     * Gets the value of the objectName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * Sets the value of the objectName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectName(String value) {
        this.objectName = value;
    }

    /**
     * Gets the value of the objectKeyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectKeyName() {
        return objectKeyName;
    }

    /**
     * Sets the value of the objectKeyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectKeyName(String value) {
        this.objectKeyName = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the listOfTables property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectTableMapType.ListOfTables }
     *     
     */
    public ObjectTableMapType.ListOfTables getListOfTables() {
        return listOfTables;
    }

    /**
     * Sets the value of the listOfTables property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectTableMapType.ListOfTables }
     *     
     */
    public void setListOfTables(ObjectTableMapType.ListOfTables value) {
        this.listOfTables = value;
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
     *         &lt;element name="Table" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="TableName" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
     *                   &lt;element name="SQL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="PKName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
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
        "table"
    })
    public static class ListOfTables {

        @XmlElement(name = "Table", required = true)
        protected List<ObjectTableMapType.ListOfTables.Table> table;

        /**
         * Gets the value of the table property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the table property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTable().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ObjectTableMapType.ListOfTables.Table }
         * 
         * 
         */
        public List<ObjectTableMapType.ListOfTables.Table> getTable() {
            if (table == null) {
                table = new ArrayList<ObjectTableMapType.ListOfTables.Table>();
            }
            return this.table;
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
         *         &lt;element name="TableName" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
         *         &lt;element name="SQL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="PKName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
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
            "tableName",
            "sql",
            "pkName"
        })
        public static class Table {

            @XmlElement(name = "TableName", required = true)
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String tableName;
            @XmlElement(name = "SQL")
            protected String sql;
            @XmlElement(name = "PKName")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String pkName;

            /**
             * Gets the value of the tableName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTableName() {
                return tableName;
            }

            /**
             * Sets the value of the tableName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTableName(String value) {
                this.tableName = value;
            }

            /**
             * Gets the value of the sql property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSQL() {
                return sql;
            }

            /**
             * Sets the value of the sql property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSQL(String value) {
                this.sql = value;
            }

            /**
             * Gets the value of the pkName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPKName() {
                return pkName;
            }

            /**
             * Sets the value of the pkName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPKName(String value) {
                this.pkName = value;
            }

        }

    }

}
