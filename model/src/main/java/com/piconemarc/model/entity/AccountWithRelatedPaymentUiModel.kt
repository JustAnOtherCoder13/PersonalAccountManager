package com.piconemarc.model.entity

data class AccountWithRelatedPaymentUiModel(
    val accountUiModel: AccountUiModel,
    val relatedPayment : List<PaymentUiModel>
)