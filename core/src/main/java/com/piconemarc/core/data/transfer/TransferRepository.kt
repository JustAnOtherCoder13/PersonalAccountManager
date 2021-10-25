package com.piconemarc.core.data.transfer

import com.piconemarc.core.domain.entityDTO.TransferDTO
import javax.inject.Inject

class TransferRepository @Inject constructor(
    private val transferDao: TransferDaoImpl
) {

     suspend fun addNewTransferOperation(transferDTO: TransferDTO) : Long {
        return transferDao.addNewTransferOperation(transferDTO)
    }

     suspend fun deleteTransferOperation(transferDTO: TransferDTO) {
        transferDao.deleteTransferOperation(transferDTO)
    }

     suspend fun getTransferOperationForId(transferId: Long): TransferDTO {
        return transferDao.getTransferOperationForId(transferId)
    }
}