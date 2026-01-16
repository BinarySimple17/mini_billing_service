package ru.binarysimple.billng.mapper;

import org.mapstruct.*;
import ru.binarysimple.billng.dto.AccountCreateDto;
import ru.binarysimple.billng.dto.AccountFullDto;
import ru.binarysimple.billng.dto.AccountInfoDto;
import ru.binarysimple.billng.model.Account;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {OperationMapper.class})
public interface AccountMapper {
    Account toEntity(AccountFullDto accountFullDto);

    @AfterMapping
    default void linkOperations(@MappingTarget Account account) {
        account.getOperations().forEach(operation -> operation.setAccount(account));
    }

    AccountFullDto toAccountFullDto(Account account);

    Account toEntity(AccountInfoDto accountInfoDto);

    AccountInfoDto toAccountInfoDto(Account account);

    Account toEntity(AccountCreateDto accountCreateDto);

    AccountCreateDto toAccountCreateDto(Account account);

    Account updateWithNull(AccountFullDto accountFullDto, @MappingTarget Account account);
}