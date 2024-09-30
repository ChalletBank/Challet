package com.challet.bankservice.global.client;

import com.challet.bankservice.domain.dto.request.BankTransferRequestDTO;
import com.challet.bankservice.domain.dto.request.MonthlyTransactionRequestDTO;
import com.challet.bankservice.domain.dto.response.AccountInfoResponseListDTO;
import com.challet.bankservice.domain.dto.response.BankTransferResponseDTO;
import com.challet.bankservice.domain.dto.response.MonthlyTransactionHistoryListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "nh-bank")
public interface NhBankFeignClient {

    @PostMapping("/api/nh-bank/mydata-connect")
    AccountInfoResponseListDTO connectMyDataKbBank(
        @RequestHeader(value = "Authorization") String tokenHeader);

    @GetMapping("/api/nh-bank")
    AccountInfoResponseListDTO getMyDataKbBank(
        @RequestHeader(value = "Authorization", required = false) String tokenHeader);

    @PostMapping("/api/nh-bank/account-transfers")
    BankTransferRequestDTO getTransferAccount(BankTransferResponseDTO responseDTO);

    @PostMapping("/api/nh-bank/transactions-monthly")
    MonthlyTransactionHistoryListDTO getMonthlyTransactionHistory(
        @RequestHeader(value = "Authorization", required = false) String tokenHeader,
        @RequestBody MonthlyTransactionRequestDTO requestDTO);
}
