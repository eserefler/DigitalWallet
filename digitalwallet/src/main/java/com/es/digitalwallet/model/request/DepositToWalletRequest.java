package com.es.digitalwallet.model.request;

import com.es.digitalwallet.domain.enums.OppositePartyType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositToWalletRequest {
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be a positive value")
    private long amount;
    private OppositePartyType oppositeParty;
    private String source;
}
