package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
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

    suspend fun updateAccountBalanceOnAddOperation(
        accountId: Long,
        oldAccountBalance: Double,
        addedOperationAmount: Double
    ) {
        accountRepository.updateAccountBalance(
            accountId = accountId,
            accountBalance = oldAccountBalance + (addedOperationAmount)
        )
    }
}