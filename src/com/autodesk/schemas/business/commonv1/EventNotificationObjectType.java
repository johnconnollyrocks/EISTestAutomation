
package com.autodesk.schemas.business.commonv1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EventNotificationObjectType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EventNotificationObjectType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EventName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EventType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EventTypeVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EventMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventNotificationObjectType", propOrder = {
    "eventName",
    "eventType",
    "eventTypeVersion",
    "eventMessage"
})
public class EventNotificationObjectType {

    @XmlElement(name = "EventName", required = true)
    protected String eventName;
    @XmlElement(name = "EventType", required = true)
    protected String eventType;
    @XmlElement(name = "EventTypeVersion", required = true)
    protected String eventTypeVersion;
    @XmlElement(name = "EventMessage", required = true)
    protected String eventMessage;

    /**
     * Gets the value of the eventName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets the value of the eventName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventName(String value) {
        this.eventName = value;
    }

    /**
     * Gets the value of the eventType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Sets the value of the eventType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventType(String value) {
        this.eventType = value;
    }

    /**
     * Gets the value of the eventTypeVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventTypeVersion() {
        return eventTypeVersion;
    }

    /**
     * Sets the value of the eventTypeVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventTypeVersion(String value) {
        this.eventTypeVersion = value;
    }

    /**
     * Gets the value of the eventMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventMessage() {
        return eventMessage;
    }

    /**
     * Sets the value of the eventMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventMessage(String value) {
        this.eventMessage = value;
    }

}
