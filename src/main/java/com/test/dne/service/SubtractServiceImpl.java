package com.test.dne.service;

import com.test.dne.rest.dto.RestAnswerDto;
import com.test.dne.rest.dto.RestRequestDto;
import com.test.dne.soap.SOAPConnector;
import com.test.dne.soap.entity.Subtract;
import com.test.dne.soap.entity.SubtractResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubtractServiceImpl implements SubtractService {
    private SOAPConnector soapConnector;

    @Autowired
    public SubtractServiceImpl(SOAPConnector soapConnector) {
        this.soapConnector = soapConnector;
    }

    @Override
    public RestAnswerDto doSubtract(RestRequestDto dto) {
        Subtract subtract = convertDto(dto);

        SubtractResponse response = (SubtractResponse) soapConnector.callWebService("http://dneonline.com/calculator.asmx",
                subtract, "http://tempuri.org/Subtract");

        return new RestAnswerDto(response.getSubtractResult());
    }

    private Subtract convertDto(RestRequestDto subtractDto) {
        Subtract subtract = new Subtract();
        subtract.setIntA(subtractDto.getNumberA());
        subtract.setIntB(subtractDto.getNumberB());

        return subtract;
    }
}
