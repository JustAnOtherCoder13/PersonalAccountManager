package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.model.entity.AccountUiModel
import javax.inject.Inject

class UpdateAccountBalanceInteractor @Inject constructor(val accountRepository: AccountRepository) {

    suspend fun updateAccountBalanceOnDeleteOperation(
        accountId: Long,
        oldAccountBalance: Double,
        deletedOperationAMount: Double
    ) {
        accountRepository.updateAccountBalance(
            accountId = accountId,
            accountBalance = oldAccountBalance - (deletedOperationAMount)
        )
    }

    suspend fun updateAccountBalance(
        updatedAccountUi: AccountUiModel
    ) {
        accountRepository.updateAccountBalance(
            accountId = updatedAccountUi.id,
            accountBalance = updatedAccountUi.accountBalance
        )
    }
}