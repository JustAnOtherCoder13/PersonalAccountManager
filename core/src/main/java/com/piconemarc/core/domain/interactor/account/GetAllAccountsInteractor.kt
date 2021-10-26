package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.Constants
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.model.entity.AccountUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllAccountsInteractor @Inject constructor(private val accountRepository: AccountRepository) :
Constants.Interactor{

    fun getAllAccountsAsFlow(): Flow<List<AccountUiModel>> = accountRepository.getAllAccountsAsFlow().map {
        mapAccountDtoToAccountModel(it)
    }
    suspend fun getAllAccounts(): List<AccountUiModel> {
        return mapAccountDtoToAccountModel(accountRepository.getAllAccounts())

    }
    private fun mapAccountDtoToAccountModel(accountDtoList: List<AccountDTO>): List<AccountUiModel> {
        return accountDtoList.map {
            AccountUiModel(
                id = it.id,
                name = it.name,
                accountBalance = it.accountBalance,
                accountOverdraft = it.accountOverdraft
            )
        }
    }
}