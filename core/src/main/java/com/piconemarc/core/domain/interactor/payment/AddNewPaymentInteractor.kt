package com.piconemarc.core.domain.interactor.payment

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.PaymentDTO
import com.piconemarc.model.entity.PaymentUiModel
import javax.inject.Inject

class AddNewPaymentInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun addNewPayment(payment: PaymentUiModel): Long {
        return operationRepository.addNewPayment(PaymentDTO().fromUiModel(payment))
    }
}