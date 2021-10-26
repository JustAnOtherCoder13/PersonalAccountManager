package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import javax.inject.Inject

class UpdateOperationPaymentIdInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun updateOperationPaymentId(operationId : Long, paymentId : Long){
        operationRepository.updateOperationPaymentId(operationId = operationId, paymentId = paymentId)
    }
}