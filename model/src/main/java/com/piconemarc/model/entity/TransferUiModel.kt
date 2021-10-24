package com.piconemarc.model.entity

data class TransferUiModel(
    override val id: Long,
    override val name: String,
    val senderAccount: AccountModel = AccountModel(),
    val senderOperation: OperationModel = OperationModel(),
    val beneficiaryAccount: AccountModel = AccountModel(),
    val beneficiaryOperation: OperationModel = OperationModel(),
    val isRecurrent: Boolean = false,
    val endDate: EndDate = EndDate()
) : BaseUiModel() {
}