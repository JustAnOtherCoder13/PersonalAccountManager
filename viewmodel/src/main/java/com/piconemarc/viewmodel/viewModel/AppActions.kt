package com.piconemarc.viewmodel.viewModel

import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.viewmodel.PAMIconButtons
import com.piconemarc.viewmodel.UiAction

object AppActions {

    sealed class GlobalAction : UiAction {
        data class UpdateBaseAppScreenVmState(val baseAction: UiAction) : GlobalAction()
        data class UpdateAddOperationPopUpState(val baseAction: UiAction) : GlobalAction()
        data class UpdateDeleteAccountPopUpState(val baseAction: UiAction) : GlobalAction()
        data class UpdateAddAccountPopUpState(val baseAction: UiAction) : GlobalAction()
    }

    sealed class BaseAppScreenAction : UiAction {
        object InitScreen : BaseAppScreenAction()
        data class SelectInterlayer(val selectedInterlayerButton: PAMIconButtons) :
            BaseAppScreenAction()

        data class UpdateAccounts(val allAccounts: List<AccountModel>) : BaseAppScreenAction()
        data class UpdateFooterBalance(val footerBalance: PresentationDataModel) :
            BaseAppScreenAction()

        data class UpdateFooterRest(val footerRest: PresentationDataModel) : BaseAppScreenAction()
        data class UpdateFooterTitle(val footerTitle: PresentationDataModel) : BaseAppScreenAction()
    }

    sealed class AddOperationPopUpAction : UiAction {
        object InitPopUp : AddOperationPopUpAction()
        object ExpandPaymentOption : AddOperationPopUpAction()
        object CollapseOptions : AddOperationPopUpAction()
        object ExpandTransferOption : AddOperationPopUpAction()
        object ExpandRecurrentOption : AddOperationPopUpAction()
        object CloseRecurrentOption : AddOperationPopUpAction()
        object ClosePopUp : AddOperationPopUpAction()
        data class SelectCategory(val category: PresentationDataModel) : AddOperationPopUpAction()
        data class FillOperationName(val operation: PresentationDataModel) :
            AddOperationPopUpAction()

        data class FillOperationAmount(val amount: PresentationDataModel) :
            AddOperationPopUpAction()

        data class UpdateCategoriesList(val allCategories: List<PresentationDataModel>) :
            AddOperationPopUpAction()

        data class UpdateAccountList(val accountList: List<PresentationDataModel>) :
            AddOperationPopUpAction()

        data class SelectEndDateYear(val selectedEndDateYear: PresentationDataModel) :
            AddOperationPopUpAction()

        data class SelectEndDateMonth(val selectedEndDateMonth: PresentationDataModel) :
            AddOperationPopUpAction()

        data class SelectSenderAccount(val senderAccount: PresentationDataModel) :
            AddOperationPopUpAction()

        data class SelectBeneficiaryAccount(val beneficiaryAccount: PresentationDataModel) :
            AddOperationPopUpAction()

        data class SelectOptionIcon(val selectedIcon: PAMIconButtons) :
            AddOperationPopUpAction()

        data class SelectAddOrMinus(val isAddOperation: Boolean) :
            AddOperationPopUpAction()
    }

    sealed class DeleteAccountAction : UiAction {
        data class InitPopUp(val accountName: PresentationDataModel) :
            DeleteAccountAction()

        object ClosePopUp : DeleteAccountAction()
        data class DeleteAccount(val accountId: Long) : DeleteAccountAction()

        data class UpdateAccountToDelete(val accountToDelete :AccountModel) : DeleteAccountAction()
    }

    sealed class AddAccountPopUpAction : UiAction {
        object InitPopUp : AddAccountPopUpAction()
        object ClosePopUp : AddAccountPopUpAction()
        data class AddNewAccount(
            val accountName: PresentationDataModel,
            val accountBalance: PresentationDataModel,
            val accountOverdraft: PresentationDataModel
        ): AddAccountPopUpAction()
        data class FillAccountName(val accountName : PresentationDataModel): AddAccountPopUpAction()
        data class FillAccountBalance (val accountBalance : PresentationDataModel): AddAccountPopUpAction()
        data class FillAccountOverdraft (val accountOverdraft : PresentationDataModel): AddAccountPopUpAction()
    }

}