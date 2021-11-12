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
import com.piconemarc.core.domain.interactor.operation.AddNewOperationInteractor
import com.piconemarc.core.domain.interactor.operation.DeleteOperationAndPaymentInteractor
import com.piconemarc.core.domain.interactor.operation.DeleteOperationInteractor
import com.piconemarc.core.domain.interactor.operation.GetOperationForIdInteractor
import com.piconemarc.core.domain.interactor.payment.*
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
    fun provideGetAccountAndRelatedOperationsForAccountId(accountRepository: AccountRepository): GetAccountAndRelatedOperationsForAccountIdInteractor {
        return GetAccountAndRelatedOperationsForAccountIdInteractor(accountRepository)
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
    fun provideDeleteOperationAndPayment(operationRepository: OperationRepository) : DeleteOperationAndPaymentInteractor {
        return DeleteOperationAndPaymentInteractor(operationRepository)
    }


    //PAYMENT
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

    @Provides
    fun provideAddNewPayment(operationRepository: OperationRepository) : AddNewPaymentInteractor{
        return AddNewPaymentInteractor(operationRepository)
    }

    @Provides
    fun provideAddNewPaymentAndRelatedOperation(operationRepository: OperationRepository) : AddPaymentAndOperationInteractor{
        return AddPaymentAndOperationInteractor(operationRepository)
    }

    @Provides
    fun provideDeletePaymentAndRelatedOperation(operationRepository: OperationRepository) : DeletePaymentAndRelatedOperationInteractor{
        return DeletePaymentAndRelatedOperationInteractor(operationRepository)
    }


    //TRANSFER
    @Provides
    fun provideGetTransferForId(transferRepository: TransferRepository): GetTransferForIdInteractor{
        return GetTransferForIdInteractor(transferRepository)
    }

    @Provides
    fun provideAddNewTransfer(operationRepository: OperationRepository) : AddNewTransferInteractor{
        return AddNewTransferInteractor(operationRepository)
    }

    @Provides
    fun provideDeleteTransfer(operationRepository: OperationRepository) : DeleteTransferInteractor{
        return DeleteTransferInteractor(operationRepository)
    }

}