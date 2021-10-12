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

    @Query("SELECT*FROM $ACCOUNT_TABLE")
    fun getAllAccounts() : Flow<List<AccountDTO>>

    @Insert
    suspend fun addNewAccount(accountDTO: AccountDTO)

    @Delete
    suspend fun deleteAccount(accountDTO: AccountDTO)
}