package ru.binarysimple.billng.dto;

import lombok.Value;
import ru.binarysimple.billng.model.Operation;
import ru.binarysimple.billng.model.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link Operation}
 */
@Value
public class OperationDto {
    Long id;
    LocalDateTime createdAt;
    OperationType type;
    BigDecimal amount;
}