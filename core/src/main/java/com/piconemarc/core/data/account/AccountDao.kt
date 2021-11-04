package com.piconemarc.core.data.account

import androidx.room.*
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.core.domain.entityDTO.AccountWithRelatedPayments
import com.piconemarc.core.domain.utils.Constants.ACCOUNT_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT*FROM $ACCOUNT_TABLE")
    fun getAllAccountsAsFlow(): Flow<List<AccountDTO>>

    @Transaction
    @Query("SELECT*FROM $ACCOUNT_TABLE")
    fun getAllAccountsWithRelatedPaymentAsFlow(): Flow<List<AccountWithRelatedPayments>>

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