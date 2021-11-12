package com.piconemarc.core.domain.entityDTO

import androidx.room.Embedded
import androidx.room.Relation

data class AccountWithRelatedOperations (
    @Embedded
    val accountDTO: AccountDTO,
    @Relation(
        parentColumn = "id",
        entityColumn = "accountId",
        entity = OperationDTO::class
    )
    val allOperationsForAccount: List<OperationDTO>
)