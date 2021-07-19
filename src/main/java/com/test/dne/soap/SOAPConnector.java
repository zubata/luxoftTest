package com.test.dne.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Component
public class SOAPConnector extends WebServiceGatewaySupport {

    @Autowired
    public SOAPConnector (Jaxb2Marshaller marshaller) {
        this.setDefaultUri("http://dneonline.com/calculator.asmx");
        this.setMarshaller(marshaller);
        this.setUnmarshaller(marshaller);
    }

    public Object callWebService(String url, Object request, String action){
        return getWebServiceTemplate().marshalSendAndReceive(url, request, new SoapActionCallback(action));
    }

}
