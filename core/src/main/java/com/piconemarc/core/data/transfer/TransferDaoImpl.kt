package com.piconemarc.core.data.transfer

import com.piconemarc.core.data.PAMDatabase
import com.piconemarc.core.domain.entityDTO.TransferDTO
import javax.inject.Inject

class TransferDaoImpl @Inject constructor(pamDatabase: PAMDatabase) : TransferDao {

    private val transferDao = pamDatabase.transferDao()

    override suspend fun addNewTransferOperation(transferDTO: TransferDTO) {
        transferDao.addNewTransferOperation(transferDTO)
    }

    override suspend fun deleteTransferOperation(transferDTO: TransferDTO) {
        transferDao.deleteTransferOperation(transferDTO)
    }

    override suspend fun getTransferOperationForId(transferId: Long): TransferDTO {
        return transferDao.getTransferOperationForId(transferId)
    }


}