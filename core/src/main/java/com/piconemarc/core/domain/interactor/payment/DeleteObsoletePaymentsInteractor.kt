package com.piconemarc.core.domain.interactor.payment

import com.piconemarc.core.data.payment.PaymentRepository
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import com.piconemarc.model.entity.PaymentUiModel
import javax.inject.Inject

class DeleteObsoletePaymentsInteractor @Inject constructor(private val paymentRepository: PaymentRepository) {

    suspend fun deleteObsoletePayments(obsoletePayments: List<PaymentUiModel>) {
        paymentRepository.deleteObsoletePayments(
            obsoletePayments.map { PaymentDTO().fromUiModel(it) } )
    }
}