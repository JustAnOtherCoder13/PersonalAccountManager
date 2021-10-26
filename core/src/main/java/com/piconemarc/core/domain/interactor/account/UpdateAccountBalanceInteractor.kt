package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.model.entity.AccountUiModel
import javax.inject.Inject

class UpdateAccountBalanceInteractor @Inject constructor(val accountRepository: AccountRepository) {

    suspend fun updateAccountBalance(
        vararg updatedAccountUi: AccountUiModel
    ) {
        updatedAccountUi.forEach {
            accountRepository.updateAccountBalance(
                accountId = it.id,
                accountBalance = it.accountBalance
            )
        }
    }
}