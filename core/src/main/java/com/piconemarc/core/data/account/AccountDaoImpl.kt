package com.piconemarc.core.data.account

import com.piconemarc.core.data.PAMDatabase
import com.piconemarc.core.domain.entityDTO.AccountDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountDaoImpl @Inject constructor(pamDatabase: PAMDatabase) :AccountDao {

    private val accountDao : AccountDao = pamDatabase.accountDao()

    override fun getAllAccountsAsFlow(): Flow<List<AccountDTO>> {
         return accountDao.getAllAccountsAsFlow()
    }

    override suspend fun getAllAccounts(): List<AccountDTO> {
        return accountDao.getAllAccounts()
    }

    override suspend fun addNewAccount(accountDTO: AccountDTO){
        accountDao.addNewAccount(accountDTO)
    }

    override suspend fun deleteAccount(accountDTO: AccountDTO) {
        accountDao.deleteAccount(accountDTO)
    }

    override suspend fun updateAccountBalance(accountId: Long, accountBalance: Double) {
        accountDao.updateAccountBalance(accountId, accountBalance)
    }

    override suspend fun getAccountForId(id:Long) : AccountDTO{
        return accountDao.getAccountForId(id)
    }

    override  fun getAccountForIdFlow(id: Long): Flow<AccountDTO> {
        return accountDao.getAccountForIdFlow(id)
    }

}