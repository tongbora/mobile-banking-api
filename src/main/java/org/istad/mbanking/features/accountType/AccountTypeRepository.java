package org.istad.mbanking.features.accountType;

import org.istad.mbanking.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {


    Optional<AccountType> findByAlias(String alias);
}
