package com.piconemarc.model.entity

data class AccountModel(
    override val id: Long = 0,
    override val name: String = "",
    var accountBalance: Double = 0.0,
    val accountOverdraft: Double = 0.0
) : BaseUiModel() {

    val rest: Double = accountOverdraft + accountBalance

    fun minusOperation(operationAmount: Double) {
        this.accountBalance -= operationAmount
    }

    fun addOperation(operationAmount: Double) {
        this.accountBalance += operationAmount
    }
}
