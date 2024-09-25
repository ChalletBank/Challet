package com.challet.bankservice.global.client;

import com.challet.bankservice.domain.dto.response.AccountInfoResponseListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "Sh-bank")
public interface ShBankFeignClient {

    @PostMapping("/api/sh-bank/mydata-connect")
    AccountInfoResponseListDTO connectMyDataKbBank(
        @RequestHeader(value = "Authorization") String tokenHeader);

    @GetMapping("/api/sh-bank")
    AccountInfoResponseListDTO getMyDataKbBank(
        @RequestHeader(value = "Authorization", required = false) String tokenHeader);
}