
package com.autodesk.schemas.business.assetv1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.autodesk.schemas.business.assetv1 package. 
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

    private final static QName _AssetExt_QNAME = new QName("http://www.autodesk.com/schemas/Business/AssetV1.0", "AssetExt");
    private final static QName _Asset_QNAME = new QName("http://www.autodesk.com/schemas/Business/AssetV1.0", "Asset");
    private final static QName _ListOfCourse_QNAME = new QName("http://www.autodesk.com/schemas/Business/AssetV1.0", "ListOfCourse");
    private final static QName _ListOfAsset_QNAME = new QName("http://www.autodesk.com/schemas/Business/AssetV1.0", "ListOfAsset");
    private final static QName _License_QNAME = new QName("http://www.autodesk.com/schemas/Business/AssetV1.0", "License");
    private final static QName _GetLicenseResponse_QNAME = new QName("http://www.autodesk.com/schemas/Business/AssetV1.0", "GetLicenseResponse");
    private final static QName _InProductRequest_QNAME = new QName("http://www.autodesk.com/schemas/Business/AssetV1.0", "InProductRequest");
    private final static QName _InProductResponse_QNAME = new QName("http://www.autodesk.com/schemas/Business/AssetV1.0", "InProductResponse");
    private final static QName _ProcessAssetStatus_QNAME = new QName("http://www.autodesk.com/schemas/Business/AssetV1.0", "ProcessAssetStatus");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.autodesk.schemas.business.assetv1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListOfProductLineType }
     * 
     */
    public ListOfProductLineType createListOfProductLineType() {
        return new ListOfProductLineType();
    }

    /**
     * Create an instance of {@link AssetLiteDataType }
     * 
     */
    public AssetLiteDataType createAssetLiteDataType() {
        return new AssetLiteDataType();
    }

    /**
     * Create an instance of {@link AssetBaseDataType }
     * 
     */
    public AssetBaseDataType createAssetBaseDataType() {
        return new AssetBaseDataType();
    }

    /**
     * Create an instance of {@link InProductRequestType }
     * 
     */
    public InProductRequestType createInProductRequestType() {
        return new InProductRequestType();
    }

    /**
     * Create an instance of {@link ListOfCourseType }
     * 
     */
    public ListOfCourseType createListOfCourseType() {
        return new ListOfCourseType();
    }

    /**
     * Create an instance of {@link ListOfCourseType.Course }
     * 
     */
    public ListOfCourseType.Course createListOfCourseTypeCourse() {
        return new ListOfCourseType.Course();
    }

    /**
     * Create an instance of {@link ListOfLicenseTypeOut }
     * 
     */
    public ListOfLicenseTypeOut createListOfLicenseTypeOut() {
        return new ListOfLicenseTypeOut();
    }

    /**
     * Create an instance of {@link LicenseType }
     * 
     */
    public LicenseType createLicenseType() {
        return new LicenseType();
    }

    /**
     * Create an instance of {@link AssetType }
     * 
     */
    public AssetType createAssetType() {
        return new AssetType();
    }

    /**
     * Create an instance of {@link GetLicenseRequest }
     * 
     */
    public GetLicenseRequest createGetLicenseRequest() {
        return new GetLicenseRequest();
    }

    /**
     * Create an instance of {@link LicenseRequestTypeIn }
     * 
     */
    public LicenseRequestTypeIn createLicenseRequestTypeIn() {
        return new LicenseRequestTypeIn();
    }

    /**
     * Create an instance of {@link InProductResponseType }
     * 
     */
    public InProductResponseType createInProductResponseType() {
        return new InProductResponseType();
    }

    /**
     * Create an instance of {@link ListOfAssetType }
     * 
     */
    public ListOfAssetType createListOfAssetType() {
        return new ListOfAssetType();
    }

    /**
     * Create an instance of {@link AssetExtDataType }
     * 
     */
    public AssetExtDataType createAssetExtDataType() {
        return new AssetExtDataType();
    }

    /**
     * Create an instance of {@link AssetCoreDataType }
     * 
     */
    public AssetCoreDataType createAssetCoreDataType() {
        return new AssetCoreDataType();
    }

    /**
     * Create an instance of {@link ListOfRelationshipType }
     * 
     */
    public ListOfRelationshipType createListOfRelationshipType() {
        return new ListOfRelationshipType();
    }

    /**
     * Create an instance of {@link AssetRefDataType }
     * 
     */
    public AssetRefDataType createAssetRefDataType() {
        return new AssetRefDataType();
    }

    /**
     * Create an instance of {@link ListOfAssetRelationship }
     * 
     */
    public ListOfAssetRelationship createListOfAssetRelationship() {
        return new ListOfAssetRelationship();
    }

    /**
     * Create an instance of {@link RelationshipType }
     * 
     */
    public RelationshipType createRelationshipType() {
        return new RelationshipType();
    }

    /**
     * Create an instance of {@link ListOfProductLineType.ProductLine }
     * 
     */
    public ListOfProductLineType.ProductLine createListOfProductLineTypeProductLine() {
        return new ListOfProductLineType.ProductLine();
    }

    /**
     * Create an instance of {@link AssetLiteDataType.ListOfEntitlement }
     * 
     */
    public AssetLiteDataType.ListOfEntitlement createAssetLiteDataTypeListOfEntitlement() {
        return new AssetLiteDataType.ListOfEntitlement();
    }

    /**
     * Create an instance of {@link AssetBaseDataType.ListOfEntitlement }
     * 
     */
    public AssetBaseDataType.ListOfEntitlement createAssetBaseDataTypeListOfEntitlement() {
        return new AssetBaseDataType.ListOfEntitlement();
    }

    /**
     * Create an instance of {@link InProductRequestType.Product }
     * 
     */
    public InProductRequestType.Product createInProductRequestTypeProduct() {
        return new InProductRequestType.Product();
    }

    /**
     * Create an instance of {@link InProductRequestType.User }
     * 
     */
    public InProductRequestType.User createInProductRequestTypeUser() {
        return new InProductRequestType.User();
    }

    /**
     * Create an instance of {@link ListOfCourseType.Course.Module }
     * 
     */
    public ListOfCourseType.Course.Module createListOfCourseTypeCourseModule() {
        return new ListOfCourseType.Course.Module();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssetExtDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/AssetV1.0", name = "AssetExt")
    public JAXBElement<AssetExtDataType> createAssetExt(AssetExtDataType value) {
        return new JAXBElement<AssetExtDataType>(_AssetExt_QNAME, AssetExtDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssetType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/AssetV1.0", name = "Asset")
    public JAXBElement<AssetType> createAsset(AssetType value) {
        return new JAXBElement<AssetType>(_Asset_QNAME, AssetType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfCourseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/AssetV1.0", name = "ListOfCourse")
    public JAXBElement<ListOfCourseType> createListOfCourse(ListOfCourseType value) {
        return new JAXBElement<ListOfCourseType>(_ListOfCourse_QNAME, ListOfCourseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfAssetType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/AssetV1.0", name = "ListOfAsset")
    public JAXBElement<ListOfAssetType> createListOfAsset(ListOfAssetType value) {
        return new JAXBElement<ListOfAssetType>(_ListOfAsset_QNAME, ListOfAssetType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LicenseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/AssetV1.0", name = "License")
    public JAXBElement<LicenseType> createLicense(LicenseType value) {
        return new JAXBElement<LicenseType>(_License_QNAME, LicenseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfLicenseTypeOut }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/AssetV1.0", name = "GetLicenseResponse")
    public JAXBElement<ListOfLicenseTypeOut> createGetLicenseResponse(ListOfLicenseTypeOut value) {
        return new JAXBElement<ListOfLicenseTypeOut>(_GetLicenseResponse_QNAME, ListOfLicenseTypeOut.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InProductRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/AssetV1.0", name = "InProductRequest")
    public JAXBElement<InProductRequestType> createInProductRequest(InProductRequestType value) {
        return new JAXBElement<InProductRequestType>(_InProductRequest_QNAME, InProductRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InProductResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/AssetV1.0", name = "InProductResponse")
    public JAXBElement<InProductResponseType> createInProductResponse(InProductResponseType value) {
        return new JAXBElement<InProductResponseType>(_InProductResponse_QNAME, InProductResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.autodesk.com/schemas/Business/AssetV1.0", name = "ProcessAssetStatus")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createProcessAssetStatus(String value) {
        return new JAXBElement<String>(_ProcessAssetStatus_QNAME, String.class, null, value);
    }

}
