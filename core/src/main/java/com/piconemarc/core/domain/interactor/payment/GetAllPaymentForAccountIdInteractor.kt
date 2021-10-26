package com.piconemarc.core.domain.interactor.payment

import com.piconemarc.core.data.payment.PaymentRepository
import com.piconemarc.model.entity.PaymentUiModel
import javax.inject.Inject

class GetAllPaymentForAccountIdInteractor @Inject constructor(private val paymentRepository: PaymentRepository) {

    suspend fun getAllPaymentForAccountId(accountId : Long) : List<PaymentUiModel>{
        return paymentRepository.getAllPaymentForAccountId(accountId).map {
            it.toUiModel()
        }
    }
}