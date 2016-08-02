
package com.autodesk.schemas.business.partyv2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListOfProfileMatrix complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListOfProfileMatrix">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.autodesk.com/schemas/Business/PartyV2.0}ProfileMatrix" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListOfProfileMatrix", propOrder = {
    "profileMatrix"
})
public class ListOfProfileMatrix {

    @XmlElement(name = "ProfileMatrix")
    protected List<ProfileMatrixType> profileMatrix;

    /**
     * Gets the value of the profileMatrix property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the profileMatrix property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProfileMatrix().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProfileMatrixType }
     * 
     * 
     */
    public List<ProfileMatrixType> getProfileMatrix() {
        if (profileMatrix == null) {
            profileMatrix = new ArrayList<ProfileMatrixType>();
        }
        return this.profileMatrix;
    }

}
