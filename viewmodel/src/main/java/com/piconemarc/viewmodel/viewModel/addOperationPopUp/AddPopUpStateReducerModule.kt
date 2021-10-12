package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import com.piconemarc.core.domain.AddOperationPopUpGlobalState
import com.piconemarc.core.domain.Constants.BENEFICIARY_ACCOUNT_MODEL
import com.piconemarc.core.domain.Constants.CATEGORY_MODEL
import com.piconemarc.core.domain.Constants.MONTH_MODEL
import com.piconemarc.core.domain.Constants.OPERATION_MODEL
import com.piconemarc.core.domain.Constants.PAYMENT_MODEL
import com.piconemarc.core.domain.Constants.SELECTABLE_MONTHS_LIST
import com.piconemarc.core.domain.Constants.SELECTABLE_YEARS_LIST
import com.piconemarc.core.domain.Constants.SENDER_ACCOUNT_MODEL
import com.piconemarc.core.domain.Constants.TRANSFER_MODEL
import com.piconemarc.core.domain.Constants.YEAR_MODEL
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.viewModel.DefaultStore
import com.piconemarc.viewmodel.viewModel.Reducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

    @Singleton
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
                    selectedCategory = CATEGORY_MODEL,
                    addPopUpTitle = OPERATION_MODEL,
                    operationName = PresentationDataModel(),
                    operationAmount = PresentationDataModel(),
                )
                is AddOperationPopUpAction.ClosePopUp -> old.copy(
                    isPopUpExpanded = false,
                    isPaymentExpanded = false,
                    isRecurrentOptionExpanded = false,
                    isTransferExpanded = false,
                )
                is AddOperationPopUpAction.SelectCategory -> old.copy(
                    selectedCategory = action.category
                )
                is AddOperationPopUpAction.FillOperationName -> old.copy(
                    operationName = action.operation
                )
                is AddOperationPopUpAction.UpdateCategoriesList -> old.copy(
                    allCategories = action.allCategories
                )
                is AddOperationPopUpAction.ExpandPaymentOption -> old.copy(
                    isPaymentExpanded = true,
                    isTransferExpanded = false,
                    addPopUpTitle = PAYMENT_MODEL
                )
                is AddOperationPopUpAction.CollapseOptions -> old.copy(
                    isPaymentExpanded = false,
                    isRecurrentOptionExpanded = false,
                    isTransferExpanded = false,
                    addPopUpTitle = OPERATION_MODEL
                )
                is AddOperationPopUpAction.ExpandRecurrentOption -> old.copy(
                    isRecurrentOptionExpanded = true,
                    selectableEndDateYears = SELECTABLE_YEARS_LIST,
                    endDateSelectedYear = YEAR_MODEL,
                    selectableEndDateMonths = SELECTABLE_MONTHS_LIST,
                    enDateSelectedMonth = MONTH_MODEL
                )
                is AddOperationPopUpAction.CloseRecurrentOption -> old.copy(
                    isRecurrentOptionExpanded = false
                )
                is AddOperationPopUpAction.ExpandTransferOption -> old.copy(
                    isPaymentExpanded = true,
                    isTransferExpanded = true,
                    addPopUpTitle = TRANSFER_MODEL,
                    senderAccount = SENDER_ACCOUNT_MODEL,
                    beneficiaryAccount = BENEFICIARY_ACCOUNT_MODEL
                )
                is AddOperationPopUpAction.UpdateAccountList -> old.copy(
                    allAccounts = action.accountList
                )
                is AddOperationPopUpAction.FillOperationAmount -> old.copy(
                    operationAmount = action.amount
                )
                is AddOperationPopUpAction.SelectEndDateYear -> old.copy(
                    endDateSelectedYear = action.selectedEndDateYear
                )
                is AddOperationPopUpAction.SelectEndDateMonth -> old.copy(
                    enDateSelectedMonth = action.selectedEndDateMonth
                )
                is AddOperationPopUpAction.SelectSenderAccount  -> old.copy(
                    senderAccount = action.senderAccount
                )
                is AddOperationPopUpAction.SelectBeneficiaryAccount -> old.copy(
                    beneficiaryAccount = action.beneficiaryAccount
                )
            }
        }
}