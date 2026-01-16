package ru.binarysimple.billng.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.binarysimple.billng.dto.*;
import ru.binarysimple.billng.filter.AccountFilter;

import java.io.IOException;
import java.util.List;

public interface AccountService {
    Page<AccountFullDto> getAll(AccountFilter filter, Pageable pageable);

    AccountFullDto getOne(Long id);

    AccountFullDto getOne(String username);

    AccountInfoDto getOneInfo(String username);

    List<AccountFullDto> getMany(List<Long> ids);

    AccountFullDto create(AccountCreateDto dto);

    OperationDto operate(OperationRequest request, String username);

    AccountFullDto patch(Long id, JsonNode patchNode) throws IOException;

    List<Long> patchMany(List<Long> ids, JsonNode patchNode) throws IOException;

    AccountFullDto delete(Long id);

    void deleteMany(List<Long> ids);
}
