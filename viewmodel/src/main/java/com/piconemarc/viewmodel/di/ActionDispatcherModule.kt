package com.piconemarc.viewmodel.di

import com.piconemarc.core.domain.interactor.account.AddNewAccountInteractor
import com.piconemarc.core.domain.interactor.account.DeleteAccountInteractor
import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.core.domain.interactor.operation.*
import com.piconemarc.core.domain.interactor.payment.*
import com.piconemarc.core.domain.interactor.transfer.AddNewTransferInteractor
import com.piconemarc.core.domain.interactor.transfer.DeleteTransferInteractor
import com.piconemarc.core.domain.interactor.transfer.GetTransferForIdInteractor
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.AddAccountPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.AddOperationPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.DeleteAccountPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.DeleteOperationPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.screen.BaseScreenActionDispatcher
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ActionDispatcherModule {

    @Provides
    fun providedBaseScreenActionDispatcher(
        getAllAccountsInteractor: GetAllAccountsInteractor,
        globalStore: DefaultStore<GlobalVmState>
    ): BaseScreenActionDispatcher {
        return BaseScreenActionDispatcher(
            getAllAccountsInteractor = getAllAccountsInteractor,
            store = globalStore
        )
    }


    @Provides
    fun provideAddOperationPopUpActionDispatcher(
        globalStore: DefaultStore<GlobalVmState>,
        addNewOperationInteractor: AddNewOperationInteractor,
        getAllAccountsInteractor: GetAllAccountsInteractor,
        getAllCategoriesInteractor: GetAllCategoriesInteractor,
        addNewPaymentInteractor: AddNewPaymentInteractor,
        addPaymentAndOperationInteractor: AddPaymentAndOperationInteractor,
        addNewTransferInteractor: AddNewTransferInteractor
    ):AddOperationPopUpActionDispatcher{
        return AddOperationPopUpActionDispatcher(
            store = globalStore,
            addNewOperationInteractor = addNewOperationInteractor,
            getAllAccountsInteractor = getAllAccountsInteractor,
            getAllCategoriesInteractor = getAllCategoriesInteractor,
            addNewTransferInteractor = addNewTransferInteractor,
            addNewPaymentInteractor = addNewPaymentInteractor,
            addPaymentAndOperationInteractor = addPaymentAndOperationInteractor
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
}