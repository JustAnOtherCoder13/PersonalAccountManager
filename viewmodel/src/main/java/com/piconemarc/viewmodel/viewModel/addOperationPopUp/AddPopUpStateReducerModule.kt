package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import com.piconemarc.core.domain.*
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.viewModel.DefaultStore
import com.piconemarc.viewmodel.viewModel.Reducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AddPopUpStateReducerModule {

    @Provides
    fun provideAddOperationScreenEvent(
        getAllCategoriesInteractor: GetAllCategoriesInteractor,
        getAllAccountsInteractor: GetAllAccountsInteractor,
        addOperationPopUpStore: DefaultStore<AddOperationPopUpGlobalState>
    ): AddOperationPopUpActionDispatcher {
        return AddOperationPopUpActionDispatcher(
            getAllCategoriesInteractor,
            getAllAccountsInteractor,
            addOperationPopUpStore
        )
    }

    @Provides
    fun addOperationPopUpStore(addOperationPopUpGlobalState: AddOperationPopUpGlobalState): DefaultStore<AddOperationPopUpGlobalState> =
        DefaultStore(
            initialState = addOperationPopUpGlobalState,
            reducer = provideAddOperationStateReducer
        )

    @Provides
    fun provideGlobalState(): AddOperationPopUpGlobalState =
        AddOperationPopUpGlobalState()

    private val provideAddOperationStateReducer: Reducer<AddOperationPopUpGlobalState> =
        { old, action ->
            action as AddOperationPopUpAction
            when (action) {
                is AddOperationPopUpAction.Init -> old.copy(
                    isPopUpExpanded = true,
                    selectedCategory = PresentationDataModel(stringValue = "Category")
                )
                is AddOperationPopUpAction.ClosePopUp -> old.copy(
                    isPopUpExpanded = false,
                    isPaymentExpanded = false,
                    isRecurrentOptionExpanded = false,
                    isTransferExpanded = false
                )
                is AddOperationPopUpAction.SelectCategory -> old.copy(
                    selectedCategory = action.category
                )
                is AddOperationPopUpAction.FillOperationName -> old.copy(
                    operationName = action.operation
                )
                is AddOperationPopUpAction.UpdateCategoriesList -> old.copy(
                    operationCategories = action.allCategories
                )
                is AddOperationPopUpAction.ExpandPaymentOption -> old.copy(
                    isPaymentExpanded = true,
                    isTransferExpanded = false
                )
                is AddOperationPopUpAction.CollapseOptions -> old.copy(
                    isPaymentExpanded = false,
                    isRecurrentOptionExpanded = false,
                    isTransferExpanded = false
                )
                is AddOperationPopUpAction.ExpandRecurrentOption -> old.copy(
                    isRecurrentOptionExpanded = true
                )
                is AddOperationPopUpAction.CloseRecurrentOption -> old.copy(
                    isRecurrentOptionExpanded = false
                )
                is AddOperationPopUpAction.ExpandTransferOption -> old.copy(
                    isPaymentExpanded = true,
                    isTransferExpanded = true
                )
                is AddOperationPopUpAction.UpdateAccountList -> old.copy(
                    accountList = action.accountList
                )
            }
        }
}