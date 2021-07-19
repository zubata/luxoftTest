package com.test.dne.service;

import com.test.dne.rest.dto.RestAnswerDto;
import com.test.dne.rest.dto.RestRequestDto;
import com.test.dne.soap.SOAPConnector;
import com.test.dne.soap.entity.Add;
import com.test.dne.soap.entity.AddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddServiceImpl implements AddService {

    private SOAPConnector soapConnector;

    @Autowired
    public AddServiceImpl(SOAPConnector soapConnector) {
        this.soapConnector = soapConnector;
    }

    @Override
    public RestAnswerDto doAdd(RestRequestDto dto) {
        Add add = convertDto(dto);

        AddResponse response = (AddResponse) soapConnector.callWebService("http://dneonline.com/calculator.asmx",
                add, "http://tempuri.org/Add");

        return new RestAnswerDto(response.getAddResult());
    }

    private Add convertDto(RestRequestDto addDto) {
        Add add = new Add();
        add.setIntA(addDto.getNumberA());
        add.setIntB(addDto.getNumberB());

        return add;
    }
}
