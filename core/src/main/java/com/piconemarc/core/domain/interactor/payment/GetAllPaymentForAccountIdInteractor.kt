package com.piconemarc.core.domain.interactor.payment

import com.piconemarc.core.data.payment.PaymentRepository
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import javax.inject.Inject

class GetAllPaymentForAccountIdInteractor @Inject constructor(private val paymentRepository: PaymentRepository) {

    suspend fun getAllPaymentForAccountId(accountId : Long) : List<PaymentDTO>{
        return paymentRepository.getAllPaymentForAccountId(accountId)
    }
}