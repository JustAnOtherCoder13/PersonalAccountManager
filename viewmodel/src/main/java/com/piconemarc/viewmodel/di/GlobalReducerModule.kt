package com.piconemarc.viewmodel.di

import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.viewmodel.viewModel.DefaultStore
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpUtilsProvider
import com.piconemarc.viewmodel.viewModel.baseAppScreen.BaseAppScreenUtilProvider
import com.piconemarc.viewmodel.viewModel.globalState.GlobalActionDispatcher
import com.piconemarc.viewmodel.viewModel.globalState.GlobalUtilProvider
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
    fun provideBaseAppScreenState() : BaseAppScreenUtilProvider.BaseAppScreenVmState =
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
    fun provideGlobalStore (state : GlobalUtilProvider.GlobalVmState): DefaultStore<GlobalUtilProvider.GlobalVmState>{
        return DefaultStore(
            initialState = state,
            reducer = GlobalUtilProvider().globalReducer
        )
    }

    @Provides
    fun provideGlobalDispatcher(
        defaultStore: DefaultStore<GlobalUtilProvider.GlobalVmState>,
        getAllAccountsInteractor: GetAllAccountsInteractor,
        getAllCategoriesInteractor: GetAllCategoriesInteractor
    )
    : GlobalActionDispatcher {
        return GlobalActionDispatcher(
            defaultStore,
            getAllAccountsInteractor,
            getAllCategoriesInteractor
        )
    }
}