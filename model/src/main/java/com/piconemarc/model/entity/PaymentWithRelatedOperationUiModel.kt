package com.piconemarc.model.entity

data class PaymentWithRelatedOperationUiModel(
val payment: PaymentUiModel,
val relatedOperation : OperationUiModel
)
