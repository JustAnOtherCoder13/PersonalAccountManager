package com.piconemarc.viewmodel.viewModel.addOperationPopUp

import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.viewModel.ActionDispatcher
import com.piconemarc.viewmodel.viewModel.DefaultStore
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class AddOperationPopUpActionDispatcher @Inject constructor(
    private val getAllCategoriesInteractor: GetAllCategoriesInteractor,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
    override val store : DefaultStore<AddOperationPopUpUtilsProvider.AddOperationPopUpVMState>
) : ActionDispatcher<AddOperationPopUpUtilsProvider.AddOperationPopUpAction,AddOperationPopUpUtilsProvider.AddOperationPopUpVMState> {
    private var getCategoriesJob: Job? = null
    private var getAllAccountsJob: Job? = null

    override val subscriber = AddOperationPopUpUtilsProvider().providedSubscriber

    //action dispatcher
    override fun dispatchAction(action: AddOperationPopUpUtilsProvider.AddOperationPopUpAction) {
        when (action) {
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.InitPopUp -> initPopUp()
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ClosePopUp -> closePopUp()
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.CollapseOptions -> collapseOptions()
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ExpandPaymentOption -> expandPaymentOption()
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ExpandTransferOption -> expandTransferOption()
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ExpandRecurrentOption -> expandRecurrentOption()
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.CloseRecurrentOption -> collapseRecurrentOption()
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectCategory -> selectCategory(
                action.category
            )
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.FillOperationName -> fillOperationName(
                action.operation
            )
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.UpdateAccountList -> updateAccountsList(
                action.accountList
            )
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.UpdateCategoriesList -> updateCategoriesList(
                action.allCategories
            )
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.FillOperationAmount -> fillOperationAmount(
                action.amount
            )
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectEndDateYear -> selectEndDateYear(
                action.selectedEndDateYear
            )
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectEndDateMonth -> selectEndDateMonth(
                action.selectedEndDateMonth
            )
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectSenderAccount -> selectSenderAccount(
                action.senderAccount
            )
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectBeneficiaryAccount -> selectBeneficiaryAccount(
                action.beneficiaryAccount
            )
        }
    }

    // IMPLEMENT ACTIONS ---------------------------------------------------------------------------------


    private fun selectBeneficiaryAccount(beneficiaryAccount: PresentationDataModel) {
        store.dispatch(
            AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectBeneficiaryAccount(
                beneficiaryAccount
            )
        )
    }

    private fun selectSenderAccount(senderAccount: PresentationDataModel) {
        store.dispatch(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectSenderAccount(senderAccount))

    }

    private fun selectEndDateMonth(selectedEndDateMonth: PresentationDataModel) {
        store.dispatch(
            AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectEndDateMonth(
                selectedEndDateMonth
            )
        )
    }

    private fun selectEndDateYear(selectedEndDateYear: PresentationDataModel) {
        store.dispatch(
            AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectEndDateYear(
                selectedEndDateYear
            )
        )
    }

    private fun fillOperationAmount(amount: PresentationDataModel) {
        store.dispatch(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.FillOperationAmount(amount))
    }

    private fun initPopUp() {
        addSubscriber()
        //todo extract logic with error handler
        getCategoriesJob = scope.launch {
            getAllCategoriesInteractor.getAllCategoriesToDataUiModelList()
                .collect { allCategories ->
                    updateCategoriesList(allCategories)
                }
        }
        store.dispatch(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.InitPopUp)
    }

    private fun closePopUp() {
        store.dispatch(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ClosePopUp)
        removeSubscriber()
        scope.cancel()
    }

    private fun collapseOptions() {
        store.dispatch(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.CollapseOptions)
    }

    private fun expandPaymentOption() {
        store.dispatch(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ExpandPaymentOption)
    }

    private fun expandTransferOption() {
        getAllAccountsJob = scope.launch {
            getAllAccountsInteractor.getAllAccountsToPresentationDataModel().collect { allAccounts ->
                updateAccountsList(allAccounts)
            }
        }
        store.dispatch(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ExpandTransferOption)
    }

    private fun expandRecurrentOption() {
        store.dispatch(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ExpandRecurrentOption)

    }

    private fun collapseRecurrentOption() {
        store.dispatch(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.CloseRecurrentOption)
    }

    private fun selectCategory(selectedCategoryUiModel: PresentationDataModel) {
        store.dispatch(
            AddOperationPopUpUtilsProvider.AddOperationPopUpAction.SelectCategory(selectedCategoryUiModel)
        )
    }

    private fun fillOperationName(operation: PresentationDataModel) {
        store.dispatch(
            AddOperationPopUpUtilsProvider.AddOperationPopUpAction.FillOperationName(
                operation
            )
        )
    }

    private fun updateCategoriesList(updatedCategoriesList: List<PresentationDataModel>) {
        store.dispatch(
            AddOperationPopUpUtilsProvider.AddOperationPopUpAction.UpdateCategoriesList(updatedCategoriesList)
        )
    }

    private fun updateAccountsList(updatedAccountList: List<PresentationDataModel>) {
        store.dispatch(
            AddOperationPopUpUtilsProvider.AddOperationPopUpAction.UpdateAccountList(updatedAccountList)
        )
    }
}