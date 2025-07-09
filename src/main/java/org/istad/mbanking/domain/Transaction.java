package org.istad.mbanking.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Account owner;
    @ManyToOne
    private Account transferReceiver;

    private String paymentReceiver;

    private BigDecimal amount;

    private String remarks;

    @Column(length = 100 , nullable = false)
    private String transactionType;

    private Boolean status;

    private Boolean isPayment;

    private LocalDateTime transactionAt;

}
