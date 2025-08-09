package org.istad.mbanking.features.account;

import org.istad.mbanking.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByActNo(String accountNo);
    Optional<Account> findByActNo(String accountNo);
}
