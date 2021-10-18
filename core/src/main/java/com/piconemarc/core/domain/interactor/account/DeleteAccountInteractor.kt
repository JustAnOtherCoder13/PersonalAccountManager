package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.model.entity.AccountModel
import javax.inject.Inject

class DeleteAccountInteractor @Inject constructor(private val accountRepository: AccountRepository) {
    suspend fun deleteAccount(accountModel: AccountModel) {
        accountRepository.deleteAccount(
            AccountDTO(
                id = accountModel.id,
                name = accountModel.name,
                accountBalance = accountModel.accountBalance,
                accountOverdraft = accountModel.accountOverdraft
            )
        )
    }
}