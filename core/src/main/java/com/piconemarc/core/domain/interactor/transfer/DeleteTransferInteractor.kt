package com.piconemarc.core.domain.interactor.transfer

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.core.domain.entityDTO.TransferDTO
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.model.entity.TransferUiModel
import javax.inject.Inject

class DeleteTransferInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun deleteTransfer(operation: OperationUiModel, transfer : TransferUiModel) {
        operationRepository.deleteTransfer(OperationDTO().fromUiModel(operation), TransferDTO().fromUiModel(transfer))
    }
}