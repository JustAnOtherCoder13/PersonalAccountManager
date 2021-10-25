package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.model.entity.AccountUiModel
import javax.inject.Inject

class AddNewAccountInteractor @Inject constructor(private val accountRepository: AccountRepository) {

    suspend fun addNewAccount(accountUiModel: AccountUiModel) {
        accountRepository.addNewAccount(
            AccountDTO(
                name = accountUiModel.name,
                accountBalance = accountUiModel.accountBalance,
                accountOverdraft = accountUiModel.accountOverdraft
            )
        )
    }

}