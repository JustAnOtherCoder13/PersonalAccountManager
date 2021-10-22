package com.piconemarc.viewmodel.di

import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.viewModel.AppReducers
import com.piconemarc.viewmodel.viewModel.ViewModelInnerStates
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class GlobalReducerModule {

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
    fun provideGlobalState(
        baseAppScreenVmState: ViewModelInnerStates.BaseAppScreenVmState,
        addOperationPopUpVMState: ViewModelInnerStates.AddOperationPopUpVMState,
        deleteAccountPopUpVMState: ViewModelInnerStates.DeleteAccountPopUpVMState,
        addAccountPopUpVMState: ViewModelInnerStates.AddAccountPopUpVMState,
        myAccountDetailScreenVMState: ViewModelInnerStates.MyAccountDetailScreenVMState,
        myAccountScreenVMState: ViewModelInnerStates.MyAccountScreenVMState,
        deleteOperationPopUpVMState: ViewModelInnerStates.DeleteOperationPopUpVMState
    ): ViewModelInnerStates.GlobalVmState =
        ViewModelInnerStates.GlobalVmState(
            baseAppScreenVmState = baseAppScreenVmState,
            addOperationPopUpVMState = addOperationPopUpVMState,
            deleteAccountPopUpVMState = deleteAccountPopUpVMState,
            addAccountPopUpVMState = addAccountPopUpVMState,
            myAccountDetailScreenVMState = myAccountDetailScreenVMState,
            myAccountScreenVmState = myAccountScreenVMState,
            deleteOperationPopUpVmState = deleteOperationPopUpVMState
        )


    @Provides
    fun provideGlobalStore(state: ViewModelInnerStates.GlobalVmState): DefaultStore<ViewModelInnerStates.GlobalVmState> {
        return DefaultStore(
            initialState = state,
            reducer = AppReducers.globalReducer
        )
    }

}