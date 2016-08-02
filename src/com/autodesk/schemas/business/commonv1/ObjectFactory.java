
package com.autodesk.schemas.business.commonv1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.autodesk.schemas.business.commonv1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EventNotificationResponseStatus_QNAME = new QName("http://www.autodesk.com/schemas/Business/CommonV1.0", "EventNotificationResponseStatus");
    private final static QName _EventNotificationObject_QNAME = new QName("http://www.autodesk.com/schemas/Business/CommonV1.0", "EventNotificationObject");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.autodesk.schemas.business.commonv1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FormatPhoneNumbersResponse }
     * 
     */
    public FormatPhoneNumbersResponse createFormatPhoneNumbersResponse() {
        return new FormatPhoneNumbersResponse();
    }

    /**
     * Create an instance of {@link FormatPhoneNumbersRequest }
     * 
     */
    public FormatPhoneNumbersRequest createFormatPhoneNumbersRequest() {
        return new FormatPhoneNumbersRequest();
    }

    /**
     * Create an instance of {@link FormatPhoneNumbersResponse.ListOfPhoneNumbers }
     * 
     */
    public FormatPhoneNumbersResponse.ListOfPhoneNumbers createFormatPhoneNumbersResponseListOfPhoneNumbers() {
        return new FormatPhoneNumbersResponse.ListOfPhoneNumbers();
    }

    /**
     * Create an instance of {@link FormatPhoneNumberResponse }
     * 
     */
    public FormatPhoneNumberResponse createFormatPhoneNumberResponse() {
        return new FormatPhoneNumberResponse();
    }

    /**
     * Create an instance of {@link PhoneNumberBaseType }
     * 
     */
    public PhoneNumberBaseType createPhoneNumberBaseType() {
        return new PhoneNumberBaseType();
    }

    /**
     * Create an instance of {@link EventNotificationObjectType }
     * 
     */
    public EventNotificationObjectType createEventNotificationObjectType() {
        return new EventNotificationObjectType();
    }

    /**
     * Create an instance of {@link FormatPhoneNumberRequest }
     * 
     */
    public FormatPhoneNumberRequest createFormatPhoneNumberRequest() {
        return new FormatPhoneNumberRequest();
    }

    /**
     * Create an instance of {@link PhoneNumberRefType }
     * 
     */
    public PhoneNumberRefType createPhoneNumberRefType() {
        return new PhoneNumberRefType();
    }

    /**
     * Create an instance of {@link FormatPhoneNumbersRequest.ListOfRequestedAttributes }
     * 
     */
    public FormatPhoneNumbersRequest.ListOfRequestedAttributes createFormatPhoneNumbersRequestListOfRequestedAttributes() {
        return new FormatPhoneNumbersRequest.ListOfRequestedAttributes();
    }

    /**
     * Create an instance of {@link FormatPhoneNumbersRequest.ListOfPhoneNumbers }
     * 
     */
    public FormatPhoneNumbersRequest.ListOfPhoneNumbers createFormatPhoneNumbersRequestListOfPhoneNumbers() {
        return new FormatPhoneNumbersRequest.ListOfPhoneNumbers();
    }

    /**
     * Create an instance of {@link AttributeType }
     * 
     */
    public AttributeType createAttributeType() {
        return new AttributeType();
    }

    /**
     * Create an instance of {@link ActivityType }
     * 
     */
    public ActivityType createActivityType() {
        return new ActivityType();
    }

    /**
     * Create an instance of {@link ListOfAddressType }
     * 
     */
    public ListOfAddressType createListOfAddressType() {
        return new ListOfAddressType();
    }

    /**
     * Create an instance of {@link ListOfPhoneNumberType }
     * 
     */
    public ListOfPhoneNumberType createListOfPhoneNumberType() {
        return new ListOfPhoneNumberType();
    }

    /**
     * Create an instance of {@link NoteType }
     * 
     */
    public NoteType createNoteType() {
        return new NoteType();
    }

    /**
     * Create an instance of {@link PhoneNumberType }
     * 
     */
    public PhoneNumberType createPhoneNumberType() {
        return new PhoneNumberType();
    }

    /**
     * Create an instance of {@link ListOfAttributeType }
     * 
     */
    public ListOfAttributeType createListOfAttributeType() {
        return new ListOfAttributeType();
    }

    /**
     * Create an instance of {@link ListOfActivityType }
     * 
     */
    public ListOfActivityType createListOfActivityType() {
        return new ListOfActivityType();
    }

    /**
     * Create an instance of {@link EmailType }
     * 
     */
    public EmailType createEmailType() {
        return new EmailType();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link MessageType }
     * 
     */
    public MessageType createMessageType() {
        return new MessageType();
    }

    /**
     * Create an instance of {@link ListOfMessageType }
     * 
     */
    public ListOfMessageType createListOfMessageType() {
        return new ListOfMessageType();
    }

    /**
     * Create an instance of {@link ListOfNoteType }
     * 
     */
    public ListOfNoteType createListOfNoteType() {
        return new ListOfNoteType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/CommonV1.0", name = "EventNotificationResponseStatus")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createEventNotificationResponseStatus(String value) {
        return new JAXBElement<String>(_EventNotificationResponseStatus_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventNotificationObjectType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/CommonV1.0", name = "EventNotificationObject")
    public JAXBElement<EventNotificationObjectType> createEventNotificationObject(EventNotificationObjectType value) {
        return new JAXBElement<EventNotificationObjectType>(_EventNotificationObject_QNAME, EventNotificationObjectType.class, null, value);
    }

}
