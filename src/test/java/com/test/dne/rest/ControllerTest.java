package com.test.dne.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.dne.DnsApplication;
import com.test.dne.rest.dto.RestAnswerDto;
import com.test.dne.rest.dto.RestRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование Rest запроса к SOAP сервису dne")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { DnsApplication.class })
@WebAppConfiguration
class ControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private final RestRequestDto REQUEST_DTO = new RestRequestDto(8,4);
    private final RestRequestDto FAIL_DIVIDE_REQUEST_DTO = new RestRequestDto(10, 0);

    private final RestAnswerDto ADD_ANSWER = new RestAnswerDto(12);
    private final RestAnswerDto SUBTRACT_ANSWER = new RestAnswerDto(4);
    private final RestAnswerDto MULTIPLY_ANSWER = new RestAnswerDto(32);
    private final RestAnswerDto DIVIDE_ANSWER = new RestAnswerDto(2);

    private final String FORMAT_FAIL = "{\"numberA\": 1, \"numberB\":\"2d\"}";
    private final String MISS_FAIL = "{\"numberA\": 1}";

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @DisplayName("Тест сложения двух чисел")
    @Test
    void addSuccessTest() throws Exception {
        MvcResult result = mvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(REQUEST_DTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(objectMapper.writeValueAsString(ADD_ANSWER));
    }

    @DisplayName("Тест вычитания двух чисел")
    @Test
    void subtractSuccessTest() throws Exception {
        MvcResult result = mvc.perform(post("/subtract")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(REQUEST_DTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(objectMapper.writeValueAsString(SUBTRACT_ANSWER));
    }

    @DisplayName("Тест умножения двух чисел")
    @Test
    void multiplySuccessTest() throws Exception {
        MvcResult result = mvc.perform(post("/multiply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(objectMapper.writeValueAsString(MULTIPLY_ANSWER));
    }

    @DisplayName("Тест деления двух чисел")
    @Test
    void divideSuccessTest() throws Exception {
        MvcResult result = mvc.perform(post("/divide")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(objectMapper.writeValueAsString(DIVIDE_ANSWER));
    }

    @DisplayName("Тест деления на ноль")
    @Test
    void divideFailTest() throws Exception {
        MvcResult result = mvc.perform(post("/divide")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(FAIL_DIVIDE_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("На ноль делить нельзя!");
    }

    @DisplayName("Тест неправильно формата одного из чисел")
    @Test
    void formatFailTest() throws Exception {
        MvcResult result = mvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(FORMAT_FAIL))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("Неправильно передан формат поля numberB");
    }

    @DisplayName("Тест запроса с недостающим количестом чисел")
    @Test
    void missFailTest() throws Exception {
        MvcResult result = mvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MISS_FAIL))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("Ошибка в переданных данных, не передано поле numberB");
    }

}