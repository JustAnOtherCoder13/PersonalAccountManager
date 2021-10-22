package com.piconemarc.core.di

import android.content.Context
import com.piconemarc.core.data.PAMDatabase
import com.piconemarc.core.data.account.AccountDaoImpl
import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.data.category.CategoryDaoImpl
import com.piconemarc.core.data.category.CategoryRepository
import com.piconemarc.core.data.operation.OperationDaoImpl
import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.interactor.account.*
import com.piconemarc.core.domain.interactor.category.AddNewCategoryInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.core.domain.interactor.operation.AddNewOperationInteractor
import com.piconemarc.core.domain.interactor.operation.DeleteOperationInteractor
import com.piconemarc.core.domain.interactor.operation.GetAllOperationsForAccountIdInteractor
import com.piconemarc.core.domain.interactor.operation.GetAllOperationsInteractor
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

}