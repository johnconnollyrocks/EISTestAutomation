
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import com.autodesk.schemas.business.commonv1.ListOfAddressType;
import com.autodesk.schemas.business.commonv1.ListOfPhoneNumberType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.autodesk.schemas.business.partyv2 package. 
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

    private final static QName _ListOfAccount_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ListOfAccount");
    private final static QName _AccountContact_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "AccountContact");
    private final static QName _Contact_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "Contact");
    private final static QName _ContactAccount_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ContactAccount");
    private final static QName _AccountContactAssociation_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "AccountContactAssociation");
    private final static QName _ContactBaseData_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ContactBaseData");
    private final static QName _ContactRefData_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ContactRefData");
    private final static QName _NotifyContactProfileResp_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "NotifyContactProfileResp");
    private final static QName _ContactProfile_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ContactProfile");
    private final static QName _PartnerUser_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "PartnerUser");
    private final static QName _Account_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "Account");
    private final static QName _ProfileMatrix_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ProfileMatrix");
    private final static QName _ListOfContact_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ListOfContact");
    private final static QName _User_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "User");
    private final static QName _ProcessExportControlRequest_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ProcessExportControlRequest");
    private final static QName _ContactCoreData_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ContactCoreData");
    private final static QName _AccountRefData_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "AccountRefData");
    private final static QName _ContactEntitlementsListOfContract_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ListOfContract");
    private final static QName _SecurityQATypeSecurityQuestion_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "SecurityQuestion");
    private final static QName _SecurityQATypeSecurityAnswer_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "SecurityAnswer");
    private final static QName _AccountRefDataTypeId_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "Id");
    private final static QName _AccountRefDataTypeAccountCSN_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "AccountCSN");
    private final static QName _AccountRefDataTypeUUID_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "UUID");
    private final static QName _AccountBaseDataTypeLocation_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "Location");
    private final static QName _AccountBaseDataTypeAccountName_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "AccountName");
    private final static QName _AccountBaseDataTypeLocationType_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "LocationType");
    private final static QName _GetAccountRequestAccountType_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "AccountType");
    private final static QName _GetAccountRequestCity_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "City");
    private final static QName _GetAccountRequestListOfAccountNumbers_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ListOfAccountNumbers");
    private final static QName _GetAccountRequestGeo_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "Geo");
    private final static QName _GetAccountRequestCountry_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "Country");
    private final static QName _GetAccountRequestPostalCode_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "PostalCode");
    private final static QName _GetAccountRequestAccountStatus_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "AccountStatus");
    private final static QName _GetAccountRequestPartnerFlag_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "PartnerFlag");
    private final static QName _GetAccountRequestState_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "State");
    private final static QName _UserBaseDataTypeFirstName_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "FirstName");
    private final static QName _UserBaseDataTypeLastName_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "LastName");
    private final static QName _UserBaseDataTypeMiddleName_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "MiddleName");
    private final static QName _UserBaseDataTypeAlternateLastName_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "AlternateLastName");
    private final static QName _UserBaseDataTypeAlternateFirstName_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "AlternateFirstName");
    private final static QName _PartnerProfileTypePartnerProfileName_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "PartnerProfileName");
    private final static QName _SecurityProfileTypeListOfSecurityQA_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ListOfSecurityQA");
    private final static QName _SecurityProfileTypeOldPassword_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "OldPassword");
    private final static QName _SecurityProfileTypeConfirmPassword_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ConfirmPassword");
    private final static QName _SecurityProfileTypeNewPassword_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "NewPassword");
    private final static QName _SecurityProfileTypePassword_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "Password");
    private final static QName _SecurityProfileTypePasswordExpired_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "PasswordExpired");
    private final static QName _UserRefDataTypeUserId_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "UserId");
    private final static QName _UserRefDataTypeGUID_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "GUID");
    private final static QName _UserRefDataTypeEmail_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "Email");
    private final static QName _UserTypeSuppressSurvey_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "SuppressSurvey");
    private final static QName _UserTypeSuppressMarketingOptions_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "SuppressMarketingOptions");
    private final static QName _UserTypeUserType_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "UserType");
    private final static QName _UserTypeSuppressAllCalls_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "SuppressAllCalls");
    private final static QName _UserTypeCountryCode_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "CountryCode");
    private final static QName _UserTypeLanguage_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "Language");
    private final static QName _UserTypeSuppressAllFaxes_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "SuppressAllFaxes");
    private final static QName _UserTypeStatus_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "Status");
    private final static QName _UserTypeSuppressAllMailings_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "SuppressAllMailings");
    private final static QName _UserTypeSuppressAllEmails_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "SuppressAllEmails");
    private final static QName _ContactRefDataTypeSCPKID_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "SCPKID");
    private final static QName _ContactRefDataTypeContactCSN_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ContactCSN");
    private final static QName _ContactExtDataTypeContactSource_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ContactSource");
    private final static QName _ContactExtDataTypeCreateDate_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "CreateDate");
    private final static QName _ContactExtDataTypeListOfAddress_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ListOfAddress");
    private final static QName _ContactExtDataTypeListOfRelatedAccount_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ListOfRelatedAccount");
    private final static QName _ContactExtDataTypeIndustry_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "Industry");
    private final static QName _ContactExtDataTypeDepartment_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "Department");
    private final static QName _ContactExtDataTypeLastLoginDate_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "LastLoginDate");
    private final static QName _ContactExtDataTypeStatusReason_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "StatusReason");
    private final static QName _ContactExtDataTypeAssignedSerialNumber_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "AssignedSerialNumber");
    private final static QName _AccountExtDataTypeVATregistrationNumber_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "VATregistrationNumber");
    private final static QName _AccountExtDataTypeDUNSData_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "DUNSData");
    private final static QName _AccountExtDataTypeCurrencyCode_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "CurrencyCode");
    private final static QName _AccountExtDataTypePrimaryAccountCountry_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "PrimaryAccountCountry");
    private final static QName _AccountExtDataTypePartnerType_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "PartnerType");
    private final static QName _AccountExtDataTypeStandardizedName_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "StandardizedName");
    private final static QName _AccountExtDataTypeExportControlNotes_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ExportControlNotes");
    private final static QName _AccountExtDataTypeEmailAddress_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "EmailAddress");
    private final static QName _AccountExtDataTypeInternalOrg_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "InternalOrg");
    private final static QName _AccountExtDataTypeOtherAddresses_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "OtherAddresses");
    private final static QName _AccountExtDataTypeDeDupToken_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "DeDupToken");
    private final static QName _AccountExtDataTypeRegion_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "Region");
    private final static QName _AccountExtDataTypeAccountGroup_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "AccountGroup");
    private final static QName _AccountExtDataTypeDataSource_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "DataSource");
    private final static QName _AccountExtDataTypeHomePage_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "HomePage");
    private final static QName _AccountExtDataTypeIndividualAccount_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "IndividualAccount");
    private final static QName _AccountExtDataTypeLanguageCode_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "LanguageCode");
    private final static QName _PartnerUserTypeIsDelegatedAdmin_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "isDelegatedAdmin");
    private final static QName _PartnerUserTypePrimaryAccount_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "PrimaryAccount");
    private final static QName _PartnerUserTypePartnerPosition_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "PartnerPosition");
    private final static QName _DUNSDataTypeParentDUNSNumber_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ParentDUNSNumber");
    private final static QName _DUNSDataTypeGlobalUltimateDUNSNumber_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "GlobalUltimateDUNSNumber");
    private final static QName _DUNSDataTypeCompanyDUNSNumber_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "CompanyDUNSNumber");
    private final static QName _DUNSDataTypeDomesticUltimateDUNSNumber_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "DomesticUltimateDUNSNumber");
    private final static QName _DUNSDataTypeGlobalUltimateBusinessName_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "GlobalUltimateBusinessName");
    private final static QName _DUNSDataTypeAccountDUNSNumber_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "AccountDUNSNumber");
    private final static QName _DUNSDataTypeDomesticUltimateBusinessName_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "DomesticUltimateBusinessName");
    private final static QName _ContactCoreDataTypeSalutation_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "Salutation");
    private final static QName _ContactCoreDataTypeJobTitle_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "JobTitle");
    private final static QName _ContactCoreDataTypeListOfPhone_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ListOfPhone");
    private final static QName _AccountCoreDataTypeExportControlFlag_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ExportControlFlag");
    private final static QName _AccountCoreDataTypeLocalLanguageName_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "LocalLanguageName");
    private final static QName _AccountCoreDataTypeExportControlStatus_QNAME = new QName("http://www.autodesk.com/schemas/Business/PartyV2.0", "ExportControlStatus");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.autodesk.schemas.business.partyv2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAccountRequest }
     * 
     */
    public GetAccountRequest createGetAccountRequest() {
        return new GetAccountRequest();
    }

    /**
     * Create an instance of {@link AccountEntitlements }
     * 
     */
    public AccountEntitlements createAccountEntitlements() {
        return new AccountEntitlements();
    }

    /**
     * Create an instance of {@link ContactEntitlements }
     * 
     */
    public ContactEntitlements createContactEntitlements() {
        return new ContactEntitlements();
    }

    /**
     * Create an instance of {@link ContactEntitlements.ListOfContractRole }
     * 
     */
    public ContactEntitlements.ListOfContractRole createContactEntitlementsListOfContractRole() {
        return new ContactEntitlements.ListOfContractRole();
    }

    /**
     * Create an instance of {@link ContactEntitlements.ListOfContractRole.ContractRole }
     * 
     */
    public ContactEntitlements.ListOfContractRole.ContractRole createContactEntitlementsListOfContractRoleContractRole() {
        return new ContactEntitlements.ListOfContractRole.ContractRole();
    }

    /**
     * Create an instance of {@link ContactExtDataType }
     * 
     */
    public ContactExtDataType createContactExtDataType() {
        return new ContactExtDataType();
    }

    /**
     * Create an instance of {@link ContactExtDataType.ListOfRelatedAccount }
     * 
     */
    public ContactExtDataType.ListOfRelatedAccount createContactExtDataTypeListOfRelatedAccount() {
        return new ContactExtDataType.ListOfRelatedAccount();
    }

    /**
     * Create an instance of {@link ContactExtDataType.ListOfProfession }
     * 
     */
    public ContactExtDataType.ListOfProfession createContactExtDataTypeListOfProfession() {
        return new ContactExtDataType.ListOfProfession();
    }

    /**
     * Create an instance of {@link ContactExtDataType.ListOfPreference }
     * 
     */
    public ContactExtDataType.ListOfPreference createContactExtDataTypeListOfPreference() {
        return new ContactExtDataType.ListOfPreference();
    }

    /**
     * Create an instance of {@link ContactExtDataType.ListOfExternalAppStatus }
     * 
     */
    public ContactExtDataType.ListOfExternalAppStatus createContactExtDataTypeListOfExternalAppStatus() {
        return new ContactExtDataType.ListOfExternalAppStatus();
    }

    /**
     * Create an instance of {@link ListOfContactType }
     * 
     */
    public ListOfContactType createListOfContactType() {
        return new ListOfContactType();
    }

    /**
     * Create an instance of {@link UserType }
     * 
     */
    public UserType createUserType() {
        return new UserType();
    }

    /**
     * Create an instance of {@link GetAccountRequest.ListOfAccountNumbers }
     * 
     */
    public GetAccountRequest.ListOfAccountNumbers createGetAccountRequestListOfAccountNumbers() {
        return new GetAccountRequest.ListOfAccountNumbers();
    }

    /**
     * Create an instance of {@link MatchContactRequest }
     * 
     */
    public MatchContactRequest createMatchContactRequest() {
        return new MatchContactRequest();
    }

    /**
     * Create an instance of {@link CleanseContactResponse }
     * 
     */
    public CleanseContactResponse createCleanseContactResponse() {
        return new CleanseContactResponse();
    }

    /**
     * Create an instance of {@link GetAccountEntitlementsRequest }
     * 
     */
    public GetAccountEntitlementsRequest createGetAccountEntitlementsRequest() {
        return new GetAccountEntitlementsRequest();
    }

    /**
     * Create an instance of {@link AccountRefDataType }
     * 
     */
    public AccountRefDataType createAccountRefDataType() {
        return new AccountRefDataType();
    }

    /**
     * Create an instance of {@link EmailPasswordResetResponse }
     * 
     */
    public EmailPasswordResetResponse createEmailPasswordResetResponse() {
        return new EmailPasswordResetResponse();
    }

    /**
     * Create an instance of {@link GetUserByGUIDRequest }
     * 
     */
    public GetUserByGUIDRequest createGetUserByGUIDRequest() {
        return new GetUserByGUIDRequest();
    }

    /**
     * Create an instance of {@link ValidateSecurityAnswerRequest }
     * 
     */
    public ValidateSecurityAnswerRequest createValidateSecurityAnswerRequest() {
        return new ValidateSecurityAnswerRequest();
    }

    /**
     * Create an instance of {@link MergeContact }
     * 
     */
    public MergeContact createMergeContact() {
        return new MergeContact();
    }

    /**
     * Create an instance of {@link ContactType }
     * 
     */
    public ContactType createContactType() {
        return new ContactType();
    }

    /**
     * Create an instance of {@link ContactCoreDataType }
     * 
     */
    public ContactCoreDataType createContactCoreDataType() {
        return new ContactCoreDataType();
    }

    /**
     * Create an instance of {@link CleanseContactRequest }
     * 
     */
    public CleanseContactRequest createCleanseContactRequest() {
        return new CleanseContactRequest();
    }

    /**
     * Create an instance of {@link UserEnableResponse }
     * 
     */
    public UserEnableResponse createUserEnableResponse() {
        return new UserEnableResponse();
    }

    /**
     * Create an instance of {@link EmailUserIdRequest }
     * 
     */
    public EmailUserIdRequest createEmailUserIdRequest() {
        return new EmailUserIdRequest();
    }

    /**
     * Create an instance of {@link GetUserByUserIdRequest }
     * 
     */
    public GetUserByUserIdRequest createGetUserByUserIdRequest() {
        return new GetUserByUserIdRequest();
    }

    /**
     * Create an instance of {@link UpdatePartnerUserResponse }
     * 
     */
    public UpdatePartnerUserResponse createUpdatePartnerUserResponse() {
        return new UpdatePartnerUserResponse();
    }

    /**
     * Create an instance of {@link ListOfClaimRequests }
     * 
     */
    public ListOfClaimRequests createListOfClaimRequests() {
        return new ListOfClaimRequests();
    }

    /**
     * Create an instance of {@link ClaimRequest }
     * 
     */
    public ClaimRequest createClaimRequest() {
        return new ClaimRequest();
    }

    /**
     * Create an instance of {@link ContactAccountType }
     * 
     */
    public ContactAccountType createContactAccountType() {
        return new ContactAccountType();
    }

    /**
     * Create an instance of {@link GetUserTokenResponse }
     * 
     */
    public GetUserTokenResponse createGetUserTokenResponse() {
        return new GetUserTokenResponse();
    }

    /**
     * Create an instance of {@link GetAccountResponse }
     * 
     */
    public GetAccountResponse createGetAccountResponse() {
        return new GetAccountResponse();
    }

    /**
     * Create an instance of {@link ListOfAccountType }
     * 
     */
    public ListOfAccountType createListOfAccountType() {
        return new ListOfAccountType();
    }

    /**
     * Create an instance of {@link AccountType }
     * 
     */
    public AccountType createAccountType() {
        return new AccountType();
    }

    /**
     * Create an instance of {@link AccountEntitlements.ListOfContract }
     * 
     */
    public AccountEntitlements.ListOfContract createAccountEntitlementsListOfContract() {
        return new AccountEntitlements.ListOfContract();
    }

    /**
     * Create an instance of {@link UpdateUserResponse }
     * 
     */
    public UpdateUserResponse createUpdateUserResponse() {
        return new UpdateUserResponse();
    }

    /**
     * Create an instance of {@link AuthorizeResponse }
     * 
     */
    public AuthorizeResponse createAuthorizeResponse() {
        return new AuthorizeResponse();
    }

    /**
     * Create an instance of {@link ListOfClaimResponses }
     * 
     */
    public ListOfClaimResponses createListOfClaimResponses() {
        return new ListOfClaimResponses();
    }

    /**
     * Create an instance of {@link ClaimResponse }
     * 
     */
    public ClaimResponse createClaimResponse() {
        return new ClaimResponse();
    }

    /**
     * Create an instance of {@link GetContactEntitlementsResponse }
     * 
     */
    public GetContactEntitlementsResponse createGetContactEntitlementsResponse() {
        return new GetContactEntitlementsResponse();
    }

    /**
     * Create an instance of {@link ContactEntitlements.ListOfContract }
     * 
     */
    public ContactEntitlements.ListOfContract createContactEntitlementsListOfContract() {
        return new ContactEntitlements.ListOfContract();
    }

    /**
     * Create an instance of {@link GetAccountEntitlementsResponse }
     * 
     */
    public GetAccountEntitlementsResponse createGetAccountEntitlementsResponse() {
        return new GetAccountEntitlementsResponse();
    }

    /**
     * Create an instance of {@link ProvisionRequest }
     * 
     */
    public ProvisionRequest createProvisionRequest() {
        return new ProvisionRequest();
    }

    /**
     * Create an instance of {@link EmailPasswordResetRequest }
     * 
     */
    public EmailPasswordResetRequest createEmailPasswordResetRequest() {
        return new EmailPasswordResetRequest();
    }

    /**
     * Create an instance of {@link MergeContactResponse }
     * 
     */
    public MergeContactResponse createMergeContactResponse() {
        return new MergeContactResponse();
    }

    /**
     * Create an instance of {@link ContactRefDataType }
     * 
     */
    public ContactRefDataType createContactRefDataType() {
        return new ContactRefDataType();
    }

    /**
     * Create an instance of {@link ContactBaseDataType }
     * 
     */
    public ContactBaseDataType createContactBaseDataType() {
        return new ContactBaseDataType();
    }

    /**
     * Create an instance of {@link AccountContactAssociationType }
     * 
     */
    public AccountContactAssociationType createAccountContactAssociationType() {
        return new AccountContactAssociationType();
    }

    /**
     * Create an instance of {@link GetUserByGUIDResponse }
     * 
     */
    public GetUserByGUIDResponse createGetUserByGUIDResponse() {
        return new GetUserByGUIDResponse();
    }

    /**
     * Create an instance of {@link UserDisableResponse }
     * 
     */
    public UserDisableResponse createUserDisableResponse() {
        return new UserDisableResponse();
    }

    /**
     * Create an instance of {@link UpdateUserRequest }
     * 
     */
    public UpdateUserRequest createUpdateUserRequest() {
        return new UpdateUserRequest();
    }

    /**
     * Create an instance of {@link AccountExtDataType }
     * 
     */
    public AccountExtDataType createAccountExtDataType() {
        return new AccountExtDataType();
    }

    /**
     * Create an instance of {@link GetContactProfileRequest }
     * 
     */
    public GetContactProfileRequest createGetContactProfileRequest() {
        return new GetContactProfileRequest();
    }

    /**
     * Create an instance of {@link ProfileMatrixType }
     * 
     */
    public ProfileMatrixType createProfileMatrixType() {
        return new ProfileMatrixType();
    }

    /**
     * Create an instance of {@link CreateUserResponse }
     * 
     */
    public CreateUserResponse createCreateUserResponse() {
        return new CreateUserResponse();
    }

    /**
     * Create an instance of {@link AuthorizeRequest }
     * 
     */
    public AuthorizeRequest createAuthorizeRequest() {
        return new AuthorizeRequest();
    }

    /**
     * Create an instance of {@link GetProfileMatrixResponse }
     * 
     */
    public GetProfileMatrixResponse createGetProfileMatrixResponse() {
        return new GetProfileMatrixResponse();
    }

    /**
     * Create an instance of {@link ListOfProfileMatrix }
     * 
     */
    public ListOfProfileMatrix createListOfProfileMatrix() {
        return new ListOfProfileMatrix();
    }

    /**
     * Create an instance of {@link PartnerUserType }
     * 
     */
    public PartnerUserType createPartnerUserType() {
        return new PartnerUserType();
    }

    /**
     * Create an instance of {@link CreatePartnerUserResponse }
     * 
     */
    public CreatePartnerUserResponse createCreatePartnerUserResponse() {
        return new CreatePartnerUserResponse();
    }

    /**
     * Create an instance of {@link AuthenticateUserResponse }
     * 
     */
    public AuthenticateUserResponse createAuthenticateUserResponse() {
        return new AuthenticateUserResponse();
    }

    /**
     * Create an instance of {@link SessionType }
     * 
     */
    public SessionType createSessionType() {
        return new SessionType();
    }

    /**
     * Create an instance of {@link CreateUserRequest }
     * 
     */
    public CreateUserRequest createCreateUserRequest() {
        return new CreateUserRequest();
    }

    /**
     * Create an instance of {@link AuthenticateUserRequest }
     * 
     */
    public AuthenticateUserRequest createAuthenticateUserRequest() {
        return new AuthenticateUserRequest();
    }

    /**
     * Create an instance of {@link GetUserProfileResponse }
     * 
     */
    public GetUserProfileResponse createGetUserProfileResponse() {
        return new GetUserProfileResponse();
    }

    /**
     * Create an instance of {@link UserCoreDataType }
     * 
     */
    public UserCoreDataType createUserCoreDataType() {
        return new UserCoreDataType();
    }

    /**
     * Create an instance of {@link CreatePartnerUserRequest }
     * 
     */
    public CreatePartnerUserRequest createCreatePartnerUserRequest() {
        return new CreatePartnerUserRequest();
    }

    /**
     * Create an instance of {@link ValidateContactResponse }
     * 
     */
    public ValidateContactResponse createValidateContactResponse() {
        return new ValidateContactResponse();
    }

    /**
     * Create an instance of {@link ValidateSecurityAnswerResponse }
     * 
     */
    public ValidateSecurityAnswerResponse createValidateSecurityAnswerResponse() {
        return new ValidateSecurityAnswerResponse();
    }

    /**
     * Create an instance of {@link UpdatePartnerUserRequest }
     * 
     */
    public UpdatePartnerUserRequest createUpdatePartnerUserRequest() {
        return new UpdatePartnerUserRequest();
    }

    /**
     * Create an instance of {@link GetUserByEmailRequest }
     * 
     */
    public GetUserByEmailRequest createGetUserByEmailRequest() {
        return new GetUserByEmailRequest();
    }

    /**
     * Create an instance of {@link ValidateContactRequest }
     * 
     */
    public ValidateContactRequest createValidateContactRequest() {
        return new ValidateContactRequest();
    }

    /**
     * Create an instance of {@link NotifyContactRequest }
     * 
     */
    public NotifyContactRequest createNotifyContactRequest() {
        return new NotifyContactRequest();
    }

    /**
     * Create an instance of {@link GetUserByUserIdResponse }
     * 
     */
    public GetUserByUserIdResponse createGetUserByUserIdResponse() {
        return new GetUserByUserIdResponse();
    }

    /**
     * Create an instance of {@link MergeContactRequest }
     * 
     */
    public MergeContactRequest createMergeContactRequest() {
        return new MergeContactRequest();
    }

    /**
     * Create an instance of {@link EmailUserIdResponse }
     * 
     */
    public EmailUserIdResponse createEmailUserIdResponse() {
        return new EmailUserIdResponse();
    }

    /**
     * Create an instance of {@link ProvisionResponse }
     * 
     */
    public ProvisionResponse createProvisionResponse() {
        return new ProvisionResponse();
    }

    /**
     * Create an instance of {@link AccountContactType }
     * 
     */
    public AccountContactType createAccountContactType() {
        return new AccountContactType();
    }

    /**
     * Create an instance of {@link GetContactProfileResponse }
     * 
     */
    public GetContactProfileResponse createGetContactProfileResponse() {
        return new GetContactProfileResponse();
    }

    /**
     * Create an instance of {@link GetUserProfileRequest }
     * 
     */
    public GetUserProfileRequest createGetUserProfileRequest() {
        return new GetUserProfileRequest();
    }

    /**
     * Create an instance of {@link UserRefDataType }
     * 
     */
    public UserRefDataType createUserRefDataType() {
        return new UserRefDataType();
    }

    /**
     * Create an instance of {@link GetContactEntitlementsRequest }
     * 
     */
    public GetContactEntitlementsRequest createGetContactEntitlementsRequest() {
        return new GetContactEntitlementsRequest();
    }

    /**
     * Create an instance of {@link MatchContactResponse }
     * 
     */
    public MatchContactResponse createMatchContactResponse() {
        return new MatchContactResponse();
    }

    /**
     * Create an instance of {@link GetContactDetailsResponse }
     * 
     */
    public GetContactDetailsResponse createGetContactDetailsResponse() {
        return new GetContactDetailsResponse();
    }

    /**
     * Create an instance of {@link GetUserByEmailResponse }
     * 
     */
    public GetUserByEmailResponse createGetUserByEmailResponse() {
        return new GetUserByEmailResponse();
    }

    /**
     * Create an instance of {@link UserEnableRequest }
     * 
     */
    public UserEnableRequest createUserEnableRequest() {
        return new UserEnableRequest();
    }

    /**
     * Create an instance of {@link GetUserTokenRequest }
     * 
     */
    public GetUserTokenRequest createGetUserTokenRequest() {
        return new GetUserTokenRequest();
    }

    /**
     * Create an instance of {@link UserDisableRequest }
     * 
     */
    public UserDisableRequest createUserDisableRequest() {
        return new UserDisableRequest();
    }

    /**
     * Create an instance of {@link GetContactDetailsRequest }
     * 
     */
    public GetContactDetailsRequest createGetContactDetailsRequest() {
        return new GetContactDetailsRequest();
    }

    /**
     * Create an instance of {@link ListOfSecurityQAType }
     * 
     */
    public ListOfSecurityQAType createListOfSecurityQAType() {
        return new ListOfSecurityQAType();
    }

    /**
     * Create an instance of {@link ListOfPartnerProfileType }
     * 
     */
    public ListOfPartnerProfileType createListOfPartnerProfileType() {
        return new ListOfPartnerProfileType();
    }

    /**
     * Create an instance of {@link PartnerProfileType }
     * 
     */
    public PartnerProfileType createPartnerProfileType() {
        return new PartnerProfileType();
    }

    /**
     * Create an instance of {@link AccountCoreDataType }
     * 
     */
    public AccountCoreDataType createAccountCoreDataType() {
        return new AccountCoreDataType();
    }

    /**
     * Create an instance of {@link ListOfRoleType }
     * 
     */
    public ListOfRoleType createListOfRoleType() {
        return new ListOfRoleType();
    }

    /**
     * Create an instance of {@link ListOfContactAccountType }
     * 
     */
    public ListOfContactAccountType createListOfContactAccountType() {
        return new ListOfContactAccountType();
    }

    /**
     * Create an instance of {@link ProcessExportControlResponse }
     * 
     */
    public ProcessExportControlResponse createProcessExportControlResponse() {
        return new ProcessExportControlResponse();
    }

    /**
     * Create an instance of {@link AccountBaseDataType }
     * 
     */
    public AccountBaseDataType createAccountBaseDataType() {
        return new AccountBaseDataType();
    }

    /**
     * Create an instance of {@link SecurityQAType }
     * 
     */
    public SecurityQAType createSecurityQAType() {
        return new SecurityQAType();
    }

    /**
     * Create an instance of {@link DQDataType }
     * 
     */
    public DQDataType createDQDataType() {
        return new DQDataType();
    }

    /**
     * Create an instance of {@link SecurityProfileType }
     * 
     */
    public SecurityProfileType createSecurityProfileType() {
        return new SecurityProfileType();
    }

    /**
     * Create an instance of {@link UserBaseDataType }
     * 
     */
    public UserBaseDataType createUserBaseDataType() {
        return new UserBaseDataType();
    }

    /**
     * Create an instance of {@link RoleType }
     * 
     */
    public RoleType createRoleType() {
        return new RoleType();
    }

    /**
     * Create an instance of {@link DUNSDataType }
     * 
     */
    public DUNSDataType createDUNSDataType() {
        return new DUNSDataType();
    }

    /**
     * Create an instance of {@link ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedAsset }
     * 
     */
    public ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedAsset createContactEntitlementsListOfContractRoleContractRoleListOfAssociatedAsset() {
        return new ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedAsset();
    }

    /**
     * Create an instance of {@link ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedContract }
     * 
     */
    public ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedContract createContactEntitlementsListOfContractRoleContractRoleListOfAssociatedContract() {
        return new ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedContract();
    }

    /**
     * Create an instance of {@link ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedEntitlement }
     * 
     */
    public ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedEntitlement createContactEntitlementsListOfContractRoleContractRoleListOfAssociatedEntitlement() {
        return new ContactEntitlements.ListOfContractRole.ContractRole.ListOfAssociatedEntitlement();
    }

    /**
     * Create an instance of {@link ContactExtDataType.DedupToken }
     * 
     */
    public ContactExtDataType.DedupToken createContactExtDataTypeDedupToken() {
        return new ContactExtDataType.DedupToken();
    }

    /**
     * Create an instance of {@link ContactExtDataType.ListOfRelatedAccount.RelatedAccount }
     * 
     */
    public ContactExtDataType.ListOfRelatedAccount.RelatedAccount createContactExtDataTypeListOfRelatedAccountRelatedAccount() {
        return new ContactExtDataType.ListOfRelatedAccount.RelatedAccount();
    }

    /**
     * Create an instance of {@link ContactExtDataType.ListOfProfession.Profession }
     * 
     */
    public ContactExtDataType.ListOfProfession.Profession createContactExtDataTypeListOfProfessionProfession() {
        return new ContactExtDataType.ListOfProfession.Profession();
    }

    /**
     * Create an instance of {@link ContactExtDataType.ListOfPreference.Preference }
     * 
     */
    public ContactExtDataType.ListOfPreference.Preference createContactExtDataTypeListOfPreferencePreference() {
        return new ContactExtDataType.ListOfPreference.Preference();
    }

    /**
     * Create an instance of {@link ContactExtDataType.ListOfExternalAppStatus.ExternalAppStatus }
     * 
     */
    public ContactExtDataType.ListOfExternalAppStatus.ExternalAppStatus createContactExtDataTypeListOfExternalAppStatusExternalAppStatus() {
        return new ContactExtDataType.ListOfExternalAppStatus.ExternalAppStatus();
    }

    /**
     * Create an instance of {@link ListOfContactType.Contact }
     * 
     */
    public ListOfContactType.Contact createListOfContactTypeContact() {
        return new ListOfContactType.Contact();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfAccountType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ListOfAccount")
    public JAXBElement<ListOfAccountType> createListOfAccount(ListOfAccountType value) {
        return new JAXBElement<ListOfAccountType>(_ListOfAccount_QNAME, ListOfAccountType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountContactType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AccountContact")
    public JAXBElement<AccountContactType> createAccountContact(AccountContactType value) {
        return new JAXBElement<AccountContactType>(_AccountContact_QNAME, AccountContactType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactExtDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Contact")
    public JAXBElement<ContactExtDataType> createContact(ContactExtDataType value) {
        return new JAXBElement<ContactExtDataType>(_Contact_QNAME, ContactExtDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactAccountType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ContactAccount")
    public JAXBElement<ContactAccountType> createContactAccount(ContactAccountType value) {
        return new JAXBElement<ContactAccountType>(_ContactAccount_QNAME, ContactAccountType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountContactAssociationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AccountContactAssociation")
    public JAXBElement<AccountContactAssociationType> createAccountContactAssociation(AccountContactAssociationType value) {
        return new JAXBElement<AccountContactAssociationType>(_AccountContactAssociation_QNAME, AccountContactAssociationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactBaseDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ContactBaseData")
    public JAXBElement<ContactBaseDataType> createContactBaseData(ContactBaseDataType value) {
        return new JAXBElement<ContactBaseDataType>(_ContactBaseData_QNAME, ContactBaseDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactRefDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ContactRefData")
    public JAXBElement<ContactRefDataType> createContactRefData(ContactRefDataType value) {
        return new JAXBElement<ContactRefDataType>(_ContactRefData_QNAME, ContactRefDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "NotifyContactProfileResp")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createNotifyContactProfileResp(String value) {
        return new JAXBElement<String>(_NotifyContactProfileResp_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactCoreDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ContactProfile")
    public JAXBElement<ContactCoreDataType> createContactProfile(ContactCoreDataType value) {
        return new JAXBElement<ContactCoreDataType>(_ContactProfile_QNAME, ContactCoreDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PartnerUserType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "PartnerUser")
    public JAXBElement<PartnerUserType> createPartnerUser(PartnerUserType value) {
        return new JAXBElement<PartnerUserType>(_PartnerUser_QNAME, PartnerUserType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Account")
    public JAXBElement<AccountType> createAccount(AccountType value) {
        return new JAXBElement<AccountType>(_Account_QNAME, AccountType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProfileMatrixType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ProfileMatrix")
    public JAXBElement<ProfileMatrixType> createProfileMatrix(ProfileMatrixType value) {
        return new JAXBElement<ProfileMatrixType>(_ProfileMatrix_QNAME, ProfileMatrixType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfContactType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ListOfContact")
    public JAXBElement<ListOfContactType> createListOfContact(ListOfContactType value) {
        return new JAXBElement<ListOfContactType>(_ListOfContact_QNAME, ListOfContactType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "User")
    public JAXBElement<UserType> createUser(UserType value) {
        return new JAXBElement<UserType>(_User_QNAME, UserType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountExtDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ProcessExportControlRequest")
    public JAXBElement<AccountExtDataType> createProcessExportControlRequest(AccountExtDataType value) {
        return new JAXBElement<AccountExtDataType>(_ProcessExportControlRequest_QNAME, AccountExtDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactCoreDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ContactCoreData")
    public JAXBElement<ContactCoreDataType> createContactCoreData(ContactCoreDataType value) {
        return new JAXBElement<ContactCoreDataType>(_ContactCoreData_QNAME, ContactCoreDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountRefDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AccountRefData")
    public JAXBElement<AccountRefDataType> createAccountRefData(AccountRefDataType value) {
        return new JAXBElement<AccountRefDataType>(_AccountRefData_QNAME, AccountRefDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactEntitlements.ListOfContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ListOfContract", scope = ContactEntitlements.class)
    public JAXBElement<ContactEntitlements.ListOfContract> createContactEntitlementsListOfContract(ContactEntitlements.ListOfContract value) {
        return new JAXBElement<ContactEntitlements.ListOfContract>(_ContactEntitlementsListOfContract_QNAME, ContactEntitlements.ListOfContract.class, ContactEntitlements.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SecurityQuestion", scope = SecurityQAType.class)
    public JAXBElement<String> createSecurityQATypeSecurityQuestion(String value) {
        return new JAXBElement<String>(_SecurityQATypeSecurityQuestion_QNAME, String.class, SecurityQAType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SecurityAnswer", scope = SecurityQAType.class)
    public JAXBElement<String> createSecurityQATypeSecurityAnswer(String value) {
        return new JAXBElement<String>(_SecurityQATypeSecurityAnswer_QNAME, String.class, SecurityQAType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Id", scope = AccountRefDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountRefDataTypeId(String value) {
        return new JAXBElement<String>(_AccountRefDataTypeId_QNAME, String.class, AccountRefDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AccountCSN", scope = AccountRefDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountRefDataTypeAccountCSN(String value) {
        return new JAXBElement<String>(_AccountRefDataTypeAccountCSN_QNAME, String.class, AccountRefDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "UUID", scope = AccountRefDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountRefDataTypeUUID(String value) {
        return new JAXBElement<String>(_AccountRefDataTypeUUID_QNAME, String.class, AccountRefDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Location", scope = AccountBaseDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountBaseDataTypeLocation(String value) {
        return new JAXBElement<String>(_AccountBaseDataTypeLocation_QNAME, String.class, AccountBaseDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AccountName", scope = AccountBaseDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountBaseDataTypeAccountName(String value) {
        return new JAXBElement<String>(_AccountBaseDataTypeAccountName_QNAME, String.class, AccountBaseDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "LocationType", scope = AccountBaseDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountBaseDataTypeLocationType(String value) {
        return new JAXBElement<String>(_AccountBaseDataTypeLocationType_QNAME, String.class, AccountBaseDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfAccountType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ListOfAccount", scope = ContactAccountType.class)
    public JAXBElement<ListOfAccountType> createContactAccountTypeListOfAccount(ListOfAccountType value) {
        return new JAXBElement<ListOfAccountType>(_ListOfAccount_QNAME, ListOfAccountType.class, ContactAccountType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Contact", scope = ContactAccountType.class)
    public JAXBElement<ContactType> createContactAccountTypeContact(ContactType value) {
        return new JAXBElement<ContactType>(_Contact_QNAME, ContactType.class, ContactAccountType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AccountName", scope = GetAccountRequest.class)
    public JAXBElement<String> createGetAccountRequestAccountName(String value) {
        return new JAXBElement<String>(_AccountBaseDataTypeAccountName_QNAME, String.class, GetAccountRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AccountType", scope = GetAccountRequest.class)
    public JAXBElement<String> createGetAccountRequestAccountType(String value) {
        return new JAXBElement<String>(_GetAccountRequestAccountType_QNAME, String.class, GetAccountRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "City", scope = GetAccountRequest.class)
    public JAXBElement<String> createGetAccountRequestCity(String value) {
        return new JAXBElement<String>(_GetAccountRequestCity_QNAME, String.class, GetAccountRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountRequest.ListOfAccountNumbers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ListOfAccountNumbers", scope = GetAccountRequest.class)
    public JAXBElement<GetAccountRequest.ListOfAccountNumbers> createGetAccountRequestListOfAccountNumbers(GetAccountRequest.ListOfAccountNumbers value) {
        return new JAXBElement<GetAccountRequest.ListOfAccountNumbers>(_GetAccountRequestListOfAccountNumbers_QNAME, GetAccountRequest.ListOfAccountNumbers.class, GetAccountRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Geo", scope = GetAccountRequest.class)
    public JAXBElement<String> createGetAccountRequestGeo(String value) {
        return new JAXBElement<String>(_GetAccountRequestGeo_QNAME, String.class, GetAccountRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Country", scope = GetAccountRequest.class)
    public JAXBElement<String> createGetAccountRequestCountry(String value) {
        return new JAXBElement<String>(_GetAccountRequestCountry_QNAME, String.class, GetAccountRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "PostalCode", scope = GetAccountRequest.class)
    public JAXBElement<String> createGetAccountRequestPostalCode(String value) {
        return new JAXBElement<String>(_GetAccountRequestPostalCode_QNAME, String.class, GetAccountRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AccountStatus", scope = GetAccountRequest.class)
    public JAXBElement<String> createGetAccountRequestAccountStatus(String value) {
        return new JAXBElement<String>(_GetAccountRequestAccountStatus_QNAME, String.class, GetAccountRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "PartnerFlag", scope = GetAccountRequest.class)
    public JAXBElement<Boolean> createGetAccountRequestPartnerFlag(Boolean value) {
        return new JAXBElement<Boolean>(_GetAccountRequestPartnerFlag_QNAME, Boolean.class, GetAccountRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "State", scope = GetAccountRequest.class)
    public JAXBElement<String> createGetAccountRequestState(String value) {
        return new JAXBElement<String>(_GetAccountRequestState_QNAME, String.class, GetAccountRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "FirstName", scope = UserBaseDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserBaseDataTypeFirstName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeFirstName_QNAME, String.class, UserBaseDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "LastName", scope = UserBaseDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserBaseDataTypeLastName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeLastName_QNAME, String.class, UserBaseDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "MiddleName", scope = UserBaseDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserBaseDataTypeMiddleName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeMiddleName_QNAME, String.class, UserBaseDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AlternateLastName", scope = UserBaseDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserBaseDataTypeAlternateLastName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeAlternateLastName_QNAME, String.class, UserBaseDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AlternateFirstName", scope = UserBaseDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserBaseDataTypeAlternateFirstName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeAlternateFirstName_QNAME, String.class, UserBaseDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Id", scope = PartnerProfileType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createPartnerProfileTypeId(String value) {
        return new JAXBElement<String>(_AccountRefDataTypeId_QNAME, String.class, PartnerProfileType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "UUID", scope = PartnerProfileType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createPartnerProfileTypeUUID(String value) {
        return new JAXBElement<String>(_AccountRefDataTypeUUID_QNAME, String.class, PartnerProfileType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "PartnerProfileName", scope = PartnerProfileType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createPartnerProfileTypePartnerProfileName(String value) {
        return new JAXBElement<String>(_PartnerProfileTypePartnerProfileName_QNAME, String.class, PartnerProfileType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountEntitlements.ListOfContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ListOfContract", scope = AccountEntitlements.class)
    public JAXBElement<AccountEntitlements.ListOfContract> createAccountEntitlementsListOfContract(AccountEntitlements.ListOfContract value) {
        return new JAXBElement<AccountEntitlements.ListOfContract>(_ContactEntitlementsListOfContract_QNAME, AccountEntitlements.ListOfContract.class, AccountEntitlements.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfSecurityQAType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ListOfSecurityQA", scope = SecurityProfileType.class)
    public JAXBElement<ListOfSecurityQAType> createSecurityProfileTypeListOfSecurityQA(ListOfSecurityQAType value) {
        return new JAXBElement<ListOfSecurityQAType>(_SecurityProfileTypeListOfSecurityQA_QNAME, ListOfSecurityQAType.class, SecurityProfileType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "OldPassword", scope = SecurityProfileType.class)
    public JAXBElement<String> createSecurityProfileTypeOldPassword(String value) {
        return new JAXBElement<String>(_SecurityProfileTypeOldPassword_QNAME, String.class, SecurityProfileType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ConfirmPassword", scope = SecurityProfileType.class)
    public JAXBElement<String> createSecurityProfileTypeConfirmPassword(String value) {
        return new JAXBElement<String>(_SecurityProfileTypeConfirmPassword_QNAME, String.class, SecurityProfileType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "NewPassword", scope = SecurityProfileType.class)
    public JAXBElement<String> createSecurityProfileTypeNewPassword(String value) {
        return new JAXBElement<String>(_SecurityProfileTypeNewPassword_QNAME, String.class, SecurityProfileType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Password", scope = SecurityProfileType.class)
    public JAXBElement<String> createSecurityProfileTypePassword(String value) {
        return new JAXBElement<String>(_SecurityProfileTypePassword_QNAME, String.class, SecurityProfileType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "PasswordExpired", scope = SecurityProfileType.class)
    public JAXBElement<String> createSecurityProfileTypePasswordExpired(String value) {
        return new JAXBElement<String>(_SecurityProfileTypePasswordExpired_QNAME, String.class, SecurityProfileType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "UserId", scope = UserRefDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserRefDataTypeUserId(String value) {
        return new JAXBElement<String>(_UserRefDataTypeUserId_QNAME, String.class, UserRefDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "GUID", scope = UserRefDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserRefDataTypeGUID(String value) {
        return new JAXBElement<String>(_UserRefDataTypeGUID_QNAME, String.class, UserRefDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Email", scope = UserRefDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserRefDataTypeEmail(String value) {
        return new JAXBElement<String>(_UserRefDataTypeEmail_QNAME, String.class, UserRefDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SuppressSurvey", scope = UserType.class)
    public JAXBElement<Boolean> createUserTypeSuppressSurvey(Boolean value) {
        return new JAXBElement<Boolean>(_UserTypeSuppressSurvey_QNAME, Boolean.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SuppressMarketingOptions", scope = UserType.class)
    public JAXBElement<Boolean> createUserTypeSuppressMarketingOptions(Boolean value) {
        return new JAXBElement<Boolean>(_UserTypeSuppressMarketingOptions_QNAME, Boolean.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "LastName", scope = UserType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserTypeLastName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeLastName_QNAME, String.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AlternateFirstName", scope = UserType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserTypeAlternateFirstName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeAlternateFirstName_QNAME, String.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "UserType", scope = UserType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserTypeUserType(String value) {
        return new JAXBElement<String>(_UserTypeUserType_QNAME, String.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SuppressAllCalls", scope = UserType.class)
    public JAXBElement<Boolean> createUserTypeSuppressAllCalls(Boolean value) {
        return new JAXBElement<Boolean>(_UserTypeSuppressAllCalls_QNAME, Boolean.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "CountryCode", scope = UserType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserTypeCountryCode(String value) {
        return new JAXBElement<String>(_UserTypeCountryCode_QNAME, String.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Language", scope = UserType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserTypeLanguage(String value) {
        return new JAXBElement<String>(_UserTypeLanguage_QNAME, String.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "FirstName", scope = UserType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserTypeFirstName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeFirstName_QNAME, String.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SuppressAllFaxes", scope = UserType.class)
    public JAXBElement<Boolean> createUserTypeSuppressAllFaxes(Boolean value) {
        return new JAXBElement<Boolean>(_UserTypeSuppressAllFaxes_QNAME, Boolean.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "MiddleName", scope = UserType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserTypeMiddleName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeMiddleName_QNAME, String.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AlternateLastName", scope = UserType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserTypeAlternateLastName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeAlternateLastName_QNAME, String.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Status", scope = UserType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserTypeStatus(String value) {
        return new JAXBElement<String>(_UserTypeStatus_QNAME, String.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SuppressAllMailings", scope = UserType.class)
    public JAXBElement<Boolean> createUserTypeSuppressAllMailings(Boolean value) {
        return new JAXBElement<Boolean>(_UserTypeSuppressAllMailings_QNAME, Boolean.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SuppressAllEmails", scope = UserType.class)
    public JAXBElement<Boolean> createUserTypeSuppressAllEmails(Boolean value) {
        return new JAXBElement<Boolean>(_UserTypeSuppressAllEmails_QNAME, Boolean.class, UserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SCPKID", scope = ContactRefDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactRefDataTypeSCPKID(String value) {
        return new JAXBElement<String>(_ContactRefDataTypeSCPKID_QNAME, String.class, ContactRefDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Id", scope = ContactRefDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactRefDataTypeId(String value) {
        return new JAXBElement<String>(_AccountRefDataTypeId_QNAME, String.class, ContactRefDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "UUID", scope = ContactRefDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactRefDataTypeUUID(String value) {
        return new JAXBElement<String>(_AccountRefDataTypeUUID_QNAME, String.class, ContactRefDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ContactCSN", scope = ContactRefDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactRefDataTypeContactCSN(String value) {
        return new JAXBElement<String>(_ContactRefDataTypeContactCSN_QNAME, String.class, ContactRefDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "GUID", scope = ContactRefDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactRefDataTypeGUID(String value) {
        return new JAXBElement<String>(_UserRefDataTypeGUID_QNAME, String.class, ContactRefDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Email", scope = ContactRefDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactRefDataTypeEmail(String value) {
        return new JAXBElement<String>(_UserRefDataTypeEmail_QNAME, String.class, ContactRefDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SuppressSurvey", scope = ContactExtDataType.class)
    public JAXBElement<Boolean> createContactExtDataTypeSuppressSurvey(Boolean value) {
        return new JAXBElement<Boolean>(_UserTypeSuppressSurvey_QNAME, Boolean.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ContactSource", scope = ContactExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactExtDataTypeContactSource(String value) {
        return new JAXBElement<String>(_ContactExtDataTypeContactSource_QNAME, String.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "CreateDate", scope = ContactExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactExtDataTypeCreateDate(String value) {
        return new JAXBElement<String>(_ContactExtDataTypeCreateDate_QNAME, String.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SuppressMarketingOptions", scope = ContactExtDataType.class)
    public JAXBElement<Boolean> createContactExtDataTypeSuppressMarketingOptions(Boolean value) {
        return new JAXBElement<Boolean>(_UserTypeSuppressMarketingOptions_QNAME, Boolean.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfAddressType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ListOfAddress", scope = ContactExtDataType.class)
    public JAXBElement<ListOfAddressType> createContactExtDataTypeListOfAddress(ListOfAddressType value) {
        return new JAXBElement<ListOfAddressType>(_ContactExtDataTypeListOfAddress_QNAME, ListOfAddressType.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SuppressAllCalls", scope = ContactExtDataType.class)
    public JAXBElement<Boolean> createContactExtDataTypeSuppressAllCalls(Boolean value) {
        return new JAXBElement<Boolean>(_UserTypeSuppressAllCalls_QNAME, Boolean.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactExtDataType.ListOfRelatedAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ListOfRelatedAccount", scope = ContactExtDataType.class)
    public JAXBElement<ContactExtDataType.ListOfRelatedAccount> createContactExtDataTypeListOfRelatedAccount(ContactExtDataType.ListOfRelatedAccount value) {
        return new JAXBElement<ContactExtDataType.ListOfRelatedAccount>(_ContactExtDataTypeListOfRelatedAccount_QNAME, ContactExtDataType.ListOfRelatedAccount.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SuppressAllFaxes", scope = ContactExtDataType.class)
    public JAXBElement<Boolean> createContactExtDataTypeSuppressAllFaxes(Boolean value) {
        return new JAXBElement<Boolean>(_UserTypeSuppressAllFaxes_QNAME, Boolean.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Industry", scope = ContactExtDataType.class)
    public JAXBElement<String> createContactExtDataTypeIndustry(String value) {
        return new JAXBElement<String>(_ContactExtDataTypeIndustry_QNAME, String.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Department", scope = ContactExtDataType.class)
    public JAXBElement<String> createContactExtDataTypeDepartment(String value) {
        return new JAXBElement<String>(_ContactExtDataTypeDepartment_QNAME, String.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "LastLoginDate", scope = ContactExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactExtDataTypeLastLoginDate(String value) {
        return new JAXBElement<String>(_ContactExtDataTypeLastLoginDate_QNAME, String.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "StatusReason", scope = ContactExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactExtDataTypeStatusReason(String value) {
        return new JAXBElement<String>(_ContactExtDataTypeStatusReason_QNAME, String.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AssignedSerialNumber", scope = ContactExtDataType.class)
    public JAXBElement<String> createContactExtDataTypeAssignedSerialNumber(String value) {
        return new JAXBElement<String>(_ContactExtDataTypeAssignedSerialNumber_QNAME, String.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SuppressAllMailings", scope = ContactExtDataType.class)
    public JAXBElement<Boolean> createContactExtDataTypeSuppressAllMailings(Boolean value) {
        return new JAXBElement<Boolean>(_UserTypeSuppressAllMailings_QNAME, Boolean.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "SuppressAllEmails", scope = ContactExtDataType.class)
    public JAXBElement<Boolean> createContactExtDataTypeSuppressAllEmails(Boolean value) {
        return new JAXBElement<Boolean>(_UserTypeSuppressAllEmails_QNAME, Boolean.class, ContactExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "VATregistrationNumber", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypeVATregistrationNumber(String value) {
        return new JAXBElement<String>(_AccountExtDataTypeVATregistrationNumber_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DUNSDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "DUNSData", scope = AccountExtDataType.class)
    public JAXBElement<DUNSDataType> createAccountExtDataTypeDUNSData(DUNSDataType value) {
        return new JAXBElement<DUNSDataType>(_AccountExtDataTypeDUNSData_QNAME, DUNSDataType.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "CurrencyCode", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypeCurrencyCode(String value) {
        return new JAXBElement<String>(_AccountExtDataTypeCurrencyCode_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "PrimaryAccountCountry", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypePrimaryAccountCountry(String value) {
        return new JAXBElement<String>(_AccountExtDataTypePrimaryAccountCountry_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "PartnerType", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypePartnerType(String value) {
        return new JAXBElement<String>(_AccountExtDataTypePartnerType_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "StandardizedName", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypeStandardizedName(String value) {
        return new JAXBElement<String>(_AccountExtDataTypeStandardizedName_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ExportControlNotes", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypeExportControlNotes(String value) {
        return new JAXBElement<String>(_AccountExtDataTypeExportControlNotes_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "EmailAddress", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypeEmailAddress(String value) {
        return new JAXBElement<String>(_AccountExtDataTypeEmailAddress_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "InternalOrg", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypeInternalOrg(String value) {
        return new JAXBElement<String>(_AccountExtDataTypeInternalOrg_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfAddressType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "OtherAddresses", scope = AccountExtDataType.class)
    public JAXBElement<ListOfAddressType> createAccountExtDataTypeOtherAddresses(ListOfAddressType value) {
        return new JAXBElement<ListOfAddressType>(_AccountExtDataTypeOtherAddresses_QNAME, ListOfAddressType.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "DeDupToken", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypeDeDupToken(String value) {
        return new JAXBElement<String>(_AccountExtDataTypeDeDupToken_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Region", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypeRegion(String value) {
        return new JAXBElement<String>(_AccountExtDataTypeRegion_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AccountGroup", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypeAccountGroup(String value) {
        return new JAXBElement<String>(_AccountExtDataTypeAccountGroup_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "DataSource", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypeDataSource(String value) {
        return new JAXBElement<String>(_AccountExtDataTypeDataSource_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "HomePage", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypeHomePage(String value) {
        return new JAXBElement<String>(_AccountExtDataTypeHomePage_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "IndividualAccount", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypeIndividualAccount(String value) {
        return new JAXBElement<String>(_AccountExtDataTypeIndividualAccount_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "LanguageCode", scope = AccountExtDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountExtDataTypeLanguageCode(String value) {
        return new JAXBElement<String>(_AccountExtDataTypeLanguageCode_QNAME, String.class, AccountExtDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "isDelegatedAdmin", scope = PartnerUserType.class)
    public JAXBElement<Boolean> createPartnerUserTypeIsDelegatedAdmin(Boolean value) {
        return new JAXBElement<Boolean>(_PartnerUserTypeIsDelegatedAdmin_QNAME, Boolean.class, PartnerUserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountBaseDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "PrimaryAccount", scope = PartnerUserType.class)
    public JAXBElement<AccountBaseDataType> createPartnerUserTypePrimaryAccount(AccountBaseDataType value) {
        return new JAXBElement<AccountBaseDataType>(_PartnerUserTypePrimaryAccount_QNAME, AccountBaseDataType.class, PartnerUserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "PartnerPosition", scope = PartnerUserType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createPartnerUserTypePartnerPosition(String value) {
        return new JAXBElement<String>(_PartnerUserTypePartnerPosition_QNAME, String.class, PartnerUserType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "FirstName", scope = ContactBaseDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactBaseDataTypeFirstName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeFirstName_QNAME, String.class, ContactBaseDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "LastName", scope = ContactBaseDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactBaseDataTypeLastName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeLastName_QNAME, String.class, ContactBaseDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountBaseDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "PrimaryAccount", scope = ContactBaseDataType.class)
    public JAXBElement<AccountBaseDataType> createContactBaseDataTypePrimaryAccount(AccountBaseDataType value) {
        return new JAXBElement<AccountBaseDataType>(_PartnerUserTypePrimaryAccount_QNAME, AccountBaseDataType.class, ContactBaseDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "MiddleName", scope = ContactBaseDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactBaseDataTypeMiddleName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeMiddleName_QNAME, String.class, ContactBaseDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AlternateLastName", scope = ContactBaseDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactBaseDataTypeAlternateLastName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeAlternateLastName_QNAME, String.class, ContactBaseDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AlternateFirstName", scope = ContactBaseDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactBaseDataTypeAlternateFirstName(String value) {
        return new JAXBElement<String>(_UserBaseDataTypeAlternateFirstName_QNAME, String.class, ContactBaseDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Account", scope = AccountContactType.class)
    public JAXBElement<AccountType> createAccountContactTypeAccount(AccountType value) {
        return new JAXBElement<AccountType>(_Account_QNAME, AccountType.class, AccountContactType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfContactType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ListOfContact", scope = AccountContactType.class)
    public JAXBElement<ListOfContactType> createAccountContactTypeListOfContact(ListOfContactType value) {
        return new JAXBElement<ListOfContactType>(_ListOfContact_QNAME, ListOfContactType.class, AccountContactType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ParentDUNSNumber", scope = DUNSDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createDUNSDataTypeParentDUNSNumber(String value) {
        return new JAXBElement<String>(_DUNSDataTypeParentDUNSNumber_QNAME, String.class, DUNSDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "GlobalUltimateDUNSNumber", scope = DUNSDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createDUNSDataTypeGlobalUltimateDUNSNumber(String value) {
        return new JAXBElement<String>(_DUNSDataTypeGlobalUltimateDUNSNumber_QNAME, String.class, DUNSDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "CompanyDUNSNumber", scope = DUNSDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createDUNSDataTypeCompanyDUNSNumber(String value) {
        return new JAXBElement<String>(_DUNSDataTypeCompanyDUNSNumber_QNAME, String.class, DUNSDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "DomesticUltimateDUNSNumber", scope = DUNSDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createDUNSDataTypeDomesticUltimateDUNSNumber(String value) {
        return new JAXBElement<String>(_DUNSDataTypeDomesticUltimateDUNSNumber_QNAME, String.class, DUNSDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "GlobalUltimateBusinessName", scope = DUNSDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createDUNSDataTypeGlobalUltimateBusinessName(String value) {
        return new JAXBElement<String>(_DUNSDataTypeGlobalUltimateBusinessName_QNAME, String.class, DUNSDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AccountDUNSNumber", scope = DUNSDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createDUNSDataTypeAccountDUNSNumber(String value) {
        return new JAXBElement<String>(_DUNSDataTypeAccountDUNSNumber_QNAME, String.class, DUNSDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "DomesticUltimateBusinessName", scope = DUNSDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createDUNSDataTypeDomesticUltimateBusinessName(String value) {
        return new JAXBElement<String>(_DUNSDataTypeDomesticUltimateBusinessName_QNAME, String.class, DUNSDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "CountryCode", scope = ContactCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactCoreDataTypeCountryCode(String value) {
        return new JAXBElement<String>(_UserTypeCountryCode_QNAME, String.class, ContactCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Language", scope = ContactCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactCoreDataTypeLanguage(String value) {
        return new JAXBElement<String>(_UserTypeLanguage_QNAME, String.class, ContactCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Salutation", scope = ContactCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactCoreDataTypeSalutation(String value) {
        return new JAXBElement<String>(_ContactCoreDataTypeSalutation_QNAME, String.class, ContactCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "JobTitle", scope = ContactCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactCoreDataTypeJobTitle(String value) {
        return new JAXBElement<String>(_ContactCoreDataTypeJobTitle_QNAME, String.class, ContactCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfPhoneNumberType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ListOfPhone", scope = ContactCoreDataType.class)
    public JAXBElement<ListOfPhoneNumberType> createContactCoreDataTypeListOfPhone(ListOfPhoneNumberType value) {
        return new JAXBElement<ListOfPhoneNumberType>(_ContactCoreDataTypeListOfPhone_QNAME, ListOfPhoneNumberType.class, ContactCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Status", scope = ContactCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createContactCoreDataTypeStatus(String value) {
        return new JAXBElement<String>(_UserTypeStatus_QNAME, String.class, ContactCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "CountryCode", scope = UserCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserCoreDataTypeCountryCode(String value) {
        return new JAXBElement<String>(_UserTypeCountryCode_QNAME, String.class, UserCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Language", scope = UserCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserCoreDataTypeLanguage(String value) {
        return new JAXBElement<String>(_UserTypeLanguage_QNAME, String.class, UserCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Salutation", scope = UserCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserCoreDataTypeSalutation(String value) {
        return new JAXBElement<String>(_ContactCoreDataTypeSalutation_QNAME, String.class, UserCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfSecurityQAType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ListOfSecurityQA", scope = UserCoreDataType.class)
    public JAXBElement<ListOfSecurityQAType> createUserCoreDataTypeListOfSecurityQA(ListOfSecurityQAType value) {
        return new JAXBElement<ListOfSecurityQAType>(_SecurityProfileTypeListOfSecurityQA_QNAME, ListOfSecurityQAType.class, UserCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "OldPassword", scope = UserCoreDataType.class)
    public JAXBElement<String> createUserCoreDataTypeOldPassword(String value) {
        return new JAXBElement<String>(_SecurityProfileTypeOldPassword_QNAME, String.class, UserCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "StatusReason", scope = UserCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserCoreDataTypeStatusReason(String value) {
        return new JAXBElement<String>(_ContactExtDataTypeStatusReason_QNAME, String.class, UserCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "Status", scope = UserCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createUserCoreDataTypeStatus(String value) {
        return new JAXBElement<String>(_UserTypeStatus_QNAME, String.class, UserCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "NewPassword", scope = UserCoreDataType.class)
    public JAXBElement<String> createUserCoreDataTypeNewPassword(String value) {
        return new JAXBElement<String>(_SecurityProfileTypeNewPassword_QNAME, String.class, UserCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "PasswordExpired", scope = UserCoreDataType.class)
    public JAXBElement<Boolean> createUserCoreDataTypePasswordExpired(Boolean value) {
        return new JAXBElement<Boolean>(_SecurityProfileTypePasswordExpired_QNAME, Boolean.class, UserCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ExportControlFlag", scope = AccountCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountCoreDataTypeExportControlFlag(String value) {
        return new JAXBElement<String>(_AccountCoreDataTypeExportControlFlag_QNAME, String.class, AccountCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AccountType", scope = AccountCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountCoreDataTypeAccountType(String value) {
        return new JAXBElement<String>(_GetAccountRequestAccountType_QNAME, String.class, AccountCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "AccountStatus", scope = AccountCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountCoreDataTypeAccountStatus(String value) {
        return new JAXBElement<String>(_GetAccountRequestAccountStatus_QNAME, String.class, AccountCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "LocalLanguageName", scope = AccountCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountCoreDataTypeLocalLanguageName(String value) {
        return new JAXBElement<String>(_AccountCoreDataTypeLocalLanguageName_QNAME, String.class, AccountCoreDataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", name = "ExportControlStatus", scope = AccountCoreDataType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createAccountCoreDataTypeExportControlStatus(String value) {
        return new JAXBElement<String>(_AccountCoreDataTypeExportControlStatus_QNAME, String.class, AccountCoreDataType.class, value);
    }

}
