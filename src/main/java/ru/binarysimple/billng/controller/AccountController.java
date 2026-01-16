package ru.binarysimple.billng.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.binarysimple.billng.dto.*;
import ru.binarysimple.billng.service.AccountService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/billing/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

//    @GetMapping
//    public PagedModel<AccountFullDto> getAll(@RequestHeader("X-Username") String currentUsername, @ModelAttribute AccountFilter filter, Pageable pageable) {
//        Page<AccountFullDto> accountFullDtos = accountService.getAll(filter, pageable);
//        return new PagedModel<>(accountFullDtos);
//    }

    @GetMapping("/full")
    public AccountFullDto getFull(@RequestHeader("X-Username") String currentUsername) {
        return accountService.getOne(currentUsername);
    }

    @GetMapping("/balance")
    public AccountInfoDto getBalance(@RequestHeader("X-Username") String currentUsername) {
        return accountService.getOneInfo(currentUsername);
    }

    @PostMapping("/new")
    public AccountFullDto create(@RequestHeader("X-Username") String currentUsername, @RequestBody AccountCreateDto dto) {
        if (!currentUsername.equals(dto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        return accountService.create(dto);
    }

    @PostMapping("/operate")
    public OperationDto operate(@RequestHeader("X-Username") String currentUsername, @RequestBody OperationRequest dto) {

        if (dto.getAmount().compareTo(BigDecimal.ZERO) <= 0) { //check for negative {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be positive");
        }

        return accountService.operate(dto, currentUsername);
    }



}
