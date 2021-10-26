package com.piconemarc.core.domain.interactor.transfer

import com.piconemarc.core.data.transfer.TransferRepository
import com.piconemarc.core.domain.entityDTO.TransferDTO
import com.piconemarc.model.entity.TransferUiModel
import javax.inject.Inject

class DeleteTransferInteractor@Inject constructor(private val transferRepository: TransferRepository) {

    suspend fun deleteTransfer(transfer: TransferUiModel){
        transferRepository.deleteTransferOperation(TransferDTO().fromUiModel(transfer))
    }
}