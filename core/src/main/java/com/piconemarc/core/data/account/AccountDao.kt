package com.piconemarc.core.data.account

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.core.domain.utils.Constants.ACCOUNT_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT*FROM $ACCOUNT_TABLE")
    fun getAllAccountsAsFlow(): Flow<List<AccountDTO>>

    @Query("SELECT*FROM $ACCOUNT_TABLE")
    suspend fun getAllAccounts(): List<AccountDTO>

    @Query("SELECT*FROM $ACCOUNT_TABLE WHERE $ACCOUNT_TABLE.id = :id")
    suspend fun getAccountForId(id: Long): AccountDTO

    @Query("SELECT*FROM $ACCOUNT_TABLE WHERE $ACCOUNT_TABLE.id = :id")
    fun getAccountForIdFlow(id: Long): Flow<AccountDTO>

    @Insert
    suspend fun addNewAccount(accountDTO: AccountDTO)

    @Delete
    suspend fun deleteAccount(accountDTO: AccountDTO)
}