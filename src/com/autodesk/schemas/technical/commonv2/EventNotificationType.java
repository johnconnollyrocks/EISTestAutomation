
package com.autodesk.schemas.technical.commonv2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EventNotificationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EventNotificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeliveryPriority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaxRetries" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NextRetryTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NotificationEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RetryCounter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RetryInterval" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReqTimeOutSeconds" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeliveryEndPoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubscriberName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreationTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EventTargetIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EventName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EventType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EventTypeVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConfirmOperation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NotifyStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DowntimeStartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DowntimeEndTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Partner_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastPublishDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubscriberTransport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventNotificationType", propOrder = {
    "id",
    "deliveryPriority",
    "maxRetries",
    "nextRetryTime",
    "notificationEmail",
    "retryCounter",
    "retryInterval",
    "reqTimeOutSeconds",
    "deliveryEndPoint",
    "subscriberName",
    "creationTime",
    "eventTargetIdentifier",
    "eventName",
    "eventType",
    "eventTypeVersion",
    "message",
    "confirmOperation",
    "notifyStatus",
    "errorMessage",
    "downtimeStartTime",
    "downtimeEndTime",
    "partnerID",
    "lastPublishDate",
    "subscriberTransport"
})
public class EventNotificationType {

    @XmlElement(name = "Id")
    protected String id;
    @XmlElement(name = "DeliveryPriority")
    protected String deliveryPriority;
    @XmlElement(name = "MaxRetries")
    protected String maxRetries;
    @XmlElement(name = "NextRetryTime")
    protected String nextRetryTime;
    @XmlElement(name = "NotificationEmail")
    protected String notificationEmail;
    @XmlElement(name = "RetryCounter")
    protected String retryCounter;
    @XmlElement(name = "RetryInterval")
    protected String retryInterval;
    @XmlElement(name = "ReqTimeOutSeconds")
    protected String reqTimeOutSeconds;
    @XmlElement(name = "DeliveryEndPoint")
    protected String deliveryEndPoint;
    @XmlElement(name = "SubscriberName")
    protected String subscriberName;
    @XmlElement(name = "CreationTime")
    protected String creationTime;
    @XmlElement(name = "EventTargetIdentifier")
    protected String eventTargetIdentifier;
    @XmlElement(name = "EventName")
    protected String eventName;
    @XmlElement(name = "EventType")
    protected String eventType;
    @XmlElement(name = "EventTypeVersion")
    protected String eventTypeVersion;
    @XmlElement(name = "Message")
    protected String message;
    @XmlElement(name = "ConfirmOperation")
    protected String confirmOperation;
    @XmlElement(name = "NotifyStatus")
    protected String notifyStatus;
    @XmlElement(name = "ErrorMessage")
    protected String errorMessage;
    @XmlElement(name = "DowntimeStartTime")
    protected String downtimeStartTime;
    @XmlElement(name = "DowntimeEndTime")
    protected String downtimeEndTime;
    @XmlElement(name = "Partner_ID")
    protected String partnerID;
    @XmlElement(name = "LastPublishDate")
    protected String lastPublishDate;
    @XmlElement(name = "SubscriberTransport")
    protected String subscriberTransport;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the deliveryPriority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryPriority() {
        return deliveryPriority;
    }

    /**
     * Sets the value of the deliveryPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryPriority(String value) {
        this.deliveryPriority = value;
    }

    /**
     * Gets the value of the maxRetries property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxRetries() {
        return maxRetries;
    }

    /**
     * Sets the value of the maxRetries property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxRetries(String value) {
        this.maxRetries = value;
    }

    /**
     * Gets the value of the nextRetryTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNextRetryTime() {
        return nextRetryTime;
    }

    /**
     * Sets the value of the nextRetryTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextRetryTime(String value) {
        this.nextRetryTime = value;
    }

    /**
     * Gets the value of the notificationEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotificationEmail() {
        return notificationEmail;
    }

    /**
     * Sets the value of the notificationEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotificationEmail(String value) {
        this.notificationEmail = value;
    }

    /**
     * Gets the value of the retryCounter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetryCounter() {
        return retryCounter;
    }

    /**
     * Sets the value of the retryCounter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetryCounter(String value) {
        this.retryCounter = value;
    }

    /**
     * Gets the value of the retryInterval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetryInterval() {
        return retryInterval;
    }

    /**
     * Sets the value of the retryInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetryInterval(String value) {
        this.retryInterval = value;
    }

    /**
     * Gets the value of the reqTimeOutSeconds property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqTimeOutSeconds() {
        return reqTimeOutSeconds;
    }

    /**
     * Sets the value of the reqTimeOutSeconds property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqTimeOutSeconds(String value) {
        this.reqTimeOutSeconds = value;
    }

    /**
     * Gets the value of the deliveryEndPoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryEndPoint() {
        return deliveryEndPoint;
    }

    /**
     * Sets the value of the deliveryEndPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryEndPoint(String value) {
        this.deliveryEndPoint = value;
    }

    /**
     * Gets the value of the subscriberName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscriberName() {
        return subscriberName;
    }

    /**
     * Sets the value of the subscriberName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscriberName(String value) {
        this.subscriberName = value;
    }

    /**
     * Gets the value of the creationTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreationTime() {
        return creationTime;
    }

    /**
     * Sets the value of the creationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreationTime(String value) {
        this.creationTime = value;
    }

    /**
     * Gets the value of the eventTargetIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventTargetIdentifier() {
        return eventTargetIdentifier;
    }

    /**
     * Sets the value of the eventTargetIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventTargetIdentifier(String value) {
        this.eventTargetIdentifier = value;
    }

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
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the confirmOperation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfirmOperation() {
        return confirmOperation;
    }

    /**
     * Sets the value of the confirmOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfirmOperation(String value) {
        this.confirmOperation = value;
    }

    /**
     * Gets the value of the notifyStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotifyStatus() {
        return notifyStatus;
    }

    /**
     * Sets the value of the notifyStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotifyStatus(String value) {
        this.notifyStatus = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the downtimeStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDowntimeStartTime() {
        return downtimeStartTime;
    }

    /**
     * Sets the value of the downtimeStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDowntimeStartTime(String value) {
        this.downtimeStartTime = value;
    }

    /**
     * Gets the value of the downtimeEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDowntimeEndTime() {
        return downtimeEndTime;
    }

    /**
     * Sets the value of the downtimeEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDowntimeEndTime(String value) {
        this.downtimeEndTime = value;
    }

    /**
     * Gets the value of the partnerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerID() {
        return partnerID;
    }

    /**
     * Sets the value of the partnerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerID(String value) {
        this.partnerID = value;
    }

    /**
     * Gets the value of the lastPublishDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastPublishDate() {
        return lastPublishDate;
    }

    /**
     * Sets the value of the lastPublishDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastPublishDate(String value) {
        this.lastPublishDate = value;
    }

    /**
     * Gets the value of the subscriberTransport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscriberTransport() {
        return subscriberTransport;
    }

    /**
     * Sets the value of the subscriberTransport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscriberTransport(String value) {
        this.subscriberTransport = value;
    }

}
