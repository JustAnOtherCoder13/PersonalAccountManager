package com.piconemarc.core.data.account

import com.piconemarc.core.domain.entityDTO.AccountDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepository @Inject constructor(private val accountDaoImpl: AccountDaoImpl){

    fun getAllAccountsAsFlow(): Flow<List<AccountDTO>> {
        return accountDaoImpl.getAllAccountsAsFlow()
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