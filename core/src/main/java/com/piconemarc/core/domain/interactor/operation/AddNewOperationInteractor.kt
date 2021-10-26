package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.OperationUiModel
import java.util.*
import javax.inject.Inject

class AddNewOperationInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun addOperation(operationModel: OperationUiModel) {
        return operationRepository.addOperation(OperationDTO().fromUiModel(operationModel))
    }

    suspend fun addPaymentOperation(operation: OperationUiModel, endDate: Date?) {
        operationRepository.addPaymentOperation(OperationDTO().fromUiModel(operation), endDate)
    }
    suspend fun addTransferOperation(operation: OperationUiModel, beneficiaryAccountId: Long) {
        operationRepository.addTransferOperation(OperationDTO().fromUiModel(operation), beneficiaryAccountId)
    }

}