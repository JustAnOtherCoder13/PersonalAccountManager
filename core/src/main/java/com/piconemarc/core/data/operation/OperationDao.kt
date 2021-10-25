package com.piconemarc.core.data.operation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.piconemarc.core.domain.Constants.OPERATION_TABLE
import com.piconemarc.core.domain.entityDTO.OperationDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface OperationDao {

    @Query("SELECT*FROM $OPERATION_TABLE")
    fun getAllOperations() : Flow<List<OperationDTO>>

    @Query("SELECT*FROM $OPERATION_TABLE WHERE $OPERATION_TABLE.accountId = :accountId")
    fun getAllOperationsForAccountId(accountId : Long) : Flow<List<OperationDTO>>

    @Query("SELECT*FROM $OPERATION_TABLE WHERE $OPERATION_TABLE.id = :operationId AND $OPERATION_TABLE.accountId = :accountId")
    suspend fun getOperationForAccountIdWithOperationId(operationId : Long, accountId: Long) : OperationDTO

    @Insert
    suspend fun addNewOperation(operationDTO: OperationDTO) : Long

    @Delete
    suspend fun deleteOperation (operationDTO: OperationDTO)
}