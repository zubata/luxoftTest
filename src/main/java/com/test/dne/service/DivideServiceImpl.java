package com.test.dne.service;

import com.test.dne.rest.dto.RestAnswerDto;
import com.test.dne.rest.dto.RestRequestDto;
import com.test.dne.soap.SOAPConnector;
import com.test.dne.soap.entity.Divide;
import com.test.dne.soap.entity.DivideResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DivideServiceImpl implements DivideService {
    private SOAPConnector soapConnector;

    @Autowired
    public DivideServiceImpl(SOAPConnector soapConnector) {
        this.soapConnector = soapConnector;
    }

    @Override
    public RestAnswerDto doDivide(RestRequestDto dto) {
        Divide divide = convertDto(dto);

        DivideResponse response = (DivideResponse) soapConnector.callWebService("http://dneonline.com/calculator.asmx",
                divide, "http://tempuri.org/Divide");

        return new RestAnswerDto(response.getDivideResult());
    }

    private Divide convertDto(RestRequestDto divideDto) {
        Divide divide = new Divide();
        divide.setIntA(divideDto.getNumberA());
        divide.setIntB(divideDto.getNumberB());

        if (divide.getIntB() == 0) {
            throw new ArithmeticException("На ноль делить нельзя!");
        }

        return divide;
    }
}
