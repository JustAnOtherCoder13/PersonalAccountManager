package com.piconemarc.viewmodel.viewModel

import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.*
import com.piconemarc.viewmodel.UiAction

object AppActions {

    //SCREEN----------------------------------------------------------------------------------------

    sealed class BaseAppScreenAction : UiAction {
        object InitScreen : BaseAppScreenAction()
        object CloseApp : BaseAppScreenAction()
        data class SelectInterlayer(val selectedInterlayerButton: PAMIconButtons) :
            BaseAppScreenAction()

        data class UpdateInterlayerTiTle(val interlayerTitle: Int) : BaseAppScreenAction()
        data class UpdateAccounts(val allAccountUis: List<AccountUiModel>) : BaseAppScreenAction()
        data class UpdateFooterBalance(val allAccountUis: List<AccountUiModel>) :
            BaseAppScreenAction()

        data class UpdateFooterRest(val allAccountUis: List<AccountUiModel>) : BaseAppScreenAction()
        data class UpdateFooterTitle(val footerTitle: String) : BaseAppScreenAction()
    }

    sealed class MyAccountScreenAction : UiAction {
        object InitScreen : MyAccountScreenAction()
        object CloseScreen : MyAccountScreenAction()
        data class UpdateAccountList(val accountUiList: List<AccountUiModel>) :
            MyAccountScreenAction()
    }

    sealed class MyAccountDetailScreenAction : UiAction {
        data class InitScreen(val selectedAccount: AccountUiModel) : MyAccountDetailScreenAction()
        object CloseScreen : MyAccountDetailScreenAction()
        data class UpdateAccountMonthlyOperations(val accountMonthlyOperations: List<OperationUiModel>) :
            MyAccountDetailScreenAction()

        data class UpdateSelectedAccount(val account: AccountUiModel) :
            MyAccountDetailScreenAction()
    }


    //POP UP----------------------------------------------------------------------------------------
    sealed class AddOperationPopUpAction : UiAction {
        object InitPopUp : AddOperationPopUpAction()
        object ExpandPaymentOption : AddOperationPopUpAction()
        object CollapseOptions : AddOperationPopUpAction()
        object ExpandTransferOption : AddOperationPopUpAction()
        object ExpandRecurrentOption : AddOperationPopUpAction()
        object CloseRecurrentOption : AddOperationPopUpAction()
        object ClosePopUp : AddOperationPopUpAction()
        data class SelectCategory(val category: CategoryModel) : AddOperationPopUpAction()
        data class FillOperationName(val operation: String) : AddOperationPopUpAction()
        data class FillOperationAmount(val amount: String) : AddOperationPopUpAction()
        data class UpdateCategoriesList(val allCategories: List<CategoryModel>) :
            AddOperationPopUpAction()

        data class UpdateAccountList(val accountList: List<AccountUiModel>) :
            AddOperationPopUpAction()

        data class SelectEndDateYear(val selectedEndDateYear: String) : AddOperationPopUpAction()
        data class SelectEndDateMonth(val selectedEndDateMonth: String) : AddOperationPopUpAction()

        data class SelectBeneficiaryAccount(val beneficiaryAccountUi: AccountUiModel) :
            AddOperationPopUpAction()

        data class SelectOptionIcon(val selectedIcon: PAMIconButtons) : AddOperationPopUpAction()
        data class SelectAddOrMinus(val isAddOperation: Boolean) : AddOperationPopUpAction()
        data class AddNewOperation(val operation: OperationUiModel) : AddOperationPopUpAction()
    }

    sealed class DeleteOperationPopUpAction : UiAction {
        data class InitPopUp(val operationToDelete: OperationUiModel) : DeleteOperationPopUpAction()
        object ClosePopUp : DeleteOperationPopUpAction()
        data class UpdateOperationToDelete(val operationToDelete: OperationUiModel) :
            DeleteOperationPopUpAction()

        data class DeleteOperation(val operationToDelete: OperationUiModel) :
            DeleteOperationPopUpAction()
        data class DeletePayment(val paymentToDelete: PaymentUiModel) : DeleteOperationPopUpAction()
        data class DeleteTransfer(val transferToDelete : TransferUiModel) :DeleteOperationPopUpAction()
        data class UpdateTransferRelatedAccount(val transferRelatedAccount : AccountUiModel) : DeleteOperationPopUpAction()
        data class UpdateIsDeletedPermanently(val isDeletedPermanently : Boolean) : DeleteOperationPopUpAction()
    }

    sealed class DeleteAccountAction : UiAction {
        data class InitPopUp(val accountUiToDelete: AccountUiModel) : DeleteAccountAction()
        object ClosePopUp : DeleteAccountAction()
        data class DeleteAccount(val accountUiToDelete: AccountUiModel) : DeleteAccountAction()
        data class UpdateAccountToDelete(val accountUiToDelete: AccountUiModel) :
            DeleteAccountAction()
    }

    sealed class AddAccountPopUpAction : UiAction {
        object InitPopUp : AddAccountPopUpAction()
        object ClosePopUp : AddAccountPopUpAction()
        data class AddNewAccount(
            val accountName: String,
            val accountBalance: String,
            val accountOverdraft: String
        ) : AddAccountPopUpAction()

        data class FillAccountName(val accountName: String) : AddAccountPopUpAction()
        data class FillAccountBalance(val accountBalance: String) : AddAccountPopUpAction()
        data class FillAccountOverdraft(val accountOverdraft: String) : AddAccountPopUpAction()
    }


}