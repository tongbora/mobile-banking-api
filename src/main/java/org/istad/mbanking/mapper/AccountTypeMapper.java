package org.istad.mbanking.mapper;

import org.istad.mbanking.domain.AccountType;
import org.istad.mbanking.features.accountType.dto.AccountTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {

    AccountTypeResponse toAccountTypeResponse(AccountType accountType);
}
