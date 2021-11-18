package com.piconemarc.viewmodel.di

import com.piconemarc.core.domain.interactor.account.AddNewAccountInteractor
import com.piconemarc.core.domain.interactor.account.DeleteAccountInteractor
import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.core.domain.interactor.operation.AddNewOperationInteractor
import com.piconemarc.core.domain.interactor.operation.DeleteOperationAndPaymentInteractor
import com.piconemarc.core.domain.interactor.operation.DeleteOperationInteractor
import com.piconemarc.core.domain.interactor.operation.GetOperationForIdInteractor
import com.piconemarc.core.domain.interactor.payment.AddNewPaymentInteractor
import com.piconemarc.core.domain.interactor.payment.AddPaymentAndOperationInteractor
import com.piconemarc.core.domain.interactor.payment.DeletePaymentAndRelatedOperationInteractor
import com.piconemarc.core.domain.interactor.payment.DeletePaymentInteractor
import com.piconemarc.core.domain.interactor.transfer.AddNewTransferInteractor
import com.piconemarc.core.domain.interactor.transfer.DeleteTransferInteractor
import com.piconemarc.core.domain.interactor.transfer.GetTransferForIdInteractor
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.*
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.utils.DefaultStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ActionDispatcherModule {

    @Provides
    fun provideAddOperationPopUpActionDispatcher(
        globalStore: DefaultStore<GlobalVmState>,
        addNewOperationInteractor: AddNewOperationInteractor,
        getAllAccountsInteractor: GetAllAccountsInteractor,
        getAllCategoriesInteractor: GetAllCategoriesInteractor,
        addNewPaymentInteractor: AddNewPaymentInteractor,
        addPaymentAndOperationInteractor: AddPaymentAndOperationInteractor,
        addNewTransferInteractor: AddNewTransferInteractor,
        getAccountForIdInteractor: GetAccountForIdInteractor
    ):AddOperationPopUpActionDispatcher{
        return AddOperationPopUpActionDispatcher(
            store = globalStore,
            addNewOperationInteractor = addNewOperationInteractor,
            getAllAccountsInteractor = getAllAccountsInteractor,
            getAllCategoriesInteractor = getAllCategoriesInteractor,
            addNewTransferInteractor = addNewTransferInteractor,
            addNewPaymentInteractor = addNewPaymentInteractor,
            addPaymentAndOperationInteractor = addPaymentAndOperationInteractor,
            getAccountForIdInteractor = getAccountForIdInteractor
        )
    }

    @Provides
    fun provideDeleteAccountPopUpActionDispatcher(
        globalStore: DefaultStore<GlobalVmState>,
        deleteAccountInteractor: DeleteAccountInteractor
    ):DeleteAccountPopUpActionDispatcher{
        return DeleteAccountPopUpActionDispatcher(
            store = globalStore,
            deleteAccountInteractor = deleteAccountInteractor,
        )
    }


    @Provides
    fun provideAddAccountPopUpActionDispatcher(
        globalStore: DefaultStore<GlobalVmState>,
        addNewAccountInteractor: AddNewAccountInteractor
    ) : AddAccountPopUpActionDispatcher{
        return AddAccountPopUpActionDispatcher(
            store = globalStore,
            addNewAccountInteractor = addNewAccountInteractor
        )
    }

    @Provides
    fun provideDeleteOperationPopUpActionDispatcher(
        globalStore: DefaultStore<GlobalVmState>,
        deleteOperationInteractor: DeleteOperationInteractor,
        getAccountForIdInteractor: GetAccountForIdInteractor,
        getOperationForIdInteractor: GetOperationForIdInteractor,
        getTransferForIdInteractor: GetTransferForIdInteractor,
        deletePaymentInteractor: DeletePaymentInteractor,
        deleteTransferInteractor: DeleteTransferInteractor,
        deleteOperationAndPaymentInteractor: DeleteOperationAndPaymentInteractor,
        deletePaymentAndRelatedOperationInteractor: DeletePaymentAndRelatedOperationInteractor
    ): DeleteOperationPopUpActionDispatcher{
        return DeleteOperationPopUpActionDispatcher(
            store = globalStore,
            deleteOperationInteractor = deleteOperationInteractor,
            getAccountForIdInteractor = getAccountForIdInteractor,
            getOperationForIdInteractor = getOperationForIdInteractor,
            getTransferForIdInteractor = getTransferForIdInteractor,
            deletePaymentInteractor = deletePaymentInteractor,
            deleteTransferInteractor = deleteTransferInteractor,
            deleteOperationAndPaymentInteractor = deleteOperationAndPaymentInteractor,
            deletePaymentAndRelatedOperationInteractor = deletePaymentAndRelatedOperationInteractor
        )
    }

    @Provides
    fun provideDeleteObsoletePaymentPopUpActionDispatcher (
        globalStore: DefaultStore<GlobalVmState>,
        deletePaymentInteractor: DeletePaymentInteractor
        ): DeleteObsoletePaymentPopUpActionDispatcher{
        return DeleteObsoletePaymentPopUpActionDispatcher(
            store = globalStore,
            deletePaymentInteractor = deletePaymentInteractor
        )

    }
}