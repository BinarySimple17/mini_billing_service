package ru.binarysimple.billng.dto;

import lombok.Value;
import ru.binarysimple.billng.model.Account;

import java.math.BigDecimal;

/**
 * DTO for {@link Account}
 */
@Value
public class AccountInfoDto {
    Long id;
    String username;
    BigDecimal balance;
}