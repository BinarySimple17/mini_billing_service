package ru.binarysimple.billng.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import ru.binarysimple.billng.model.Account;
import ru.binarysimple.billng.model.Operation;
import ru.binarysimple.billng.model.OperationType;

import java.math.BigDecimal;

/**
 * DTO for {@link Operation}
 */
@Value
public class OperationRequest {
    OperationType type;
    BigDecimal amount;
    @NotNull
    AccountOperationDto account;
}