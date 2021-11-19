package com.piconemarc.core.domain.entityDTO

import androidx.room.Embedded
import androidx.room.Relation

data class PaymentWithRelatedOperation(
    @Embedded
    val paymentDTO: PaymentDTO,

    @Relation(
        parentColumn = "id",
        entityColumn = "paymentId",
        entity = OperationDTO::class
    )
    val relatedOperation: OperationDTO?
)