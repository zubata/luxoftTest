package com.test.dne.rest;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.test.dne.rest.dto.RestAnswerDto;
import com.test.dne.rest.dto.RestRequestDto;
import com.test.dne.service.AddService;
import com.test.dne.service.DivideService;
import com.test.dne.service.MultiplyService;
import com.test.dne.service.SubtractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="Вычисления", description="Позволяется посылать запрос на DNEonline")
public class Controller {

    private AddService addService;
    private MultiplyService multiplyService;
    private DivideService divideService;
    private SubtractService subtractService;

    @Autowired
    public Controller(AddService addService, MultiplyService multiplyService, DivideService divideService, SubtractService subtractService) {
        this.addService = addService;
        this.multiplyService = multiplyService;
        this.divideService = divideService;
        this.subtractService = subtractService;
    }

    @Operation(
            summary = "Сложение",
            description = "Позволяет сложить два числа"
    )
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Parameter(description = "Числа") RestRequestDto dto) {
        RestAnswerDto result = addService.doAdd(dto);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Умножение",
            description = "Позволяет умножить два числа"
    )
    @PostMapping("/multiply")
    public ResponseEntity multiply(@RequestBody @Parameter(description = "Числа") RestRequestDto dto) {
        RestAnswerDto result = multiplyService.doMultiply(dto);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Деление",
            description = "Позволяет поделить число на другое"
    )
    @PostMapping("/divide")
    public ResponseEntity divide(@RequestBody @Parameter(description = "Числа") RestRequestDto dto) {
        RestAnswerDto result = divideService.doDivide(dto);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Вычитание",
            description = "Позволяет вычитать одно число из другого"
    )
    @PostMapping("/subtract")
    public ResponseEntity subtract(@RequestBody @Parameter(description = "Числа") RestRequestDto dto) {
        RestAnswerDto result = subtractService.doSubtract(dto);
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler({JsonMappingException.class})
    public ResponseEntity errorJsonHandler(JsonMappingException e) {
        String error;
        if (e instanceof InvalidFormatException) {
            error = String.format("Неправильно передан формат поля %s", e.getPath().get(0).getFieldName());
        } else if (e instanceof MismatchedInputException) {
            error = String.format("Ошибка в переданных данных, не передано поле %s", e.getPath().get(0).getFieldName());
        } else {
            error = e.toString();
        }

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler({ArithmeticException.class})
    public ResponseEntity errorDivideHandler(ArithmeticException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
