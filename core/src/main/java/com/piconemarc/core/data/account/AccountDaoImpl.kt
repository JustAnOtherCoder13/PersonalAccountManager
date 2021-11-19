package com.piconemarc.core.data.account

import com.piconemarc.core.data.PAMDatabase
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.core.domain.entityDTO.AccountWithRelatedOperations
import com.piconemarc.core.domain.entityDTO.AccountWithRelatedPayments
import com.piconemarc.core.domain.entityDTO.PaymentWithRelatedOperation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class AccountDaoImpl @Inject constructor(pamDatabase: PAMDatabase) :AccountDao {

    private val accountDao : AccountDao = pamDatabase.accountDao()

    override fun getAllAccountsAsFlow(): Flow<List<AccountDTO>> {
         return accountDao.getAllAccountsAsFlow()
    }

    override fun getAllAccountsWithRelatedPaymentAsFlow(): Flow<List<AccountWithRelatedPayments>> {
        return accountDao.getAllAccountsWithRelatedPaymentAsFlow()
    }

    override suspend fun getAllPaymentWithRelatedOperation(): List<PaymentWithRelatedOperation> {
        return accountDao.getAllPaymentWithRelatedOperation()
    }

    override fun getAccountForIdWithRelatedOperationsAsFlow(accountId: Long): Flow<AccountWithRelatedOperations> {
        return accountDao.getAccountForIdWithRelatedOperationsAsFlow(accountId).distinctUntilChanged()
    }

    override suspend fun getAccountForIdWithRelatedOperations(accountId: Long): AccountWithRelatedOperations {
        return accountDao.getAccountForIdWithRelatedOperations(accountId)
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

    override suspend fun getAccountForId(id:Long) : AccountDTO{
        return accountDao.getAccountForId(id)
    }
}