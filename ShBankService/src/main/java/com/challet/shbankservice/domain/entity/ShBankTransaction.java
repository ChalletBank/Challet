package com.challet.shbankservice.domain.entity;

import com.challet.shbankservice.domain.dto.request.AccountTransferRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sh_transaction")
public class ShBankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sh_bank_id", nullable = false)
    private ShBank shBank;

    @Column(name = "transaction_amount", nullable = false)
    private Long transactionAmount;

    @Column(name = "transaction_datetime", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime transactionDatetime;

    @Column(name = "deposit", nullable = false)
    private String deposit;

    @Column(name = "withdrawal", nullable = false)
    private String withdrawal;

    @Column(name = "transaction_balance", nullable = false)
    private Long transactionBalance;

    @Enumerated(EnumType.STRING)
    private Category category;

    public void assignTransactionNhAccount(ShBank shBank) {
        this.shBank = shBank;
    }

    public static ShBankTransaction createAccountTransferHistory(ShBank shBank,
        AccountTransferRequestDTO requestDTO, long accountTransactionBalance) {

        return ShBankTransaction
            .builder()
            .transactionAmount(requestDTO.amount())
            .transactionDatetime(LocalDateTime.now())
            .deposit(shBank.getAccountNumber())
            .withdrawal(requestDTO.name())
            .transactionBalance(accountTransactionBalance)
            .build();
    }
}