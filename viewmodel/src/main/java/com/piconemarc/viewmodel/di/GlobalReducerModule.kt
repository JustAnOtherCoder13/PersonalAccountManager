package com.piconemarc.viewmodel.di

import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.appReducer
import com.piconemarc.viewmodel.viewModel.utils.DefaultStore
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class GlobalReducerModule {

    @Singleton
    @Provides
    fun provideGlobalStore(state: GlobalVmState): DefaultStore<GlobalVmState> {
        return DefaultStore(
            initialState = state,
            reducer = appReducer
        )
    }

    @Provides
    fun provideGlobalState(
        baseAppScreenVmState: ViewModelInnerStates.BaseAppScreenVmState,
        addOperationPopUpVMState: ViewModelInnerStates.AddOperationPopUpVMState,
        deleteAccountPopUpVMState: ViewModelInnerStates.DeleteAccountPopUpVMState,
        addAccountPopUpVMState: ViewModelInnerStates.AddAccountPopUpVMState,
        myAccountDetailScreenVMState: ViewModelInnerStates.MyAccountDetailScreenVMState,
        myAccountScreenVMState: ViewModelInnerStates.MyAccountScreenVMState,
        deleteOperationPopUpVMState: ViewModelInnerStates.DeleteOperationPopUpVMState,
        paymentScreenVmState: ViewModelInnerStates.PaymentScreenVmState,
        deleteObsoletePaymentPopUpVMState: ViewModelInnerStates.DeleteObsoletePaymentPopUpVMState
    ): GlobalVmState =
        GlobalVmState(
            baseAppScreenVmState = baseAppScreenVmState,
            addOperationPopUpVMState = addOperationPopUpVMState,
            deleteAccountPopUpVMState = deleteAccountPopUpVMState,
            addAccountPopUpVMState = addAccountPopUpVMState,
            myAccountDetailScreenVMState = myAccountDetailScreenVMState,
            myAccountScreenVmState = myAccountScreenVMState,
            deleteOperationPopUpVmState = deleteOperationPopUpVMState,
            paymentScreenVmState = paymentScreenVmState,
            deleteObsoletePaymentPopUpVMState = deleteObsoletePaymentPopUpVMState
        )

    //Provided states, provide each new state and and it to provideGlobalState
    @Provides
    fun provideAddOperationState(): ViewModelInnerStates.AddOperationPopUpVMState =
        ViewModelInnerStates.AddOperationPopUpVMState()

    @Provides
    fun provideBaseAppScreenState(): ViewModelInnerStates.BaseAppScreenVmState =
        ViewModelInnerStates.BaseAppScreenVmState()

    @Provides
    fun provideDeleteAccountState(): ViewModelInnerStates.DeleteAccountPopUpVMState =
        ViewModelInnerStates.DeleteAccountPopUpVMState()

    @Provides
    fun provideAddAccountState(): ViewModelInnerStates.AddAccountPopUpVMState =
        ViewModelInnerStates.AddAccountPopUpVMState()
    @Provides
    fun provideMyAccountScreenState() : ViewModelInnerStates.MyAccountScreenVMState =
        ViewModelInnerStates.MyAccountScreenVMState()

    @Provides
    fun provideMyAccountDetailScreenVmState() : ViewModelInnerStates.MyAccountDetailScreenVMState =
        ViewModelInnerStates.MyAccountDetailScreenVMState()

    @Provides
    fun provideDeleteOperationPopUpVmState() : ViewModelInnerStates.DeleteOperationPopUpVMState =
        ViewModelInnerStates.DeleteOperationPopUpVMState()

    @Provides
    fun providePaymentScreenVmState() : ViewModelInnerStates.PaymentScreenVmState =
        ViewModelInnerStates.PaymentScreenVmState()

    @Provides
    fun provideDeleteObsoletePaymentPopUpVmState() : ViewModelInnerStates.DeleteObsoletePaymentPopUpVMState =
        ViewModelInnerStates.DeleteObsoletePaymentPopUpVMState()

}