package ru.binarysimple.billng.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import ru.binarysimple.billng.model.Account;

/**
 * DTO for {@link Account}
 */
@Value
public class AccountOperationDto {
    @NotNull
    @NotEmpty
    String username;
}