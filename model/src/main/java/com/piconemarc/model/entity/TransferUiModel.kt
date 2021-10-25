package com.piconemarc.model.entity

data class TransferUiModel(
    override val id: Long = 0,
    override val name: String = "",
    val senderOperationId: Long=0,
    val beneficiaryOperationId: Long=0,
    val paymentId : Long? = null
) : BaseUiModel() {

}