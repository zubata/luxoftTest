package com.test.dne.service;

import com.test.dne.rest.dto.RestAnswerDto;
import com.test.dne.rest.dto.RestRequestDto;

public interface MultiplyService {

    RestAnswerDto doMultiply(RestRequestDto dto);

}
