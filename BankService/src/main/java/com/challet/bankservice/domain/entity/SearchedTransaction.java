package com.challet.bankservice.domain.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Document(indexName = "ch_bank_transaction", createIndex = false)
@Schema(description = "챌렛은행 거래내역 검색")
@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchedTransaction(

    @Id
    @Schema(description = "거래내역 ID")
    String transactionId,

    @Field(type = FieldType.Keyword)
    @Schema(description = "계좌 ID")
    Long accountId,

    @Field(type = FieldType.Date, format = DateFormat.date_time, pattern = "uuuu-MM-dd'T'HH:mm:ss")
    @Schema(description = "거래 날짜 시간")
    Date transactionDate,

    @Field(type = FieldType.Text)
    @Schema(description = "입금처")
    String deposit,

    @Field(type = FieldType.Text)
    @Schema(description = "출금처")
    String withdrawal,

    @Field(type = FieldType.Long)
    @Schema(description = "거래 후 잔액")
    Long transactionBalance,

    @Field(type = FieldType.Long)
    @Schema(description = "거래 금액")
    Long transactionAmount
) {

    public static SearchedTransaction fromAccountIdAndChalletBankTransaction(final Long accountId, final ChalletBankTransaction transaction) {
        return SearchedTransaction.builder()
            .transactionId(String.valueOf(transaction.getId()))
            .accountId(accountId)
            .transactionDate(convertToDate(transaction.getTransactionDatetime()))
            .deposit(transaction.getDeposit())
            .withdrawal(transaction.getWithdrawal())
            .transactionBalance(transaction.getTransactionBalance())
            .transactionAmount(transaction.getTransactionAmount())
            .build();
    }

    public static Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static SearchedTransaction fromAccountTransferByFrom(ChalletBankTransaction fromTransaction) {
        return SearchedTransaction.builder()
            .transactionId(String.valueOf(fromTransaction.getId()))
            .accountId(fromTransaction.getChalletBank().getId())
            .transactionDate(convertToDate(fromTransaction.getTransactionDatetime()))
            .deposit(fromTransaction.getDeposit())
            .withdrawal(fromTransaction.getWithdrawal())
            .transactionBalance(fromTransaction.getTransactionBalance())
            .transactionAmount(fromTransaction.getTransactionAmount())
            .build();
    }

    public static SearchedTransaction fromAccountTransferByTo(ChalletBankTransaction toTransaction) {
        return SearchedTransaction.builder()
            .transactionId(String.valueOf(toTransaction.getId()))
            .accountId(toTransaction.getChalletBank().getId())
            .transactionDate(convertToDate(toTransaction.getTransactionDatetime()))
            .deposit(toTransaction.getDeposit())
            .withdrawal(toTransaction.getWithdrawal())
            .transactionBalance(toTransaction.getTransactionBalance())
            .transactionAmount(toTransaction.getTransactionAmount())
            .build();
    }
}