package com.piconemarc.core.domain.interactor.payment

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.OperationUiModel
import javax.inject.Inject

class PassSinglePaymentForThisMonthInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun passPaymentForThisMonth(operation: OperationUiModel, paymentId: Long) {
        operationRepository.passPaymentForThisMonth(
            OperationDTO().fromUiModel(operation),
            paymentId
        )
    }
}