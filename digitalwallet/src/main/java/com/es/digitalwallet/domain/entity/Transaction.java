package com.es.digitalwallet.domain.entity;

import com.es.digitalwallet.constants.TransactionSettings;
import com.es.digitalwallet.domain.enums.OppositePartyType;
import com.es.digitalwallet.domain.enums.TransactionStatus;
import com.es.digitalwallet.domain.enums.TransactionType;
import com.es.digitalwallet.exception.TransactionCantBeApprovedOrDeniedException;
import jakarta.persistence.*;
import lombok.Getter;

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
        transaction.status = (amount > TransactionSettings.MAX_TRANSACTION_AMOUNT) ? TransactionStatus.PENDING : TransactionStatus.APPROVED;
        return transaction;
    }

    public void setStatus(boolean isApproved) {
        if (this.isPending()) {
           status = isApproved ? TransactionStatus.APPROVED : TransactionStatus.DENIED;
           return;
        }

        throw new TransactionCantBeApprovedOrDeniedException();
    }

    public boolean isApproved() {
        return this.status == TransactionStatus.APPROVED;
    }

    public boolean isPending() {return this.status == TransactionStatus.PENDING;}
    public boolean isDepositTransaction() {return this.type == TransactionType.DEPOSIT;}
    public boolean isWithdrawTransaction() {return this.type == TransactionType.WITHDRAW;}
}
