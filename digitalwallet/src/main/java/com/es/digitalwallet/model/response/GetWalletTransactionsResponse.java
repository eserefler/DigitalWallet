package com.es.digitalwallet.model.response;

import com.es.digitalwallet.model.response.dto.TransactionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetWalletTransactionsResponse {
    private List<TransactionDto> transactions;
}
