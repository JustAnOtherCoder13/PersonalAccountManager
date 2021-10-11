package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.DataUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllAccountsInteractor @Inject constructor(private val accountRepository: AccountRepository) {

    fun getAllAccounts(): Flow<List<AccountModel>> = accountRepository.getAllAccounts().map {
        mapAccountDtoToAccountModel(it)
    }

    fun getAllAccountToDataUiModelList(): Flow<List<DataUiModel>> =
        accountRepository.getAllAccounts().map {
            mapAccountDtoToDataUiModel(it)
        }

    private fun mapAccountDtoToAccountModel(accountDtoList: List<AccountDTO>): List<AccountModel> {
        return accountDtoList.map {
            AccountModel(
                id = it.id,
                name = it.name,
                accountBalance = it.accountBalance
            )
        }.toMutableList()
    }

    private fun mapAccountDtoToDataUiModel(accountDtoList: List<AccountDTO>): List<DataUiModel> {
        return accountDtoList.map { DataUiModel(it.name, it.id) }
    }

}