package com.piconemarc.core.data.transfer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.piconemarc.core.domain.entityDTO.TransferDTO
import com.piconemarc.core.domain.utils.Constants.TRANSFER_TABLE

@Dao
interface TransferDao {

    @Insert
    suspend fun addNewTransferOperation(transferDTO: TransferDTO) : Long

    @Delete
    suspend fun deleteTransferOperation(transferDTO: TransferDTO)

    @Query("SELECT*FROM $TRANSFER_TABLE WHERE $TRANSFER_TABLE.id = :transferId")
    suspend fun getTransferOperationForId(transferId : Long) : TransferDTO

}