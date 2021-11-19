package com.piconemarc.core.domain.interactor.payment

import com.piconemarc.core.data.payment.PaymentRepository
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import com.piconemarc.model.entity.PaymentUiModel
import javax.inject.Inject

class DeletePaymentInteractor @Inject constructor(private val paymentRepository: PaymentRepository) {

    suspend fun deletePayment(payment: PaymentUiModel){
        paymentRepository.deletePayment(PaymentDTO().fromUiModel(payment))
    }

}