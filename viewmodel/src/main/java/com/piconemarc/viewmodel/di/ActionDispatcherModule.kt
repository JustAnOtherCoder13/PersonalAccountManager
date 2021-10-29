package com.piconemarc.viewmodel.di

import com.piconemarc.core.domain.interactor.account.*
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.core.domain.interactor.operation.*
import com.piconemarc.core.domain.interactor.transfer.GetTransferForIdInteractor
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.AddAccountPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.AddOperationPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.DeleteAccountPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.popup.DeleteOperationPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.screen.BaseScreenActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.screen.MyAccountDetailScreenActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.screen.MyAccountScreenActionDispatcher
import com.piconemarc.viewmodel.viewModel.actionDispatcher.screen.PaymentScreenActionDispatcher
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
        getAllAccountsInteractor: GetAllAccountsInteractor,
        getAllCategoriesInteractor: GetAllCategoriesInteractor
    ):AddOperationPopUpActionDispatcher{
        return AddOperationPopUpActionDispatcher(
            store = globalStore,
            addNewOperationInteractor = addNewOperationInteractor,
            getAllAccountsInteractor = getAllAccountsInteractor,
            getAllCategoriesInteractor = getAllCategoriesInteractor
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
    ): DeleteOperationPopUpActionDispatcher{
        return DeleteOperationPopUpActionDispatcher(
            store = globalStore,
            deleteOperationInteractor = deleteOperationInteractor,
            getAccountForIdInteractor = getAccountForIdInteractor,
            getOperationForIdInteractor = getOperationForIdInteractor,
            getTransferForIdInteractor = getTransferForIdInteractor,
        )
    }

    @Provides
    fun providePaymentScreenActionDispatcher(
        globalStore: DefaultStore<GlobalVmState>,
        getAllAccountsInteractor: GetAllAccountsInteractor
        ):PaymentScreenActionDispatcher{
        return PaymentScreenActionDispatcher(
            store = globalStore,
            getAllAccountsInteractor = getAllAccountsInteractor,
        )
    }
}