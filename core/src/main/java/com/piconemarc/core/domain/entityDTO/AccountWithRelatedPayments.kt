package com.piconemarc.core.domain.entityDTO

import androidx.room.Embedded
import androidx.room.Relation

data class AccountWithRelatedPayments(
    @Embedded
    val accountDTO: AccountDTO,
    @Relation(
        parentColumn = "id",
        entityColumn = "accountId",
        entity = PaymentDTO::class
    )
    val allPaymentsForAccount: List<PaymentWithRelatedOperation>
)


