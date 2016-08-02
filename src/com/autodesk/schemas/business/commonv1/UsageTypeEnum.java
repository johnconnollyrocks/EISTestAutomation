
package com.autodesk.schemas.business.commonv1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UsageTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UsageTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="COM"/>
 *     &lt;enumeration value="EDU"/>
 *     &lt;enumeration value="EMR"/>
 *     &lt;enumeration value="NFR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UsageTypeEnum")
@XmlEnum
public enum UsageTypeEnum {


    /**
     * Commercial
     * 
     */
    COM,

    /**
     * Education
     * 
     */
    EDU,
    EMR,
    NFR;

    public String value() {
        return name();
    }

    public static UsageTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
