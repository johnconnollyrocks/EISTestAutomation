
package com.autodesk.ws.user._1;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import com.autodesk.schemas.business.partyv2.AuthenticateUserRequest;
import com.autodesk.schemas.business.partyv2.AuthenticateUserResponse;
import com.autodesk.schemas.business.partyv2.AuthorizeRequest;
import com.autodesk.schemas.business.partyv2.AuthorizeResponse;
import com.autodesk.schemas.business.partyv2.CreateUserRequest;
import com.autodesk.schemas.business.partyv2.CreateUserResponse;
import com.autodesk.schemas.business.partyv2.EmailPasswordResetRequest;
import com.autodesk.schemas.business.partyv2.EmailPasswordResetResponse;
import com.autodesk.schemas.business.partyv2.EmailUserIdRequest;
import com.autodesk.schemas.business.partyv2.EmailUserIdResponse;
import com.autodesk.schemas.business.partyv2.GetUserByEmailRequest;
import com.autodesk.schemas.business.partyv2.GetUserByEmailResponse;
import com.autodesk.schemas.business.partyv2.GetUserByGUIDRequest;
import com.autodesk.schemas.business.partyv2.GetUserByGUIDResponse;
import com.autodesk.schemas.business.partyv2.GetUserByUserIdRequest;
import com.autodesk.schemas.business.partyv2.GetUserByUserIdResponse;
import com.autodesk.schemas.business.partyv2.UpdateUserRequest;
import com.autodesk.schemas.business.partyv2.UpdateUserResponse;
import com.autodesk.schemas.business.partyv2.UserDisableRequest;
import com.autodesk.schemas.business.partyv2.UserDisableResponse;
import com.autodesk.schemas.business.partyv2.UserEnableRequest;
import com.autodesk.schemas.business.partyv2.UserEnableResponse;
import com.autodesk.schemas.business.partyv2.ValidateSecurityAnswerRequest;
import com.autodesk.schemas.business.partyv2.ValidateSecurityAnswerResponse;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "UserPort", targetNamespace = "http://www.autodesk.com/ws/User/1.0")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    com.autodesk.schemas.business.commonv1.ObjectFactory.class,
    com.autodesk.schemas.business.partyv2.ObjectFactory.class,
    com.autodesk.schemas.business.contractv1.ObjectFactory.class,
    com.autodesk.schemas.technical.commonv2.ObjectFactory.class,
    com.autodesk.schemas.business.assetv1.ObjectFactory.class,
    com.autodesk.schemas.technical.common.respsonseheaderv1.ObjectFactory.class,
    com.autodesk.schemas.technical.common.requestheaderv1.ObjectFactory.class,
    com.autodesk.schemas.business.productv1.ObjectFactory.class
})
public interface UserPort {


    /**
     * 
     * @param authorizeRequest
     * @return
     *     returns com.autodesk.schemas.business.partyv2.AuthorizeResponse
     * @throws SOAPFault
     */
    @WebMethod(operationName = "Authorize", action = "Authorize")
    @WebResult(name = "AuthorizeResponse", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "AuthorizeResponse")
    public AuthorizeResponse authorize(
        @WebParam(name = "AuthorizeRequest", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "AuthorizeRequest")
        AuthorizeRequest authorizeRequest)
        throws SOAPFault
    ;

    /**
     * 
     * @param request
     * @return
     *     returns com.autodesk.schemas.business.partyv2.CreateUserResponse
     * @throws SOAPFault
     */
    @WebMethod(operationName = "CreateUser", action = "CreateUserAction")
    @WebResult(name = "CreateUserResponse", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Response")
    public CreateUserResponse createUser(
        @WebParam(name = "CreateUserRequest", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Request")
        CreateUserRequest request)
        throws SOAPFault
    ;

    /**
     * 
     * @param request
     * @return
     *     returns com.autodesk.schemas.business.partyv2.UserDisableResponse
     * @throws SOAPFault
     */
    @WebMethod(operationName = "UserDisable", action = "/Services/Enterprise/UserService.serviceagent//UserDisable")
    @WebResult(name = "UserDisableResponse", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Response")
    public UserDisableResponse userDisable(
        @WebParam(name = "UserDisableRequest", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Request")
        UserDisableRequest request)
        throws SOAPFault
    ;

    /**
     * 
     * @param request
     * @return
     *     returns com.autodesk.schemas.business.partyv2.GetUserByGUIDResponse
     * @throws SOAPFault
     */
    @WebMethod(operationName = "GetUserByGUID", action = "GetUserByGUIDAction")
    @WebResult(name = "GetUserByGUIDResponse", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Response")
    public GetUserByGUIDResponse getUserByGUID(
        @WebParam(name = "GetUserByGUIDRequest", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Request")
        GetUserByGUIDRequest request)
        throws SOAPFault
    ;

    /**
     * 
     * @param request
     * @return
     *     returns com.autodesk.schemas.business.partyv2.AuthenticateUserResponse
     * @throws SOAPFault
     */
    @WebMethod(operationName = "AuthenticateUser", action = "AuthenticateUserAction")
    @WebResult(name = "AuthenticateUserResponse", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Response")
    public AuthenticateUserResponse authenticateUser(
        @WebParam(name = "AuthenticateUserRequest", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Request")
        AuthenticateUserRequest request)
        throws SOAPFault
    ;

    /**
     * 
     * @param request
     * @return
     *     returns com.autodesk.schemas.business.partyv2.ValidateSecurityAnswerResponse
     * @throws SOAPFault
     */
    @WebMethod(operationName = "ValidateSecurityAnswer", action = "ValidateSecurityAnswerAction")
    @WebResult(name = "ValidateSecurityAnswerResponse", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Response")
    public ValidateSecurityAnswerResponse validateSecurityAnswer(
        @WebParam(name = "ValidateSecurityAnswerRequest", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Request")
        ValidateSecurityAnswerRequest request)
        throws SOAPFault
    ;

    /**
     * 
     * @param request
     * @return
     *     returns com.autodesk.schemas.business.partyv2.UserEnableResponse
     * @throws SOAPFault
     */
    @WebMethod(operationName = "UserEnable", action = "/Services/Enterprise/UserService.serviceagent//UserEnable")
    @WebResult(name = "UserEnableResponse", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Response")
    public UserEnableResponse userEnable(
        @WebParam(name = "UserEnableRequest", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Request")
        UserEnableRequest request)
        throws SOAPFault
    ;

    /**
     * 
     * @param request
     * @return
     *     returns com.autodesk.schemas.business.partyv2.GetUserByEmailResponse
     * @throws SOAPFault
     */
    @WebMethod(operationName = "GetUserByEmail", action = "GetUserByEmailAction")
    @WebResult(name = "GetUserByEmailResponse", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Response")
    public GetUserByEmailResponse getUserByEmail(
        @WebParam(name = "GetUserByEmailRequest", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Request")
        GetUserByEmailRequest request)
        throws SOAPFault
    ;

    /**
     * 
     * @param request
     * @return
     *     returns com.autodesk.schemas.business.partyv2.EmailUserIdResponse
     * @throws SOAPFault
     */
    @WebMethod(operationName = "EmailUserId", action = "EmailUserIdAction")
    @WebResult(name = "EmailUserIdResponse", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Response")
    public EmailUserIdResponse emailUserId(
        @WebParam(name = "EmailUserIdRequest", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Request")
        EmailUserIdRequest request)
        throws SOAPFault
    ;

    /**
     * 
     * @param request
     * @return
     *     returns com.autodesk.schemas.business.partyv2.GetUserByUserIdResponse
     * @throws SOAPFault
     */
    @WebMethod(operationName = "GetUserByUserId", action = "GetUserByUserIdAction")
    @WebResult(name = "GetUserByUserIdResponse", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Response")
    public GetUserByUserIdResponse getUserByUserId(
        @WebParam(name = "GetUserByUserIdRequest", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Request")
        GetUserByUserIdRequest request)
        throws SOAPFault
    ;

    /**
     * 
     * @param request
     * @return
     *     returns com.autodesk.schemas.business.partyv2.EmailPasswordResetResponse
     * @throws SOAPFault
     */
    @WebMethod(operationName = "EmailPasswordReset", action = "EmailPasswordResetAction")
    @WebResult(name = "EmailPasswordResetResponse", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Response")
    public EmailPasswordResetResponse emailPasswordReset(
        @WebParam(name = "EmailPasswordResetRequest", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Request")
        EmailPasswordResetRequest request)
        throws SOAPFault
    ;

    /**
     * 
     * @param request
     * @return
     *     returns com.autodesk.schemas.business.partyv2.UpdateUserResponse
     * @throws SOAPFault
     */
    @WebMethod(operationName = "UpdateUser", action = "UpdateUserAction")
    @WebResult(name = "UpdateUserResponse", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Response")
    public UpdateUserResponse updateUser(
        @WebParam(name = "UpdateUserRequest", targetNamespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", partName = "Request")
        UpdateUserRequest request)
        throws SOAPFault
    ;

}
