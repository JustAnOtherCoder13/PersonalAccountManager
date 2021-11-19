package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.model.entity.AccountUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class GetAllAccountsInteractor @Inject constructor(private val accountRepository: AccountRepository) {

    suspend fun getAllAccounts(): List<AccountUiModel> {
        return accountRepository.getAllAccounts().map { it.toUiModel() }
    }

    suspend fun getAllAccountsAsFlow(scope: CoroutineScope): StateFlow<List<AccountUiModel>> {
        return accountRepository.getAllAccountsAsFlow().map { allAccountDto ->
            allAccountDto.map { it.toUiModel() }
        }.stateIn(scope)
    }
}