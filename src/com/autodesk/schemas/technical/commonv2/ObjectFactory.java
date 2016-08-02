
package com.autodesk.schemas.technical.commonv2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.autodesk.schemas.technical.commonv2 package. 
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

    private final static QName _Request_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "Request");
    private final static QName _ListOfEventNotifications_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "ListOfEventNotifications");
    private final static QName _MessageSet_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "MessageSet");
    private final static QName _ListOfSessions_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "ListOfSessions");
    private final static QName _Response_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "Response");
    private final static QName _ServiceCtxtHeader_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "ServiceCtxtHeader");
    private final static QName _EventNotification_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "EventNotification");
    private final static QName _SessionControllerOperation_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "SessionControllerOperation");
    private final static QName _ChangeEvent_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "ChangeEvent");
    private final static QName _SessionExpiredFlag_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "SessionExpiredFlag");
    private final static QName _TargetAppsHeader_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "TargetAppsHeader");
    private final static QName _RequestTypeListOfTargets_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "ListOfTargets");
    private final static QName _RequestTypeSessionId_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "SessionId");
    private final static QName _RequestTypeEventName_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "EventName");
    private final static QName _RequestTypeRequestNumber_QNAME = new QName("http://www.autodesk.com/schemas/Technical/CommonV2.0", "RequestNumber");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.autodesk.schemas.technical.commonv2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ValidateFaultType }
     * 
     */
    public ValidateFaultType createValidateFaultType() {
        return new ValidateFaultType();
    }

    /**
     * Create an instance of {@link ObjectTableMapType }
     * 
     */
    public ObjectTableMapType createObjectTableMapType() {
        return new ObjectTableMapType();
    }

    /**
     * Create an instance of {@link ObjectTableMapType.ListOfTables }
     * 
     */
    public ObjectTableMapType.ListOfTables createObjectTableMapTypeListOfTables() {
        return new ObjectTableMapType.ListOfTables();
    }

    /**
     * Create an instance of {@link MessageSetType }
     * 
     */
    public MessageSetType createMessageSetType() {
        return new MessageSetType();
    }

    /**
     * Create an instance of {@link ChangeEventType }
     * 
     */
    public ChangeEventType createChangeEventType() {
        return new ChangeEventType();
    }

    /**
     * Create an instance of {@link EventNotificationType }
     * 
     */
    public EventNotificationType createEventNotificationType() {
        return new EventNotificationType();
    }

    /**
     * Create an instance of {@link ResponseType }
     * 
     */
    public ResponseType createResponseType() {
        return new ResponseType();
    }

    /**
     * Create an instance of {@link ServiceCtxType }
     * 
     */
    public ServiceCtxType createServiceCtxType() {
        return new ServiceCtxType();
    }

    /**
     * Create an instance of {@link RequestType }
     * 
     */
    public RequestType createRequestType() {
        return new RequestType();
    }

    /**
     * Create an instance of {@link ListOfEventNotificationsType }
     * 
     */
    public ListOfEventNotificationsType createListOfEventNotificationsType() {
        return new ListOfEventNotificationsType();
    }

    /**
     * Create an instance of {@link TransactionId }
     * 
     */
    public TransactionId createTransactionId() {
        return new TransactionId();
    }

    /**
     * Create an instance of {@link LastTransaction }
     * 
     */
    public LastTransaction createLastTransaction() {
        return new LastTransaction();
    }

    /**
     * Create an instance of {@link ListOfTargetAppType }
     * 
     */
    public ListOfTargetAppType createListOfTargetAppType() {
        return new ListOfTargetAppType();
    }

    /**
     * Create an instance of {@link ObjectTableMap }
     * 
     */
    public ObjectTableMap createObjectTableMap() {
        return new ObjectTableMap();
    }

    /**
     * Create an instance of {@link ValidateFault }
     * 
     */
    public ValidateFault createValidateFault() {
        return new ValidateFault();
    }

    /**
     * Create an instance of {@link ListOfSessionType }
     * 
     */
    public ListOfSessionType createListOfSessionType() {
        return new ListOfSessionType();
    }

    /**
     * Create an instance of {@link SOAPFault }
     * 
     */
    public SOAPFault createSOAPFault() {
        return new SOAPFault();
    }

    /**
     * Create an instance of {@link SOAPFaultType }
     * 
     */
    public SOAPFaultType createSOAPFaultType() {
        return new SOAPFaultType();
    }

    /**
     * Create an instance of {@link SessionType }
     * 
     */
    public SessionType createSessionType() {
        return new SessionType();
    }

    /**
     * Create an instance of {@link AttributeType }
     * 
     */
    public AttributeType createAttributeType() {
        return new AttributeType();
    }

    /**
     * Create an instance of {@link TargetAppType }
     * 
     */
    public TargetAppType createTargetAppType() {
        return new TargetAppType();
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
     * Create an instance of {@link ValidateFaultType.ListOfAttribute }
     * 
     */
    public ValidateFaultType.ListOfAttribute createValidateFaultTypeListOfAttribute() {
        return new ValidateFaultType.ListOfAttribute();
    }

    /**
     * Create an instance of {@link ObjectTableMapType.ListOfTables.Table }
     * 
     */
    public ObjectTableMapType.ListOfTables.Table createObjectTableMapTypeListOfTablesTable() {
        return new ObjectTableMapType.ListOfTables.Table();
    }

    /**
     * Create an instance of {@link MessageSetType.Message }
     * 
     */
    public MessageSetType.Message createMessageSetTypeMessage() {
        return new MessageSetType.Message();
    }

    /**
     * Create an instance of {@link ChangeEventType.Response }
     * 
     */
    public ChangeEventType.Response createChangeEventTypeResponse() {
        return new ChangeEventType.Response();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "Request")
    public JAXBElement<RequestType> createRequest(RequestType value) {
        return new JAXBElement<RequestType>(_Request_QNAME, RequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfEventNotificationsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "ListOfEventNotifications")
    public JAXBElement<ListOfEventNotificationsType> createListOfEventNotifications(ListOfEventNotificationsType value) {
        return new JAXBElement<ListOfEventNotificationsType>(_ListOfEventNotifications_QNAME, ListOfEventNotificationsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MessageSetType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "MessageSet")
    public JAXBElement<MessageSetType> createMessageSet(MessageSetType value) {
        return new JAXBElement<MessageSetType>(_MessageSet_QNAME, MessageSetType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfSessionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "ListOfSessions")
    public JAXBElement<ListOfSessionType> createListOfSessions(ListOfSessionType value) {
        return new JAXBElement<ListOfSessionType>(_ListOfSessions_QNAME, ListOfSessionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "Response")
    public JAXBElement<ResponseType> createResponse(ResponseType value) {
        return new JAXBElement<ResponseType>(_Response_QNAME, ResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceCtxType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "ServiceCtxtHeader")
    public JAXBElement<ServiceCtxType> createServiceCtxtHeader(ServiceCtxType value) {
        return new JAXBElement<ServiceCtxType>(_ServiceCtxtHeader_QNAME, ServiceCtxType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventNotificationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "EventNotification")
    public JAXBElement<EventNotificationType> createEventNotification(EventNotificationType value) {
        return new JAXBElement<EventNotificationType>(_EventNotification_QNAME, EventNotificationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "SessionControllerOperation")
    public JAXBElement<String> createSessionControllerOperation(String value) {
        return new JAXBElement<String>(_SessionControllerOperation_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeEventType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "ChangeEvent")
    public JAXBElement<ChangeEventType> createChangeEvent(ChangeEventType value) {
        return new JAXBElement<ChangeEventType>(_ChangeEvent_QNAME, ChangeEventType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "SessionExpiredFlag")
    public JAXBElement<Boolean> createSessionExpiredFlag(Boolean value) {
        return new JAXBElement<Boolean>(_SessionExpiredFlag_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfTargetAppType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "TargetAppsHeader")
    public JAXBElement<ListOfTargetAppType> createTargetAppsHeader(ListOfTargetAppType value) {
        return new JAXBElement<ListOfTargetAppType>(_TargetAppsHeader_QNAME, ListOfTargetAppType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfTargetAppType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "ListOfTargets", scope = RequestType.class)
    public JAXBElement<ListOfTargetAppType> createRequestTypeListOfTargets(ListOfTargetAppType value) {
        return new JAXBElement<ListOfTargetAppType>(_RequestTypeListOfTargets_QNAME, ListOfTargetAppType.class, RequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "SessionId", scope = RequestType.class)
    public JAXBElement<String> createRequestTypeSessionId(String value) {
        return new JAXBElement<String>(_RequestTypeSessionId_QNAME, String.class, RequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "EventName", scope = RequestType.class)
    public JAXBElement<String> createRequestTypeEventName(String value) {
        return new JAXBElement<String>(_RequestTypeEventName_QNAME, String.class, RequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", name = "RequestNumber", scope = RequestType.class)
    public JAXBElement<String> createRequestTypeRequestNumber(String value) {
        return new JAXBElement<String>(_RequestTypeRequestNumber_QNAME, String.class, RequestType.class, value);
    }

}
