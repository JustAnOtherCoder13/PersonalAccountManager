package com.piconemarc.core.data.transfer

import com.piconemarc.core.domain.entityDTO.TransferDTO
import javax.inject.Inject

class TransferRepository @Inject constructor(
    private val transferDao: TransferDaoImpl
) {
     suspend fun getTransferOperationForId(transferId: Long): TransferDTO {
        return transferDao.getTransferOperationForId(transferId)
    }
}