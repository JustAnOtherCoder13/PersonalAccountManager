package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.model.entity.AccountModel

class AddNewAccountInteractor(private val accountRepository: AccountRepository) : BaseAccountInteractor(
    accountRepository
) {

    suspend fun addNewAccount(accountModel: AccountModel){
        accountRepository.addNewAccount(AccountDTO(
            name = accountModel.name,
            accountBalance = accountModel.accountBalance
        ))
    }

}