package com.piconemarc.core.domain.interactor.transfer

import com.piconemarc.core.data.transfer.TransferRepository
import com.piconemarc.core.domain.entityDTO.TransferDTO
import com.piconemarc.model.entity.TransferUiModel
import javax.inject.Inject

class AddNewTransferInteractor @Inject constructor(private val transferRepository: TransferRepository) {

    suspend fun addNewTransfer(transfer: TransferUiModel) : Long {
        return transferRepository.addNewTransferOperation(mapTransferUiModelToDto(transfer))
    }

    private fun mapTransferUiModelToDto (transfer: TransferUiModel) : TransferDTO{
        return TransferDTO().fromUiModel(transfer)
    }
}