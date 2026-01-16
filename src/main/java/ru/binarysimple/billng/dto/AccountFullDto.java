package ru.binarysimple.billng.dto;

import lombok.Value;
import ru.binarysimple.billng.model.Account;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for {@link Account}
 */
@Value
public class AccountFullDto {
    Long id;
    String username;
    BigDecimal balance;
    List<OperationDto> operations;
}