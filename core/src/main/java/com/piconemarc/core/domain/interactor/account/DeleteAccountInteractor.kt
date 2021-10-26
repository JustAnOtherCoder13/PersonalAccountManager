package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.model.entity.AccountUiModel
import javax.inject.Inject

class DeleteAccountInteractor @Inject constructor(private val accountRepository: AccountRepository) {

    suspend fun deleteAccount(accountUiModel: AccountUiModel) {
        accountRepository.deleteAccount(
            AccountDTO().fromUiModel(accountUiModel)
        )
    }
}