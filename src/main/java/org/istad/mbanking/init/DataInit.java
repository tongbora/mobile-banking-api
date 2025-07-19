package org.istad.mbanking.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.istad.mbanking.domain.AccountType;
import org.istad.mbanking.domain.Role;
import org.istad.mbanking.features.accountType.AccountTypeRepository;
import org.istad.mbanking.features.user.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final RoleRepository roleRepository;
    private final AccountTypeRepository accountTypeRepository;
    @PostConstruct
    public void initRoles() {

        if(roleRepository.count() < 1 ) {
            Role user = new Role();
            user.setName("USER");

            Role admin = new Role();
            admin.setName("ADMIN");

            Role customer = new Role();
            customer.setName("CUSTOMER");

            Role staff = new Role();
            staff.setName("STAFF");

            roleRepository.saveAll(
                    List.of(user, admin, customer, staff)
            );
        }
    }

    @PostConstruct
    public void initAccountTypes() {
        if(accountTypeRepository.count() == 0) {

            AccountType saving = new AccountType();
            saving.setAlias("saving-account");
            saving.setName("Saving Account");
            saving.setDescription("A savings account is a type of bank account designed for storing money and earning interest.");
            saving.setIsDeleted(false);

//        AccountType checking = new AccountType();
//        checking.setAlias("checking-account");
//        checking.setName("Checking Account");
//        checking.setDescription("A checking account is a type of bank account designed for storing and using money.");

            AccountType creditCard = new AccountType();
            creditCard.setAlias("credit-card");
            creditCard.setName("Credit Card");
            creditCard.setDescription("A credit card is a type of financial instrument that enables you to store and use money.");
            creditCard.setIsDeleted(false);

            AccountType payroll = new AccountType();
            payroll.setAlias("payroll-account");
            payroll.setName("Payroll Account");
            payroll.setDescription("A payroll account is a type of bank account designed for storing and using money.");
            payroll.setIsDeleted(false);

//        AccountType loan = new AccountType();
//        loan.setAlias("loan");
//        loan.setName("Loan");
//        loan.setDescription("A loan is a financial instrument that enables you to store and use money.");


            accountTypeRepository.saveAll(
                    List.of(saving, creditCard, payroll)
            );
        }
    }
}
