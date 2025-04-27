package com.es.digitalwallet.model.request;

import com.es.digitalwallet.domain.enums.OppositePartyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawFromWalletRequest {
    private long amount;
    private OppositePartyType oppositeParty;
    private String destination;
}
