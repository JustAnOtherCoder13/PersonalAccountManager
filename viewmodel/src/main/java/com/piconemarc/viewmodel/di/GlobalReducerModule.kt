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
    fun provideBaseAppScreenState() : ViewModelInnerStates.BaseAppScreenVmState =
        ViewModelInnerStates.BaseAppScreenVmState()

    @Provides
    fun provideGlobalState(
        baseAppScreenVmState: ViewModelInnerStates.BaseAppScreenVmState,
        addOperationPopUpVMState: ViewModelInnerStates.AddOperationPopUpVMState
    ) : ViewModelInnerStates.GlobalVmState =
        ViewModelInnerStates.GlobalVmState(
            baseAppScreenVmState = baseAppScreenVmState,
            addOperationPopUpVMState = addOperationPopUpVMState
        )


    @Provides
    fun provideGlobalStore (state : ViewModelInnerStates.GlobalVmState): DefaultStore<ViewModelInnerStates.GlobalVmState> {
        return DefaultStore(
            initialState = state,
            reducer = AppReducers.globalReducer
        )
    }

}