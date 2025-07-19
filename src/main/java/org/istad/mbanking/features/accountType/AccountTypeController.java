package org.istad.mbanking.features.accountType;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/account-types")
@RequiredArgsConstructor
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    @GetMapping
    public ResponseEntity<Map<String , Object>> getAllAccountTypes() {
        return ResponseEntity.ok(
                Map.of(
                        "message", "Get All Account Types Successfully",
                        "accountTypes", accountTypeService.findAll()
                )
        );
    }

    @GetMapping("/{alias}")
    public ResponseEntity<Map<String , Object>> getAccountTypeByAlias(@PathVariable String alias) {
        return ResponseEntity.ok(
                Map.of(
                        "message", "Get Account Type Successfully",
                        "accountType", accountTypeService.findByAlias(alias)
                )
        );
    }

}
