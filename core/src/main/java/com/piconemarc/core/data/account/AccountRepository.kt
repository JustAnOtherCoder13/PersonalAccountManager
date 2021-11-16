package com.piconemarc.core.data.account

import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.core.domain.entityDTO.AccountWithRelatedOperations
import com.piconemarc.core.domain.entityDTO.AccountWithRelatedPayments
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepository @Inject constructor(private val accountDaoImpl: AccountDaoImpl){

    fun getAllAccountsAsFlow(): Flow<List<AccountDTO>> {
        return accountDaoImpl.getAllAccountsAsFlow()
    }

    fun getAllAccountsWithRelatedPaymentAsFlow(): Flow<List<AccountWithRelatedPayments>> {
        return accountDaoImpl.getAllAccountsWithRelatedPaymentAsFlow()
    }

    suspend fun getAllAccountsWithRelatedPayment(): List<AccountWithRelatedPayments> {
        return accountDaoImpl.getAllAccountsWithRelatedPayment()
    }

    fun getAccountForIdWithRelatedOperationAsFlow(accountId: Long): Flow<AccountWithRelatedOperations> {
        return accountDaoImpl.getAccountForIdWithRelatedOperationsAsFlow(accountId)
    }

    suspend fun getAccountForIdWithRelatedOperations(accountId: Long): AccountWithRelatedOperations {
        return accountDaoImpl.getAccountForIdWithRelatedOperations(accountId)
    }

    suspend fun getAllAccounts(): List<AccountDTO> {
        return accountDaoImpl.getAllAccounts()
    }

     suspend fun getAccountForId(id:Long):AccountDTO{
        return accountDaoImpl.getAccountForId(id)
    }

     fun getAccountForIdFlow(id: Long): Flow<AccountDTO> {
        return accountDaoImpl.getAccountForIdFlow(id)
    }

    suspend fun addNewAccount(accountDTO: AccountDTO){
        accountDaoImpl.addNewAccount(accountDTO)
    }

    suspend fun deleteAccount(accountDTO: AccountDTO) {
        accountDaoImpl.deleteAccount(accountDTO)
    }
}