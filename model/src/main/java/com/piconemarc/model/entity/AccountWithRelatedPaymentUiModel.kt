package com.piconemarc.model.entity

data class AccountWithRelatedPaymentUiModel(
    val account: AccountUiModel,
    val relatedPayment : List<PaymentUiModel>
)