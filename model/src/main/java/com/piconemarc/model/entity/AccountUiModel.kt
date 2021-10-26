package com.piconemarc.model.entity

data class AccountUiModel(
    override val id: Long = 0,
    override val name: String = "",
    var accountBalance: Double = 0.0,
    val accountOverdraft: Double = 0.0
) : BaseUiModel() {

    val rest: Double = accountOverdraft + accountBalance

    fun updateAccountBalance(operation : OperationUiModel):AccountUiModel{
        return this.copy(accountBalance = this.accountBalance+operation.amount)
    }
}
