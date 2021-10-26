package com.piconemarc.core.domain.interactor.payment

import com.piconemarc.core.data.payment.PaymentRepository
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import com.piconemarc.model.entity.PaymentUiModel
import javax.inject.Inject

class AddNewPaymentInteractor @Inject constructor(private val paymentRepository: PaymentRepository)  {

    suspend fun addNewPayment(payment : PaymentUiModel): Long{
        return paymentRepository.addNewPayment(mapPaymentUiModelToPaymentDto(payment))
    }

    private fun mapPaymentUiModelToPaymentDto(payment : PaymentUiModel):PaymentDTO{
        return PaymentDTO().fromUiModel(payment)
    }
}