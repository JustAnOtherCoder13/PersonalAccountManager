package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.entityDTO.AccountWithRelatedPayments
import com.piconemarc.core.domain.utils.Constants
import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.model.entity.AccountWithRelatedPaymentUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class GetAllAccountsInteractor @Inject constructor(private val accountRepository: AccountRepository) :
    Constants.Interactor {

    suspend fun getAllAccountsAsFlow(scope: CoroutineScope): StateFlow<List<AccountUiModel>> {
        return accountRepository.getAllAccountsAsFlow().map { allAccountDto ->
            allAccountDto.map { it.toUiModel() }
        }.stateIn(scope)
    }

    suspend fun getAllAccountsWithRelatedPaymentAsFlow(scope: CoroutineScope): StateFlow<List<AccountWithRelatedPaymentUiModel>> {
        return accountRepository.getAllAccountsWithRelatedPaymentAsFlow().map {
            mapAllAccountsWithRelatedPaymentToUiModel(it)
        }.stateIn(scope)
    }

    private fun mapAllAccountsWithRelatedPaymentToUiModel(accountWithRelatedPayment: List<AccountWithRelatedPayments>): List<AccountWithRelatedPaymentUiModel> {
        return accountWithRelatedPayment.map {
            AccountWithRelatedPaymentUiModel(
                account = it.accountDTO.toUiModel(),
                relatedPayment = it.allPaymentsForAccount.map { it.toUiModel() }
            )
        }
    }

    suspend fun getAllAccounts(): List<AccountUiModel> {
        return accountRepository.getAllAccounts().map { it.toUiModel() }
    }
}