package ru.binarysimple.billng.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDto {
    int code;
    String message;
}