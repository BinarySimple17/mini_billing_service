package ru.binarysimple.billng.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import ru.binarysimple.billng.model.Account;

import java.math.BigDecimal;

/**
 * DTO for {@link Account}
 */
@Value
public class AccountCreateDto {
    @NotNull
    @NotEmpty
    String username;
    BigDecimal balance;
}