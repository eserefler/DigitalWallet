package com.es.digitalwallet.model.request;

import com.es.digitalwallet.domain.enums.OppositePartyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositToWalletRequest {
    private long amount;
    private OppositePartyType oppositeParty;
    private String source;
}
