package com.piconemarc.core.domain.interactor.transfer

import com.piconemarc.core.data.transfer.TransferRepository
import com.piconemarc.core.domain.entityDTO.TransferDTO
import javax.inject.Inject

class GetTransferForIdInteractor @Inject constructor(private val transferRepository: TransferRepository) {

    suspend fun getTransferForId(transferId : Long): TransferDTO {
        return transferRepository.getTransferOperationForId(transferId)
    }
}