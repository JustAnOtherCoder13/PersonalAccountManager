package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.model.entity.AccountUiModel
import javax.inject.Inject

class GetAccountForIdInteractor @Inject constructor(private val accountRepository: AccountRepository) {

    suspend fun getAccountForId(id: Long): AccountUiModel {
        return accountRepository.getAccountForId(id).toUiModel()
    }
}