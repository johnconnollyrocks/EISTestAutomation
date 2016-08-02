
package com.autodesk.schemas.business.assetv1;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="XSLT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="License" type="{http://www.autodesk.com/schemas/Business/AssetV1.0}LicenseRequestTypeIn" maxOccurs="unbounded"/>
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
    "xslt",
    "license"
})
@XmlRootElement(name = "GetLicenseRequest")
public class GetLicenseRequest {

    @XmlElement(name = "XSLT")
    protected String xslt;
    @XmlElement(name = "License", required = true)
    protected List<LicenseRequestTypeIn> license;

    /**
     * Gets the value of the xslt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXSLT() {
        return xslt;
    }

    /**
     * Sets the value of the xslt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXSLT(String value) {
        this.xslt = value;
    }

    /**
     * Gets the value of the license property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the license property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLicense().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LicenseRequestTypeIn }
     * 
     * 
     */
    public List<LicenseRequestTypeIn> getLicense() {
        if (license == null) {
            license = new ArrayList<LicenseRequestTypeIn>();
        }
        return this.license;
    }

}
