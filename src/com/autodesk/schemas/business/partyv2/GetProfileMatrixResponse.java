
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="ListOfProfileMatrix" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ListOfProfileMatrix"/>
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
    "listOfProfileMatrix"
})
@XmlRootElement(name = "GetProfileMatrixResponse")
public class GetProfileMatrixResponse {

    @XmlElement(name = "ListOfProfileMatrix", required = true)
    protected ListOfProfileMatrix listOfProfileMatrix;

    /**
     * Gets the value of the listOfProfileMatrix property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfProfileMatrix }
     *     
     */
    public ListOfProfileMatrix getListOfProfileMatrix() {
        return listOfProfileMatrix;
    }

    /**
     * Sets the value of the listOfProfileMatrix property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfProfileMatrix }
     *     
     */
    public void setListOfProfileMatrix(ListOfProfileMatrix value) {
        this.listOfProfileMatrix = value;
    }

}
