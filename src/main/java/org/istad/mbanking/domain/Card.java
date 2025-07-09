package org.istad.mbanking.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    private String cvv;

    private String holder;

    private LocalDate issuedAt;
    private LocalDate expiredAt;

    private Boolean isDeleted;

    @ManyToOne
    private CardType cardType;

    @OneToOne(mappedBy = "card")
    private Account account;
}
