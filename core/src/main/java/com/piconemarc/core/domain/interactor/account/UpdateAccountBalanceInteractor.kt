package com.piconemarc.core.domain.interactor.account

import android.util.Log
import com.piconemarc.core.data.account.AccountRepository
import javax.inject.Inject

class UpdateAccountBalanceInteractor @Inject constructor(val accountRepository: AccountRepository) {

    suspend fun updateAccountBalanceOnDeleteOperation(
        accountId: Long,
        oldAccountBalance: Double,
        deletedOperationAMount: Double
    ) {
        Log.e("TAG", "updateAccountBalanceOnDeleteOperation: " +
                accountId + " "+oldAccountBalance + (deletedOperationAMount * -1), )
        accountRepository.updateAccountBalance(
            accountId = accountId,
            accountBalance = oldAccountBalance + (deletedOperationAMount * -1)
        )
    }

    suspend fun updateAccountBalanceOnAddOperation(
        accountId: Long,
        oldAccountBalance: Double,
        addedOperationAMount: Double
    ) {
        accountRepository.updateAccountBalance(
            accountId = accountId,
            accountBalance = oldAccountBalance + addedOperationAMount
        )
    }
}