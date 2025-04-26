package com.es.digitalwallet.domain.entity;

import com.es.digitalwallet.domain.enums.OppositePartyType;
import com.es.digitalwallet.domain.enums.TransactionStatus;
import com.es.digitalwallet.domain.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "transaction")
@Getter
public class Transaction  extends BaseEntity{

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "opposite_party_type", nullable = false)
    private OppositePartyType oppositePartyType;

    @Column(name = "opposite_party", nullable = false)
    private String oppositeParty;

    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    public static Transaction of(Wallet wallet, long amount, TransactionType type, OppositePartyType oppositePartyType, String oppositeParty) {
        Transaction transaction = new Transaction();
        transaction.wallet = wallet;
        transaction.amount = amount;
        transaction.type = type;
        transaction.oppositePartyType = oppositePartyType;
        transaction.oppositeParty = oppositeParty;
        transaction.status = (amount > 100000 && type == TransactionType.DEPOSIT) ? TransactionStatus.PENDING : TransactionStatus.APPROVED;
        return transaction;
    }
}
