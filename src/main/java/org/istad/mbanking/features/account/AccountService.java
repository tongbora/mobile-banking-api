package org.istad.mbanking.features.account;

import org.istad.mbanking.features.account.dto.AccountCreateRequest;
import org.istad.mbanking.features.account.dto.AccountRenameRequest;
import org.istad.mbanking.features.account.dto.AccountResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(AccountCreateRequest request);
    AccountResponse findAccountByAccountNo(String accountNo);
    Page<AccountResponse> findAll(Integer page, Integer size);
    AccountResponse renameAccount(String actNo, AccountRenameRequest newName);

    void hideAccount(String actNo);
    void unhideAccount(String actNo);
}
