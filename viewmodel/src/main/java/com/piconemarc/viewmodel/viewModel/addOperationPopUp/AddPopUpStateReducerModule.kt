package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import com.piconemarc.core.domain.AddOperationPopUpGlobalState
import com.piconemarc.core.domain.AddOperationPopUpOperationOptionState
import com.piconemarc.core.domain.AddOperationPopUpPaymentOptionState
import com.piconemarc.core.domain.AddOperationPopUpTransferOptionState
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.model.entity.CategoryModel
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
    fun provideOperationState(): AddOperationPopUpOperationOptionState {
        return AddOperationPopUpOperationOptionState(
            isPopUpExpanded = false,
            operationCategories = listOf(),
            selectedCategory = CategoryModel(),
            operationName = "",
            operationAmount = "",
        )
    }

    @Provides
    fun providePaymentState(): AddOperationPopUpPaymentOptionState =
        AddOperationPopUpPaymentOptionState(
            isPaymentExpanded = false,
            isRecurrentOptionExpanded = false,
            enDateSelectedMonth = "",
            endDateSelectedYear = ""
        )

    @Provides
    fun provideTransferState(): AddOperationPopUpTransferOptionState =
        AddOperationPopUpTransferOptionState(
            isTransferExpanded = false,
            accountList = listOf(),
            senderAccount = "",
            beneficiaryAccount = ""
        )

    @Provides
    fun provideAddOperationGlobalState(
        addOperationPopUpOperationOptionState: AddOperationPopUpOperationOptionState,
        addOperationPopUpPaymentOptionState: AddOperationPopUpPaymentOptionState,
        addOperationPopUpTransferOptionState: AddOperationPopUpTransferOptionState
    ): AddOperationPopUpGlobalState =
        AddOperationPopUpGlobalState(
            addOperationPopUpOperationOptionState,
            addOperationPopUpPaymentOptionState,
            addOperationPopUpTransferOptionState
        )

    private val operationStateReducer: Reducer<AddOperationPopUpOperationOptionState> =
        { old, action ->
            when (action) {
                is AddOperationPopUpAction.Init -> old.copy(
                    isPopUpExpanded = true,
                    selectedCategory = CategoryModel(name = "Category")
                )
                is AddOperationPopUpAction.ClosePopUp -> old.copy(isPopUpExpanded = false)
                is AddOperationPopUpAction.SelectCategory -> old.copy(selectedCategory = action.category)
                is AddOperationPopUpAction.FillOperationName -> old.copy(operationName = action.operationName)
                is AddOperationPopUpAction.UpdateCategoriesList -> old.copy(operationCategories = action.allCategories)
                else -> old
            }
        }

    private val paymentStateReducer: Reducer<AddOperationPopUpPaymentOptionState> = { old, action ->
        when (action) {
            is AddOperationPopUpAction.ExpandPaymentOption -> old.copy(isPaymentExpanded = true)
            is AddOperationPopUpAction.CollapseOptions -> old.copy(
                isPaymentExpanded = false,
                isRecurrentOptionExpanded = false
            )
            is AddOperationPopUpAction.ExpandRecurrentOption -> old.copy(isRecurrentOptionExpanded = true)
            is AddOperationPopUpAction.CloseRecurrentOption -> old.copy(isRecurrentOptionExpanded = false)
            is AddOperationPopUpAction.ExpandTransferOption -> old.copy(isPaymentExpanded = true)
            is AddOperationPopUpAction.ClosePopUp -> old.copy(
                isPaymentExpanded = false,
                isRecurrentOptionExpanded = false
            )
            else -> old
        }
    }

    private val transferStateReducer: Reducer<AddOperationPopUpTransferOptionState> =
        { old, action ->
            when (action) {
                is AddOperationPopUpAction.ExpandTransferOption -> old.copy(isTransferExpanded = true)
                is AddOperationPopUpAction.CollapseOptions -> old.copy(isTransferExpanded = false)
                is AddOperationPopUpAction.ClosePopUp -> old.copy(isTransferExpanded = false)
                is AddOperationPopUpAction.ExpandPaymentOption -> old.copy(isTransferExpanded = false)
                is AddOperationPopUpAction.UpdateAccountList -> old.copy(accountList = action.accountList)
                else -> old
            }
        }

    private val addOperationStateReducer: Reducer<AddOperationPopUpGlobalState> = { old, action ->
        AddOperationPopUpGlobalState(
            addOperationPopUpOperationOptionState = operationStateReducer(
                old.addOperationPopUpOperationOptionState,
                action
            ),
            addOperationPopUpPaymentOptionState = paymentStateReducer(
                old.addOperationPopUpPaymentOptionState,
                action
            ),
            addOperationPopUpTransferOptionState = transferStateReducer(
                old.addOperationPopUpTransferOptionState,
                action
            )
        )
    }

    @Provides
    fun addOperationPopUpStore(addOperationPopUpGlobalState: AddOperationPopUpGlobalState): DefaultStore<AddOperationPopUpGlobalState> =
        DefaultStore(
            initialState = addOperationPopUpGlobalState,
            reducer = addOperationStateReducer
        )

    @Provides
    fun provideAddOperationScreenEvent(
        getAllCategoriesInteractor: GetAllCategoriesInteractor,
        getAllAccountsInteractor: GetAllAccountsInteractor,
        addOperationPopUpStore: DefaultStore<AddOperationPopUpGlobalState>
    ): AddOperationScreenEvent {
        return AddOperationScreenEvent(
            getAllCategoriesInteractor,
            getAllAccountsInteractor,
            addOperationPopUpStore
        )
    }

}