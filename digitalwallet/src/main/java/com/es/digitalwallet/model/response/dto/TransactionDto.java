package com.es.digitalwallet.model.response.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TransactionDto {
    private UUID Id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String Type;
    private String oppositePartyType;
    private String oppositeParty;
    private long amount;
    private String currency;
    private String status;
}