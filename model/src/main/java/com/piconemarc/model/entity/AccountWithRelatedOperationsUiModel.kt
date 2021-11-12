package com.piconemarc.model.entity

data class AccountWithRelatedOperationsUiModel (
    val account: AccountUiModel,
    val relatedOperations : List<OperationUiModel>
        )