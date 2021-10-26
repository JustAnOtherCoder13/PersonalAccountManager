package com.piconemarc.core.domain.interactor.transfer

import com.piconemarc.core.data.transfer.TransferRepository
import com.piconemarc.core.domain.entityDTO.TransferDTO
import com.piconemarc.model.entity.TransferUiModel
import javax.inject.Inject

class GetTransferForIdInteractor @Inject constructor(private val transferRepository: TransferRepository) {

    suspend fun getTransferForId(transferId : Long): TransferUiModel {
        return mapTransferDtoToTransferUiModel(transferRepository.getTransferOperationForId(transferId))
    }

    private fun mapTransferDtoToTransferUiModel(transferDTO: TransferDTO) : TransferUiModel{
        return transferDTO.toUiModel()
    }
}