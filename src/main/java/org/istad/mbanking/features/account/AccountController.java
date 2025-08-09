package org.istad.mbanking.features.account;

import lombok.RequiredArgsConstructor;
import org.istad.mbanking.features.account.dto.AccountCreateRequest;
import org.istad.mbanking.features.account.dto.AccountRenameRequest;
import org.istad.mbanking.features.account.dto.AccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@RequestBody AccountCreateRequest request) {
       return accountService.createAccount(request);
    }

    @GetMapping("/{accountNo}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse findAccountByAccountNo(@PathVariable String accountNo) {
        return accountService.findAccountByAccountNo(accountNo);
    }

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<AccountResponse> findAllAccount() {
//        return accountService.findAll();
//    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<AccountResponse> findAllAccount(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                @RequestParam(required = false, defaultValue = "2") Integer size) {
        return accountService.findAll(page, size);
    }

    @PutMapping("/{actNo}/rename")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse renameAccount(@PathVariable String actNo, @RequestBody AccountRenameRequest request){
        return accountService.renameAccount(actNo, request);
    }

    @PutMapping("/{actNo}/unhide")
    @ResponseStatus(HttpStatus.OK)
    public void unhideAccount(@PathVariable String actNo){
        accountService.unhideAccount(actNo);
    }

    @PutMapping("/{actNo}/hide")
    @ResponseStatus(HttpStatus.OK)
    public void hideAccount(@PathVariable String actNo){
        accountService.hideAccount(actNo);
    }
}
