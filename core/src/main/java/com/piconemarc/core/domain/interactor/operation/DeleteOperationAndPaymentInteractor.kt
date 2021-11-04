package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.OperationUiModel
import javax.inject.Inject

class DeleteOperationAndPaymentInteractor @Inject constructor(private val operationRepository: OperationRepository) {
    suspend fun deleteOperationAndPayment(operation: OperationUiModel) {
        operationRepository.deleteOperationAndPayment(OperationDTO().fromUiModel(operation))
    }
}