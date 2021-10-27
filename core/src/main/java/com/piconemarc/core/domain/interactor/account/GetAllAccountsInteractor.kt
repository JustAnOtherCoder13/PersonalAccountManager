package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.utils.Constants
import com.piconemarc.model.entity.AccountUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetAllAccountsInteractor @Inject constructor(private val accountRepository: AccountRepository) :
Constants.Interactor{

    fun getAllAccountsAsFlow(scope: CoroutineScope): SharedFlow<List<AccountUiModel>> {
        return accountRepository.getAllAccountsAsFlow().map {
                allAccountDto -> allAccountDto.map { it.toUiModel() }
        }.shareIn(scope, SharingStarted.WhileSubscribed())
    }
    suspend fun getAllAccounts(): List<AccountUiModel> {
        return accountRepository.getAllAccounts().map { it.toUiModel() }
    }
}