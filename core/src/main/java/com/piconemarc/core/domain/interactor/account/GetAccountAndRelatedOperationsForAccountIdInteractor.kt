package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.model.entity.AccountWithRelatedOperationsUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class GetAccountAndRelatedOperationsForAccountIdInteractor @Inject constructor(private val accountRepository: AccountRepository) {

    suspend fun getAccountForIdWithRelatedOperationsAsFlow(accountId: Long, scope: CoroutineScope): StateFlow<AccountWithRelatedOperationsUiModel> {
        return accountRepository.getAccountForIdWithRelatedOperationAsFlow(accountId).map {
            AccountWithRelatedOperationsUiModel(
                account = it.accountDTO.toUiModel(),
                relatedOperations = it.allOperationsForAccount.map {operationDto -> operationDto.toUiModel() }
            )
        }
            .stateIn(scope)
    }

    suspend fun getAccountForIdWithRelatedOperations(accountId: Long): AccountWithRelatedOperationsUiModel {
        return AccountWithRelatedOperationsUiModel(
            account = accountRepository.getAccountForIdWithRelatedOperations(accountId).accountDTO.toUiModel(),
            relatedOperations = accountRepository.getAccountForIdWithRelatedOperations(accountId).allOperationsForAccount.map {
                operationDTO -> operationDTO.toUiModel()
            }
        )


    }
}