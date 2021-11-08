package com.piconemarc.viewmodel.viewModel.utils

import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.*

object AppActions {

    //SCREEN----------------------------------------------------------------------------------------

    sealed class BaseAppScreenAction : UiAction {
        object InitScreen : BaseAppScreenAction()
        object CloseApp : BaseAppScreenAction()
        data class SelectInterlayer(val selectedInterlayerButton: PAMIconButtons) :
            BaseAppScreenAction()

        data class UpdateInterlayerTiTle(val interlayerTitle: Int) : BaseAppScreenAction()
        data class UpdateAccounts(val allAccountUis: List<AccountUiModel>) : BaseAppScreenAction()
        data class UpdateFooterBalance(val allAccounts: List<AccountUiModel>) :
            BaseAppScreenAction()

        data class UpdateFooterRest(val allAccountUis: List<AccountUiModel>) : BaseAppScreenAction()
    }

    sealed class MyAccountScreenAction : UiAction {
        object InitScreen : MyAccountScreenAction()
        object CloseScreen : MyAccountScreenAction()
        data class UpdateAccountList(val accountList: List<AccountUiModel>) :
            MyAccountScreenAction()
    }

    sealed class MyAccountDetailScreenAction : UiAction {
        data class InitScreen(val selectedAccountId: String) : MyAccountDetailScreenAction()
        object CloseScreen : MyAccountDetailScreenAction()
        data class UpdateAccountMonthlyOperations(val accountMonthlyOperations: List<OperationUiModel>) :
            MyAccountDetailScreenAction()

        data class UpdateSelectedAccount(val account: AccountUiModel) :
            MyAccountDetailScreenAction()
        data class GetSelectedOperation(val operation: OperationUiModel) : MyAccountDetailScreenAction()
        data class UpdateOperationMessage(val message : String) : MyAccountDetailScreenAction()
    }

    sealed class PaymentScreenAction : UiAction {
        object InitScreen : PaymentScreenAction()
        object CloseScreen : PaymentScreenAction()
        data class UpdateAllAccounts(val allAccounts : List<AccountWithRelatedPaymentUiModel>) : PaymentScreenAction()
    }


    //POP UP----------------------------------------------------------------------------------------
    sealed class AddOperationPopUpAction : UiAction {
        data class InitPopUp(val isOnPaymentScreen : Boolean = false, val selectedAccountId : Long) : AddOperationPopUpAction()
        object ExpandPaymentOption : AddOperationPopUpAction()
        object CollapseOptions : AddOperationPopUpAction()
        object ExpandTransferOption : AddOperationPopUpAction()
        object ExpandRecurrentOption : AddOperationPopUpAction()
        object CloseRecurrentOption : AddOperationPopUpAction()
        object ClosePopUp : AddOperationPopUpAction()
        data class SelectCategory(val category: CategoryUiModel) : AddOperationPopUpAction()
        data class FillOperationName(val operation: String) : AddOperationPopUpAction()
        data class FillOperationAmount(val amount: String) : AddOperationPopUpAction()
        data class UpdateCategoriesList(val allCategories: List<CategoryUiModel>) :
            AddOperationPopUpAction()

        data class UpdateAccountList(val accountList: List<AccountUiModel>) :
            AddOperationPopUpAction()

        data class SelectEndDateYear(val selectedEndDateYear: String) : AddOperationPopUpAction()
        data class SelectEndDateMonth(val selectedEndDateMonth: String) : AddOperationPopUpAction()

        data class SelectBeneficiaryAccount(val beneficiaryAccountUi: AccountUiModel) :
            AddOperationPopUpAction()

        data class SelectOptionIcon(val selectedIcon: PAMIconButtons) : AddOperationPopUpAction()
        data class SelectAddOrMinus(val isAddOperation: Boolean) : AddOperationPopUpAction()
        data class AddNewOperation<T : BaseOperation>(val operation: T) : AddOperationPopUpAction()
        data class UpdateIsPaymentStartThisMonth( val isPaymentStartThisMonth: Boolean) : AddOperationPopUpAction()
    }

    sealed class DeleteOperationPopUpAction : UiAction {
        data class InitPopUp<T : BaseOperation>(val operationToDelete: T) : DeleteOperationPopUpAction()
        object ClosePopUp : DeleteOperationPopUpAction()
        data class UpdateOperationToDelete<T : BaseOperation>(val operationToDelete: T) :
            DeleteOperationPopUpAction()

        data class DeleteOperation<T : BaseOperation>(val operationToDelete: T) :
            DeleteOperationPopUpAction()
        data class DeletePayment(val paymentToDelete: PaymentUiModel) : DeleteOperationPopUpAction()
        data class DeleteTransfer(val transferToDelete : TransferUiModel) :
            DeleteOperationPopUpAction()
        data class UpdateTransferRelatedAccount(val transferRelatedAccount : AccountUiModel) : DeleteOperationPopUpAction()
        data class UpdateIsDeletedPermanently(val isRelatedOperationDeleted : Boolean) : DeleteOperationPopUpAction()
    }

    sealed class DeleteAccountAction : UiAction {
        data class InitPopUp(val accountUiToDelete: AccountUiModel) : DeleteAccountAction()
        object ClosePopUp : DeleteAccountAction()
        data class DeleteAccount(val accountUiToDelete: AccountUiModel) : DeleteAccountAction()
        data class UpdateAccountToDelete(val accountToDelete: AccountUiModel) :
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