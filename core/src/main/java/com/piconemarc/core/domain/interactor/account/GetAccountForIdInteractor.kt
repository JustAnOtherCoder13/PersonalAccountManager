package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.model.entity.AccountUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAccountForIdInteractor @Inject constructor(private val accountRepository: AccountRepository) {

    suspend fun getAccountForId(id:Long) : AccountUiModel{
       return mapAccountModelToPresentationDataModel(accountRepository.getAccountForId(id))
    }

    fun getAccountForIdFlow(id : Long) : Flow<AccountUiModel>{
        return accountRepository.getAccountForIdFlow(id).map {
            mapAccountModelToPresentationDataModel(it)
        }
    }

    private fun mapAccountModelToPresentationDataModel(accountDTO: AccountDTO):AccountUiModel{
        return AccountUiModel(
            id = accountDTO.id,
            name = accountDTO.name,
            accountBalance = accountDTO.accountBalance,
            accountOverdraft = accountDTO.accountOverdraft
        )
    }

}