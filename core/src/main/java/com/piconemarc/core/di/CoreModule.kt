package com.piconemarc.core.di

import android.content.Context
import com.piconemarc.core.data.PAMDatabase
import com.piconemarc.core.data.account.AccountDaoImpl
import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.data.category.CategoryDaoImpl
import com.piconemarc.core.data.category.CategoryRepository
import com.piconemarc.core.data.operation.OperationDaoImpl
import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.data.payment.PaymentDaoImpl
import com.piconemarc.core.data.payment.PaymentRepository
import com.piconemarc.core.data.transfer.TransferDaoImpl
import com.piconemarc.core.data.transfer.TransferRepository
import com.piconemarc.core.domain.interactor.account.*
import com.piconemarc.core.domain.interactor.category.AddNewCategoryInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.core.domain.interactor.operation.*
import com.piconemarc.core.domain.interactor.payment.AddNewPaymentInteractor
import com.piconemarc.core.domain.interactor.payment.DeletePaymentInteractor
import com.piconemarc.core.domain.interactor.payment.GetAllPaymentForAccountIdInteractor
import com.piconemarc.core.domain.interactor.payment.GetPaymentForIdInteractor
import com.piconemarc.core.domain.interactor.transfer.AddNewTransferInteractor
import com.piconemarc.core.domain.interactor.transfer.DeleteTransferInteractor
import com.piconemarc.core.domain.interactor.transfer.GetTransferForIdInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class CoreModule {

    @Singleton
    @Provides
    fun providePAMDatabase(@ApplicationContext context: Context) : PAMDatabase{
        return PAMDatabase.getPAMDatabase(context)
    }

    //---------------------------DAO--------------------------------------

    @Singleton
    @Provides
    fun provideAccountDao(pamDatabase: PAMDatabase):AccountDaoImpl{
        return AccountDaoImpl(pamDatabase)
    }

    @Singleton
    @Provides
    fun provideOperationDao(pamDatabase: PAMDatabase):OperationDaoImpl{
        return OperationDaoImpl(pamDatabase)
    }

    @Singleton
    @Provides
    fun provideCategoryDao(pamDatabase: PAMDatabase):CategoryDaoImpl{
        return CategoryDaoImpl(pamDatabase)
    }

    @Singleton
    @Provides
    fun provideTransferDao(pamDatabase: PAMDatabase):TransferDaoImpl{
        return TransferDaoImpl(pamDatabase)
    }

    @Singleton
    @Provides
    fun providePaymentDao(pamDatabase: PAMDatabase): PaymentDaoImpl{
        return PaymentDaoImpl(pamDatabase)
    }

    //-----------------------------REPOSITORY--------------------------------

    @Provides
    fun provideCategoryRepository(categoryDaoImpl: CategoryDaoImpl) : CategoryRepository {
        return CategoryRepository(categoryDaoImpl)
    }

    @Provides
    fun provideOperationRepository(operationDaoImpl: OperationDaoImpl) : OperationRepository{
        return OperationRepository(operationDaoImpl)
    }

    @Provides
    fun provideAccountRepository(accountDaoImpl: AccountDaoImpl) : AccountRepository{
        return AccountRepository(accountDaoImpl)
    }

    @Provides
    fun provideTransferRepository(transferDaoImpl: TransferDaoImpl): TransferRepository{
        return TransferRepository(transferDaoImpl)
    }

    @Provides
    fun providePaymentRepository(paymentDaoImpl: PaymentDaoImpl) : PaymentRepository{
        return PaymentRepository(paymentDaoImpl)
    }

    //------------------------------INTERACTORS----------------------------------

    //ACCOUNT
    @Provides
    fun provideGetAllAccounts(accountRepository: AccountRepository) : GetAllAccountsInteractor{
        return GetAllAccountsInteractor(accountRepository)
    }

    @Provides
    fun provideGetAccountForId(accountRepository: AccountRepository) :GetAccountForIdInteractor{
        return GetAccountForIdInteractor(accountRepository)
    }

    @Provides
    fun provideAddNewAccount(accountRepository: AccountRepository) : AddNewAccountInteractor{
        return AddNewAccountInteractor(accountRepository)
    }

    @Provides
    fun provideDeleteAccount(accountRepository: AccountRepository) : DeleteAccountInteractor{
        return DeleteAccountInteractor(accountRepository)
    }

    @Provides
    fun provideUpdateAccountBalanceInteractor (accountRepository: AccountRepository) :UpdateAccountBalanceInteractor{
        return UpdateAccountBalanceInteractor(accountRepository)
    }

    //CATEGORY
    @Provides
    fun provideGetAllCategories(categoryRepository: CategoryRepository) : GetAllCategoriesInteractor {
        return GetAllCategoriesInteractor(categoryRepository)
    }

    @Provides
    fun provideAddNewCategory(categoryRepository: CategoryRepository) : AddNewCategoryInteractor{
        return AddNewCategoryInteractor(categoryRepository)
    }

    //Operation

    @Provides
    fun provideGetAllOperations(operationRepository: OperationRepository): GetAllOperationsInteractor{
        return GetAllOperationsInteractor(operationRepository)
    }

    @Provides
    fun provideGetAllOperationsForAccountId(operationRepository: OperationRepository):GetAllOperationsForAccountIdInteractor{
        return GetAllOperationsForAccountIdInteractor(operationRepository)
    }

    @Provides
    fun provideAddOperation(operationRepository: OperationRepository): AddNewOperationInteractor{
        return AddNewOperationInteractor(operationRepository)
    }

    @Provides
    fun provideDeleteOperation(operationRepository: OperationRepository) : DeleteOperationInteractor{
        return DeleteOperationInteractor(operationRepository)
    }

    @Provides
    fun provideGetOperationForId(operationRepository: OperationRepository) :GetOperationForIdInteractor{
        return GetOperationForIdInteractor(operationRepository)
    }

    @Provides
    fun provideUpdateOperationPaymentId(operationRepository: OperationRepository):UpdateOperationPaymentIdInteractor{
        return UpdateOperationPaymentIdInteractor(operationRepository)
    }

    @Provides
    fun provideUpdateOperationTransferId(operationRepository: OperationRepository):UpdateOperationTransferIdInteractor{
        return UpdateOperationTransferIdInteractor(operationRepository)
    }
    //PAYMENT
    @Provides
    fun provideAddNewPayment(paymentRepository: PaymentRepository): AddNewPaymentInteractor{
        return AddNewPaymentInteractor(paymentRepository)
    }

    @Provides
    fun provideDeletePayment(paymentRepository: PaymentRepository) :DeletePaymentInteractor{
        return DeletePaymentInteractor(paymentRepository)
    }

    @Provides
    fun provideGetAllPaymentForAccountId(paymentRepository: PaymentRepository): GetAllPaymentForAccountIdInteractor{
        return GetAllPaymentForAccountIdInteractor(paymentRepository)
    }

    @Provides
    fun provideGetPaymentForId(paymentRepository: PaymentRepository) : GetPaymentForIdInteractor{
        return GetPaymentForIdInteractor(paymentRepository)
    }

    //TRANSFER
    @Provides
    fun provideAddNewTransfer(transferRepository: TransferRepository): AddNewTransferInteractor{
        return AddNewTransferInteractor(transferRepository)
    }

    @Provides
    fun provideDeleteTransfer(transferRepository: TransferRepository): DeleteTransferInteractor{
        return DeleteTransferInteractor(transferRepository)
    }

    @Provides
    fun provideGetTransferForId(transferRepository: TransferRepository): GetTransferForIdInteractor{
        return GetTransferForIdInteractor(transferRepository)
    }

}