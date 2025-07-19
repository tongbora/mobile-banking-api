package org.istad.mbanking.features.user;

import jakarta.transaction.Transactional;
import org.istad.mbanking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByNationalCardId(String nationalCardId);

    boolean existsByStudentCardId(String studentCardId);

    Optional<User> findByUuid(String uuid);

    boolean existsByUuid(String uuid);

    @Transactional
    void deleteUserByUuid(String uuid);

    @Transactional
    @Modifying
    @Query("UPDATE User As u SET u.isDeleted = TRUE WHERE u.uuid = :uuid")
    void disableUserByUuid(String uuid);

    @Transactional
    @Modifying
    @Query("UPDATE User As u SET u.isDeleted = FALSE WHERE u.uuid = :uuid")
    void enableUserByUuid(String uuid);

    @Modifying
    @Transactional
    @Query("UPDATE User AS u SET u.isBlocked = TRUE WHERE u.uuid = ?1 ")
    void blockByUuid(String uuid);
}
