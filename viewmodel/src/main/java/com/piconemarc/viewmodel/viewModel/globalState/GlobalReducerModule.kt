package com.piconemarc.viewmodel.viewModel.globalState

import com.piconemarc.viewmodel.viewModel.DefaultStore
import com.piconemarc.viewmodel.viewModel.Store
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpUtilsProvider
import com.piconemarc.viewmodel.viewModel.baseAppScreen.BaseAppScreenUtilProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class GlobalReducerModule {

    @Provides
    fun provideAddOperationState(): AddOperationPopUpUtilsProvider.AddOperationPopUpVMState =
        AddOperationPopUpUtilsProvider.AddOperationPopUpVMState()

    @Provides
    fun baseAppScreenState() : BaseAppScreenUtilProvider.BaseAppScreenVmState =
        BaseAppScreenUtilProvider.BaseAppScreenVmState()

    @Provides
    fun provideGlobalState(
        baseAppScreenVmState: BaseAppScreenUtilProvider.BaseAppScreenVmState,
        addOperationPopUpVMState: AddOperationPopUpUtilsProvider.AddOperationPopUpVMState
    ) : GlobalUtilProvider.GlobalVmState =
        GlobalUtilProvider.GlobalVmState(
            baseAppScreenVmState = baseAppScreenVmState,
            addOperationPopUpVMState = addOperationPopUpVMState
        )


    @Provides
    fun provideAppStore (state : GlobalUtilProvider.GlobalVmState): DefaultStore<GlobalUtilProvider.GlobalVmState>{
        return DefaultStore(
            initialState = state,
            reducer = GlobalUtilProvider().globalReducer
        )
    }

    @Provides
    fun provideGlobalDispatcher(defaultStore: DefaultStore<GlobalUtilProvider.GlobalVmState>) : GlobalActionDispatcher {
        return GlobalActionDispatcher(
            defaultStore
        )
    }
}