package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.core.domain.entityDTO.TransferDTO
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.model.entity.TransferUiModel
import javax.inject.Inject

class DeleteOperationInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun deleteOperation(operation: OperationUiModel) {
        operationRepository.deleteOperation(OperationDTO().fromUiModel(operation))
    }
    suspend fun deletePayment(operation: OperationUiModel) {
        operationRepository.deletePayment(OperationDTO().fromUiModel(operation))
    }
    suspend fun deleteTransfer(operation: OperationUiModel,transfer : TransferUiModel) {
        operationRepository.deleteTransfer(OperationDTO().fromUiModel(operation), TransferDTO().fromUiModel(transfer))
    }

}