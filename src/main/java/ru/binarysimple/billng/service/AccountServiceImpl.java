package ru.binarysimple.billng.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.binarysimple.billng.dto.*;
import ru.binarysimple.billng.exception.BadRequestException;
import ru.binarysimple.billng.exception.EntityNotFoundException;
import ru.binarysimple.billng.exception.InsufficientFundsException;
import ru.binarysimple.billng.filter.AccountFilter;
import ru.binarysimple.billng.kafka.UserEvent;
import ru.binarysimple.billng.mapper.AccountMapper;
import ru.binarysimple.billng.mapper.OperationMapper;
import ru.binarysimple.billng.model.Account;
import ru.binarysimple.billng.model.Operation;
import ru.binarysimple.billng.repository.AccountRepository;
import ru.binarysimple.billng.repository.OperationRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    private final OperationMapper operationMapper;

    private final AccountRepository accountRepository;

    private final OperationRepository operationRepository;

    private final ObjectMapper objectMapper;

    @Override
    public Page<AccountFullDto> getAll(AccountFilter filter, Pageable pageable) {
        Specification<Account> spec = filter.toSpecification();
        Page<Account> accounts = accountRepository.findAll(spec, pageable);
        return accounts.map(accountMapper::toAccountFullDto);
    }

    @Override
    public AccountFullDto getOne(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        return accountMapper.toAccountFullDto(accountOptional.orElseThrow(() ->
                new EntityNotFoundException("Entity with id `%s` not found".formatted(id))));
    }

    @Override
    public AccountFullDto getOne(String username) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        return accountMapper.toAccountFullDto(accountOptional.orElseThrow(() ->
                new EntityNotFoundException("Entity with username `%s` not found".formatted(username))));
    }

    @Override
    public AccountInfoDto getOneInfo(String username) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        return accountMapper.toAccountInfoDto(accountOptional.orElseThrow(() ->
                new EntityNotFoundException("Entity with username `%s` not found".formatted(username))));
    }

    @Override
    public List<AccountFullDto> getMany(List<Long> ids) {
        List<Account> accounts = accountRepository.findAllById(ids);
        return accounts.stream()
                .map(accountMapper::toAccountFullDto)
                .toList();
    }

    @Override
    public AccountFullDto create(AccountCreateDto dto) {
        if (accountRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new BadRequestException("Account with username `%s` already exists".formatted(dto.getUsername()));
        }
        Account account = accountMapper.toEntity(dto);
        Account resultAccount = accountRepository.save(account);
        return accountMapper.toAccountFullDto(resultAccount);
    }

    @Override
    public AccountFullDto patch(Long id, JsonNode patchNode) throws IOException {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Entity with id `%s` not found".formatted(id)));

        AccountFullDto accountFullDto = accountMapper.toAccountFullDto(account);
        objectMapper.readerForUpdating(accountFullDto).readValue(patchNode);
        accountMapper.updateWithNull(accountFullDto, account);

        Account resultAccount = accountRepository.save(account);
        return accountMapper.toAccountFullDto(resultAccount);
    }

    @Override
    public List<Long> patchMany(List<Long> ids, JsonNode patchNode) throws IOException {
        Collection<Account> accounts = accountRepository.findAllById(ids);

        for (Account account : accounts) {
            AccountFullDto accountFullDto = accountMapper.toAccountFullDto(account);
            objectMapper.readerForUpdating(accountFullDto).readValue(patchNode);
            accountMapper.updateWithNull(accountFullDto, account);
        }

        List<Account> resultAccounts = accountRepository.saveAll(accounts);
        return resultAccounts.stream()
                .map(Account::getId)
                .toList();
    }

    @Override
    public AccountFullDto delete(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null) {
            accountRepository.delete(account);
        }
        return accountMapper.toAccountFullDto(account);
    }

    @Override
    public void deleteMany(List<Long> ids) {
        accountRepository.deleteAllById(ids);
    }

    @Override
    public OperationDto operate(OperationRequest request, String username) {

        Account account = accountRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Entity with username `%s` not found".formatted(username)));

        Operation operation = operationMapper.toEntity(request);

        operation.setAccount(account);

        switch (request.getType()) {
            case DEPOSIT -> {
                account.setBalance(account.getBalance().add(request.getAmount()));
            }
            case WITHDRAW, PAYMENT -> {

                if (account.getBalance().compareTo(request.getAmount()) < 0) {
                    throw new InsufficientFundsException("Need more money");
                }

                account.setBalance(account.getBalance().subtract(request.getAmount()));
            }
        }

        Operation resultOperation = operationRepository.save(operation);

        accountRepository.save(account);

        return operationMapper.toOperationDto(resultOperation);
    }

    @Override
    public void processUserEvent(UserEvent event) {
        AccountCreateDto accountCreateDto = accountMapper.toAccountCreateDto(event);

        create(accountCreateDto);

    }
}
