package org.istad.mbanking.features.accountType;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.istad.mbanking.features.accountType.dto.AccountTypeResponse;
import org.istad.mbanking.mapper.AccountTypeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountTypeServiceImpl implements AccountTypeService {
    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;

    @Override
    public List<AccountTypeResponse> findAll() {
        return accountTypeRepository.findAll().stream()
                .map(accountTypeMapper::toAccountTypeResponse)
                .toList();
    }

    @Override
    public AccountTypeResponse findByAlias(String alias) {
        return accountTypeMapper.toAccountTypeResponse(
                accountTypeRepository.findByAlias(alias).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Account Type not found")
                )
        );
    }
}
