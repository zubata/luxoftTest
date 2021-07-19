package com.test.dne.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Request", description = "Сущность запроса")
public class RestRequestDto {

    @Schema(description = "Первое число")
    private Integer numberA;
    @Schema(description = "Второе число")
    private Integer numberB;

    public int getNumberA() {
        return numberA;
    }

    public void setNumberA(int numberA) {
        this.numberA = numberA;
    }

    public int getNumberB() {
        return numberB;
    }

    public void setNumberB(int numberB) {
        this.numberB = numberB;
    }

    @JsonCreator
    public RestRequestDto(@JsonProperty(required = true) int numberA,
                          @JsonProperty(required = true) int numberB) {
        this.numberA = numberA;
        this.numberB = numberB;
    }
}
