package com.piconemarc.core.data.account

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.piconemarc.core.domain.Constants.ACCOUNT_TABLE
import com.piconemarc.core.domain.entityDTO.AccountDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    //todo pass all with flow or better use shared flow and state flow
    @Query("SELECT*FROM $ACCOUNT_TABLE")
    fun getAllAccounts() : Flow<List<AccountDTO>>

    @Query("SELECT*FROM $ACCOUNT_TABLE WHERE $ACCOUNT_TABLE.id = :id")
    suspend fun getAccountForId(id:Long) : AccountDTO

    @Query("SELECT*FROM $ACCOUNT_TABLE WHERE $ACCOUNT_TABLE.id = :id")
     fun getAccountForIdFlow(id:Long) : Flow<AccountDTO>

    @Insert
    suspend fun addNewAccount(accountDTO: AccountDTO)

    @Delete
    suspend fun deleteAccount(accountDTO: AccountDTO)

    @Query("UPDATE $ACCOUNT_TABLE SET accountBalance = :accountBalance WHERE id = :accountId")
    suspend fun updateAccountBalance(accountId : Long, accountBalance : Double)
}