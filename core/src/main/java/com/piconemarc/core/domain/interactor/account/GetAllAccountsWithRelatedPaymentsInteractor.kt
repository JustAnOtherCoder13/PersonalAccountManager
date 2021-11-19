package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.domain.entityDTO.AccountWithRelatedPayments
import com.piconemarc.core.domain.entityDTO.PaymentWithRelatedOperation
import com.piconemarc.model.entity.AccountWithRelatedPaymentUiModel
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.model.getCalendarDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.*
import javax.inject.Inject

class GetAllAccountsWithRelatedPaymentsInteractor @Inject constructor(private val accountRepository: AccountRepository) {

    suspend fun getAllAccountsWithRelatedPaymentAsFlow(scope: CoroutineScope): StateFlow<List<AccountWithRelatedPaymentUiModel>> {
        return accountRepository.getAllAccountsWithRelatedPaymentAsFlow().map {
            mapAllAccountsWithRelatedPaymentToUiModel(it)
        }.stateIn(scope)
    }

    private fun mapAllAccountsWithRelatedPaymentToUiModel(accountWithRelatedPayment: List<AccountWithRelatedPayments>): List<AccountWithRelatedPaymentUiModel> {
        return accountWithRelatedPayment.map { accountWithRelatedPayment_ ->
            AccountWithRelatedPaymentUiModel(
                account = accountWithRelatedPayment_.accountDTO.toUiModel(),
                relatedPayment = accountWithRelatedPayment_.allPaymentsForAccount.map {paymentWithRelatedOperation ->
                    updateIfPaymentsPassedForTHisMonth(paymentWithRelatedOperation)
                }
            )
        }
    }

    private fun updateIfPaymentsPassedForTHisMonth(paymentWithRelatedOperation: PaymentWithRelatedOperation) : PaymentUiModel {
        return paymentWithRelatedOperation.paymentDTO.toUiModel().copy(
            isPaymentPassForThisMonth = paymentWithRelatedOperation.paymentDTO.operationId != null
                    && getCalendarDate(paymentWithRelatedOperation.relatedOperation?.emitDate).get(Calendar.MONTH).compareTo(Calendar.getInstance().get(Calendar.MONTH)) == 0
        )
    }

}