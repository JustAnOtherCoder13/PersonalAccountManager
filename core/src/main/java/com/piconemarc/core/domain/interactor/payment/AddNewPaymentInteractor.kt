package com.piconemarc.core.domain.interactor.payment

import com.piconemarc.core.data.payment.PaymentRepository
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import javax.inject.Inject

class AddNewPaymentInteractor @Inject constructor(private val paymentRepository: PaymentRepository)  {

    suspend fun addNewPayment(paymentDTO: PaymentDTO){
        paymentRepository.addNewPayment(paymentDTO)
    }
}