package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.entityDTO.AccountWithRelatedPayments
import com.piconemarc.core.domain.utils.Constants
import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.model.entity.AccountWithRelatedPaymentUiModel
import com.piconemarc.model.entity.PaymentUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetAllAccountsInteractor @Inject constructor(private val accountRepository: AccountRepository) :
    Constants.Interactor {

    fun getAllAccountsAsFlow(scope: CoroutineScope): SharedFlow<List<AccountUiModel>> {
        return accountRepository.getAllAccountsAsFlow().map { allAccountDto ->
            allAccountDto.map { it.toUiModel() }
        }.shareIn(scope, SharingStarted.WhileSubscribed())
    }

    fun getAllAccountsWithRelatedPaymentAsFlow(): Flow<List<AccountWithRelatedPaymentUiModel>> {
        return accountRepository.getAllAccountsWithRelatedPaymentAsFlow().map {
            mapAllAccountsWithRelatedPaymentToUiModel(it)
        }
    }

    private fun mapAllAccountsWithRelatedPaymentToUiModel(accountWithRelatedPayment: List<AccountWithRelatedPayments>): List<AccountWithRelatedPaymentUiModel> {
        return accountWithRelatedPayment.map {
            AccountWithRelatedPaymentUiModel(
                accountUiModel = it.accountDTO.toUiModel(),
                relatedPayment = it.allPaymentsForAccount.map { it.toUiModel() }
            )
        }
    }

    suspend fun getAllAccounts(): List<AccountUiModel> {
        return accountRepository.getAllAccounts().map { it.toUiModel() }
    }
}