package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.Constants
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.model.entity.AccountModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllAccountsInteractor @Inject constructor(private val accountRepository: AccountRepository) :
Constants.Interactor{

    fun getAllAccounts(): Flow<List<AccountModel>> = accountRepository.getAllAccounts().map {
        mapAccountDtoToAccountModel(it)
    }
    private fun mapAccountDtoToAccountModel(accountDtoList: List<AccountDTO>): List<AccountModel> {
        return accountDtoList.map {
            AccountModel(
                id = it.id,
                name = it.name,
                accountBalance = it.accountBalance,
                accountOverdraft = it.accountOverdraft
            )
        }
    }
}