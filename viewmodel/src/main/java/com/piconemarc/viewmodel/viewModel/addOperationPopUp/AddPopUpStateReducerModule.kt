package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.viewmodel.viewModel.DefaultStore
import com.piconemarc.viewmodel.viewModel.Reducer
import com.piconemarc.viewmodel.viewModel.ReducerModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AddPopUpStateReducerModule
    : ReducerModule<AddOperationPopUpUtilsProvider.AddOperationPopUpVMState> {

    override val provideStateReducer: Reducer<AddOperationPopUpUtilsProvider.AddOperationPopUpVMState> =
        AddOperationPopUpUtilsProvider().providedReducer

    @Singleton
    @Provides
    override fun provideStore(state: AddOperationPopUpUtilsProvider.AddOperationPopUpVMState): DefaultStore<AddOperationPopUpUtilsProvider.AddOperationPopUpVMState> {
        return DefaultStore(
            initialState = state,
            reducer = provideStateReducer
        )
    }

    @Provides
    override fun provideState(): AddOperationPopUpUtilsProvider.AddOperationPopUpVMState =
        AddOperationPopUpUtilsProvider().providedVmState

    @Provides
    fun provideAddOperationPopUpActionDispatcher(
        getAllCategoriesInteractor: GetAllCategoriesInteractor,
        getAllAccountsInteractor: GetAllAccountsInteractor,
        addOperationPopUpStore: DefaultStore<AddOperationPopUpUtilsProvider.AddOperationPopUpVMState>
    ): AddOperationPopUpActionDispatcher {
        return AddOperationPopUpActionDispatcher(
            getAllCategoriesInteractor,
            getAllAccountsInteractor,
            addOperationPopUpStore
        )
    }

}