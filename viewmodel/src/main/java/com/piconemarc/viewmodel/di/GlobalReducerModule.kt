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
    fun provideGlobalState(
        baseAppScreenVmState: ViewModelInnerStates.BaseAppScreenVmState,
        addOperationPopUpVMState: ViewModelInnerStates.AddOperationPopUpVMState,
        deleteAccountPopUpVMState: ViewModelInnerStates.DeleteAccountPopUpVMState,
        addAccountPopUpVMState: ViewModelInnerStates.AddAccountPopUpVMState
    ): ViewModelInnerStates.GlobalVmState =
        ViewModelInnerStates.GlobalVmState(
            baseAppScreenVmState = baseAppScreenVmState,
            addOperationPopUpVMState = addOperationPopUpVMState,
            deleteAccountPopUpVMState = deleteAccountPopUpVMState,
            addAccountPopUpVMState = addAccountPopUpVMState
        )


    @Provides
    fun provideGlobalStore(state: ViewModelInnerStates.GlobalVmState): DefaultStore<ViewModelInnerStates.GlobalVmState> {
        return DefaultStore(
            initialState = state,
            reducer = AppReducers.globalReducer
        )
    }

}