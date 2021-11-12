package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.model.entity.AccountUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class GetAccountForIdInteractor @Inject constructor(private val accountRepository: AccountRepository) {

    suspend fun getAccountForId(id:Long) : AccountUiModel{
       return accountRepository.getAccountForId(id).toUiModel()
    }

    suspend fun getAccountForIdFlow(id : Long, scope: CoroutineScope) : StateFlow<AccountUiModel> {
        return accountRepository.getAccountForIdFlow(id).map { it.toUiModel() }.stateIn(scope)
    }
}