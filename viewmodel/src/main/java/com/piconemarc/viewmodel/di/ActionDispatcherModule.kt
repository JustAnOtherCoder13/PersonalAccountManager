package com.piconemarc.viewmodel.di

import com.piconemarc.core.domain.interactor.account.*
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.core.domain.interactor.operation.*
import com.piconemarc.core.domain.interactor.payment.AddNewPaymentInteractor
import com.piconemarc.core.domain.interactor.transfer.AddNewTransferInteractor
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.AddAccountPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.AddOperationPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.DeleteAccountPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.DeleteOperationPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.screen.BaseScreenActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.screen.MyAccountDetailScreenActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.screen.MyAccountScreenActionDispatcher
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
    fun provideMyAccountScreenActionDispatcher(
        globalStore: DefaultStore<GlobalVmState>,
        getAllAccountsInteractor: GetAllAccountsInteractor
    ): MyAccountScreenActionDispatcher {
        return MyAccountScreenActionDispatcher(
            getAllAccountsInteractor = getAllAccountsInteractor,
            store = globalStore
        )
    }

    @Provides
    fun provideMyAccountDetailScreenActionDispatcher(
        globalStore: DefaultStore<GlobalVmState>,
        getAccountForIdInteractor: GetAccountForIdInteractor,
        getAllOperationsForAccountIdInteractor: GetAllOperationsForAccountIdInteractor
    ) : MyAccountDetailScreenActionDispatcher{
        return MyAccountDetailScreenActionDispatcher(
            store = globalStore,
            getAccountForIdInteractor = getAccountForIdInteractor,
            getAllOperationsForAccountIdInteractor = getAllOperationsForAccountIdInteractor,
        )
    }

    @Provides
    fun provideAddOperationPopUpActionDispatcher(
        globalStore: DefaultStore<GlobalVmState>,
        addNewOperationInteractor: AddNewOperationInteractor,
        getAccountForIdInteractor: GetAccountForIdInteractor,
        getAllAccountsInteractor: GetAllAccountsInteractor,
        getAllCategoriesInteractor: GetAllCategoriesInteractor,
        updateAccountBalanceInteractor: UpdateAccountBalanceInteractor,
        addNewPaymentInteractor: AddNewPaymentInteractor,
        updateOperationPaymentIdInteractor: UpdateOperationPaymentIdInteractor,
        addNewTransferInteractor: AddNewTransferInteractor,
        updateOperationTransferIdInteractor: UpdateOperationTransferIdInteractor
    ):AddOperationPopUpActionDispatcher{
        return AddOperationPopUpActionDispatcher(
            store = globalStore,
            addNewOperationInteractor = addNewOperationInteractor,
            getAccountForIdInteractor = getAccountForIdInteractor,
            getAllAccountsInteractor = getAllAccountsInteractor,
            getAllCategoriesInteractor = getAllCategoriesInteractor,
            updateAccountBalanceInteractor = updateAccountBalanceInteractor,
            addNewPaymentInteractor = addNewPaymentInteractor,
            updateOperationPaymentIdInteractor = updateOperationPaymentIdInteractor,
            addNewTransferInteractor = addNewTransferInteractor,
            updateOperationTransferIdInteractor = updateOperationTransferIdInteractor
        )
    }

    @Provides
    fun provideDeleteAccountPopUpActionDispatcher(
        globalStore: DefaultStore<GlobalVmState>,
        getAccountForIdInteractor: GetAccountForIdInteractor,
        deleteAccountInteractor: DeleteAccountInteractor
    ):DeleteAccountPopUpActionDispatcher{
        return DeleteAccountPopUpActionDispatcher(
            store = globalStore,
            getAccountForIdInteractor = getAccountForIdInteractor,
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
        updateAccountBalanceInteractor: UpdateAccountBalanceInteractor
    ): DeleteOperationPopUpActionDispatcher{
        return DeleteOperationPopUpActionDispatcher(
            store = globalStore,
            deleteOperationInteractor = deleteOperationInteractor,
            getAccountForIdInteractor = getAccountForIdInteractor,
            getOperationForIdInteractor = getOperationForIdInteractor,
            updateAccountBalanceInteractor = updateAccountBalanceInteractor
        )
    }

}