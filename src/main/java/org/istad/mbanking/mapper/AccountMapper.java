package org.istad.mbanking.mapper;

import org.istad.mbanking.domain.Account;
import org.istad.mbanking.domain.User;
import org.istad.mbanking.domain.UserAccount;
import org.istad.mbanking.features.account.dto.AccountCreateRequest;
import org.istad.mbanking.features.account.dto.AccountRenameRequest;
import org.istad.mbanking.features.account.dto.AccountResponse;
import org.istad.mbanking.features.account.dto.UserAccountResponse;
import org.istad.mbanking.features.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring" , uses = {AccountTypeMapper.class , UserMapper.class})
public interface AccountMapper {
    // user account

    @Mapping(source = "account.id", target = "account")
    @Mapping(source = "user.id", target = "user")
    UserAccountResponse toUserAccountResponse(UserAccount account);

    Account fromAccountCreateRequest(AccountCreateRequest request);

    @Mapping(source = "userAccountList" , target = "user", qualifiedByName = "mapUserResponse")
    AccountResponse toAccountResponse(Account account);

    void updateFromAccountRenameRequest(AccountRenameRequest request, @MappingTarget Account account);
}
