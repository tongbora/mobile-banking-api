package org.istad.mbanking.features.accountType;

import org.istad.mbanking.domain.AccountType;
import org.istad.mbanking.features.accountType.dto.AccountTypeResponse;

import java.util.List;

public interface AccountTypeService {

    List<AccountTypeResponse> findAll();

    AccountTypeResponse findByAlias(String alias);
}
