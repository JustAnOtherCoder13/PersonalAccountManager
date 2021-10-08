package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.model.entity.AccountModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllAccountsInteractor @Inject constructor(private val accountRepository: AccountRepository) {

    fun getAllAccounts(): Flow<List<AccountModel>> = accountRepository.getAllAccounts().map {
        mapAccountDtoToAccountModel(it)
    }

    fun getAllAccountToStringList(): Flow<List<String>> = accountRepository.getAllAccounts().map {
        mapAccountDtoToString(it)
    }

    private fun mapAccountDtoToAccountModel(accountDtoList: List<AccountDTO>): List<AccountModel> {
        val accountModelList = mutableListOf<AccountModel>()
        accountDtoList.forEachIndexed { index, accountDTO ->
            accountModelList.add(
                index = index,
                element = AccountModel(
                    id = accountDTO.id,
                    name = accountDTO.name,
                    accountBalance = accountDTO.accountBalance
                )
            )
        }
        return accountModelList
    }

    private fun mapAccountDtoToString(accountDtoList: List<AccountDTO>): List<String> {
        val accountStringList = mutableListOf<String>()
        accountDtoList.forEach {
            accountStringList.add(it.name)
        }
        return accountStringList
    }

}