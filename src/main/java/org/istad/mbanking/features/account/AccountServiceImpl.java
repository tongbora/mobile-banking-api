package org.istad.mbanking.features.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.istad.mbanking.domain.Account;
import org.istad.mbanking.domain.AccountType;
import org.istad.mbanking.domain.User;
import org.istad.mbanking.domain.UserAccount;
import org.istad.mbanking.features.account.dto.AccountCreateRequest;
import org.istad.mbanking.features.account.dto.AccountRenameRequest;
import org.istad.mbanking.features.account.dto.AccountResponse;
import org.istad.mbanking.features.accountType.AccountTypeRepository;
import org.istad.mbanking.features.user.UserRepository;
import org.istad.mbanking.mapper.AccountMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountTypeRepository accountTypeRepository;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    @Override
    public AccountResponse createAccount(AccountCreateRequest request) {
        // check account type
        AccountType accountType = accountTypeRepository.findByAlias(request.accountTypeAlias()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Account Type.")
        );
        User user = userRepository.findByUuid(request.userUuid()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid User Uuid.")
        );

        Account account = accountMapper.fromAccountCreateRequest(request);
        account.setAccountType(accountType);
        account.setActName(user.getName());
        account.setIsHidden(false);
        // set account number
        String actNo;
        do {
            actNo = String.format("%09d", new Random().nextInt(1_000_000_000));
        } while (accountRepository.existsByActNo(actNo));
        account.setActNo(actNo);

        account.setTransferLimit(BigDecimal.valueOf(5000));
        account.setIsHidden(false);

        UserAccount userAccount = new UserAccount();
        userAccount.setUser(user);
        userAccount.setAccount(account);
        userAccount.setIsDeleted(false);
        userAccount.setIsBlocked(false);
        userAccount.setCreatedAt(LocalDateTime.now());
        userAccountRepository.save(userAccount);

//        account.setUserAccountList(List.of(userAccount));
//        accountRepository.save(account);
//        log.info("Account created: {}", account);
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public AccountResponse findAccountByAccountNo(String accountNo) {
        Account account = accountRepository.findByActNo(accountNo).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.")
        );
        if(account.getIsHidden().equals(true)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account is hidden.");
        }
        return accountMapper.toAccountResponse(account);
    }

//    @Override
//    public List<AccountResponse> findAll() {
//        return accountRepository.findAll().stream()
//                .filter(account -> account.getIsHidden().equals(false))
//                .map(accountMapper::toAccountResponse)
//                .toList();
//    }

    public AccountResponse renameAccount(String actNo, AccountRenameRequest newName) {
        Account account = accountRepository.findByActNo(actNo).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.")
        );

        if(account.getAlias().equals(newName.newName()) && account.getAlias() != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account name is already exists.");
        }
//        accountMapper.updateFromAccountRenameRequest(newName, account);
        account.setAlias(newName.newName());
        account = accountRepository.save(account);
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public void hideAccount(String actNo) {
        Account account = accountRepository.findByActNo(actNo).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.")
        );
        account.setIsHidden(true);
        accountRepository.save(account);
    }
    @Override
    public void unhideAccount(String actNo) {
        Account account = accountRepository.findByActNo(actNo).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.")
        );
        account.setIsHidden(false);
        accountRepository.save(account);
    }

    public Page<AccountResponse> findAll(Integer page, Integer size) {
        if(page < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page number must be greater than 0.");
        }
        if (size < 1 ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Size number must be greater than 0.");
        }

        Sort sortByActName = Sort.by(Sort.Direction.ASC, "actName");
        PageRequest pageRequest = PageRequest.of(page, size, sortByActName);
        Page<Account> accounts = accountRepository.findAll(pageRequest);
        return accounts.map(
                accountMapper::toAccountResponse
        );

    }
}
