package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.model.entity.AccountModel
import javax.inject.Inject

class AddNewAccountInteractor @Inject constructor(private val accountRepository: AccountRepository) {

    suspend fun addNewAccount(accountModel: AccountModel) {
        accountRepository.addNewAccount(
            AccountDTO(
                name = accountModel.name,
                accountBalance = accountModel.accountBalance
            )
        )
    }

}