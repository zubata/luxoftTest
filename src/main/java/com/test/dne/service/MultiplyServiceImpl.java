package com.test.dne.service;

import com.test.dne.rest.dto.RestAnswerDto;
import com.test.dne.rest.dto.RestRequestDto;
import com.test.dne.soap.SOAPConnector;
import com.test.dne.soap.entity.Multiply;
import com.test.dne.soap.entity.MultiplyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiplyServiceImpl implements MultiplyService {

    private SOAPConnector soapConnector;

    @Autowired
    public MultiplyServiceImpl(SOAPConnector soapConnector) {
        this.soapConnector = soapConnector;
    }

    @Override
    public RestAnswerDto doMultiply(RestRequestDto dto) {
        Multiply multiply = convertDto(dto);

        MultiplyResponse response = (MultiplyResponse) soapConnector.callWebService("http://dneonline.com/calculator.asmx",
                multiply, "http://tempuri.org/Multiply");

        return new RestAnswerDto(response.getMultiplyResult());
    }

    private Multiply convertDto(RestRequestDto multiplyDto) {
        Multiply multiply = new Multiply();
        multiply.setIntA(multiplyDto.getNumberA());
        multiply.setIntB(multiplyDto.getNumberB());

        return multiply;
    }
}
