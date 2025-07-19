package org.istad.mbanking.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String uuid = UUID.randomUUID().toString();

    @Column(unique = true, nullable = false)
    private String nationalCardId;

    @Column(nullable = false)
    private Integer pin;

    @Column(nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    private String profileImage;

    @Column(length = 10, nullable = false)
    private String gender;

    private LocalDate dob;

    @Column(length = 100)
    private String cityOrProvince;

    @Column(length = 100)
    private String khanOrDistrict;

    @Column(length = 100)
    private String village;

    @Column(length = 100)
    private String street;

    @Column(length=100)
    private String employeeType;

    @Column(length = 100)
    private String position;

    @Column(length = 100)
    private String companyName;

    @Column(length = 100)
    private String mainSourceOfIncome;

    @Column(length = 100)
    private BigDecimal monthlyIncomeRance;

    @Column(nullable = false , unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String onSignalId;

    private Boolean isDeleted = false;
    private Boolean isBlocked = false;
    private Boolean isStudent;

    @Column(unique = true, nullable = false)
    private String studentCardId;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id" , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<UserAccount> userAccountList;

    private LocalDateTime createdAt;
}
