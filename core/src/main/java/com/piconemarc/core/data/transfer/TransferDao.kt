package com.piconemarc.core.data.transfer

import androidx.room.Dao
import androidx.room.Query
import com.piconemarc.core.domain.entityDTO.TransferDTO
import com.piconemarc.core.domain.utils.Constants.TRANSFER_TABLE

@Dao
interface TransferDao {

    @Query("SELECT*FROM $TRANSFER_TABLE WHERE $TRANSFER_TABLE.id = :transferId")
    suspend fun getTransferOperationForId(transferId : Long) : TransferDTO

}