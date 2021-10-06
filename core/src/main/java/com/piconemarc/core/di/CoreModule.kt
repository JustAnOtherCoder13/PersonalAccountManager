package com.piconemarc.core.di

import android.content.Context
import com.piconemarc.core.data.PAMDatabase
import com.piconemarc.core.data.account.AccountDaoImpl
import com.piconemarc.core.data.account.AccountRepository
import com.piconemarc.core.data.category.CategoryDaoImpl
import com.piconemarc.core.data.category.CategoryRepository
import com.piconemarc.core.data.operation.OperationDaoImpl
import com.piconemarc.core.data.operation.OperationRepository
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


}