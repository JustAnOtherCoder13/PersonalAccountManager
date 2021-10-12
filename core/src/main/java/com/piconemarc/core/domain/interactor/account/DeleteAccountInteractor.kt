package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.model.entity.AccountModel

class DeleteAccountInteractor(private val accountRepository: AccountRepository) : BaseAccountInteractor(
    accountRepository
) {
    suspend fun deleteAccount(accountModel: AccountModel){
        accountRepository.deleteAccount(
            AccountDTO(
                id = accountModel.id,
                name = accountModel.name,
                accountBalance = accountModel.accountBalance
            )
        )
    }
}