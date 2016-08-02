
package com.autodesk.schemas.business.convergentchargingexv1;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ConvergentChargingExService", targetNamespace = "http://www.autodesk.com/schemas/Business/ConvergentChargingExV1.0", wsdlLocation = "file:/C:/EISAutomation/build/ConvergentCharging.wsdl")
public class ConvergentChargingExService
    extends Service
{

    private final static URL CONVERGENTCHARGINGEXSERVICE_WSDL_LOCATION;
    private final static WebServiceException CONVERGENTCHARGINGEXSERVICE_EXCEPTION;
    private final static QName CONVERGENTCHARGINGEXSERVICE_QNAME = new QName("http://www.autodesk.com/schemas/Business/ConvergentChargingExV1.0", "ConvergentChargingExService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/EISAutomation/build/ConvergentCharging.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CONVERGENTCHARGINGEXSERVICE_WSDL_LOCATION = url;
        CONVERGENTCHARGINGEXSERVICE_EXCEPTION = e;
    }

    public ConvergentChargingExService() {
        super(__getWsdlLocation(), CONVERGENTCHARGINGEXSERVICE_QNAME);
    }

    public ConvergentChargingExService(WebServiceFeature... features) {
        super(__getWsdlLocation(), CONVERGENTCHARGINGEXSERVICE_QNAME, features);
    }

    public ConvergentChargingExService(URL wsdlLocation) {
        super(wsdlLocation, CONVERGENTCHARGINGEXSERVICE_QNAME);
    }

    public ConvergentChargingExService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CONVERGENTCHARGINGEXSERVICE_QNAME, features);
    }

    public ConvergentChargingExService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ConvergentChargingExService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ConvergentChargingExPortType
     */
    @WebEndpoint(name = "ConvergentChargingExPort")
    public ConvergentChargingExPortType getConvergentChargingExPort() {
        return super.getPort(new QName("http://www.autodesk.com/schemas/Business/ConvergentChargingExV1.0", "ConvergentChargingExPort"), ConvergentChargingExPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ConvergentChargingExPortType
     */
    @WebEndpoint(name = "ConvergentChargingExPort")
    public ConvergentChargingExPortType getConvergentChargingExPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.autodesk.com/schemas/Business/ConvergentChargingExV1.0", "ConvergentChargingExPort"), ConvergentChargingExPortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CONVERGENTCHARGINGEXSERVICE_EXCEPTION!= null) {
            throw CONVERGENTCHARGINGEXSERVICE_EXCEPTION;
        }
        return CONVERGENTCHARGINGEXSERVICE_WSDL_LOCATION;
    }

}