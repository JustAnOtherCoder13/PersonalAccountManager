package com.piconemarc.viewmodel.viewModel.utils

import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.*
import java.util.*

object AppActions {

    //SCREEN----------------------------------------------------------------------------------------

    sealed class BaseAppScreenAction : UiAction {
        object InitScreen : BaseAppScreenAction()
        data class SelectInterlayer(val selectedInterlayerButton: PAMIconButtons) :
            BaseAppScreenAction()

        data class UpdateInterlayerTiTle(val interlayerTitle: Int) : BaseAppScreenAction()
        data class UpdateAccounts(val allAccounts: List<AccountUiModel>) : BaseAppScreenAction()
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

        data class GetSelectedOperation(val operation: OperationUiModel) :
            MyAccountDetailScreenAction()

        data class UpdateOperationMessage(val message: String) : MyAccountDetailScreenAction()
    }

    sealed class PaymentScreenAction : UiAction {
        object InitScreen : PaymentScreenAction()
        object CloseScreen : PaymentScreenAction()
        data class UpdateAllAccounts(val allAccounts: List<AccountWithRelatedPaymentUiModel>) :
            PaymentScreenAction()
    }


    //POP UP----------------------------------------------------------------------------------------
    sealed class AddOpePopupAction : UiAction {
        data class InitPopUp(val isOnPaymentScreen: Boolean = false, val selectedAccountId: Long) :
            AddOpePopupAction()

        object ClosePopUp : AddOpePopupAction()

        data class OnOperationIconSelected(
            val isAddOperation: Boolean,
            val operationAmount: String
        ) : AddOpePopupAction()

        data class OnPaymentIconSelected(val isAddOperation: Boolean, val operationAmount: String) :
            AddOpePopupAction()

        data class OnTransferIconSelected(val operationAmount: String) : AddOpePopupAction()
        data class OnRecurrentOptionSelected(val isRecurrent: Boolean) : AddOpePopupAction()

        data class OnAddOrMinusSelected(val isAddOperation: Boolean, val operationAmount: String) :
            AddOpePopupAction()

        data class OnFillOperationName(val operationName: String) : AddOpePopupAction()
        data class OnFillOperationAmount(val operationAmount: String) : AddOpePopupAction()
        data class OnSelectCategory(val selectedCategory: CategoryUiModel) : AddOpePopupAction()
        data class OnPaymentEndDateSelected(val selectedMonthOrYear: String) : AddOpePopupAction()
        data class OnBeneficiaryAccountSelected(val beneficiaryAccount: AccountUiModel) :
            AddOpePopupAction()

        data class AddOperation(
            val newOperation: OperationUiModel,
            val isOperationError : Boolean
        ) : AddOpePopupAction()

        data class AddPayment(
            val isOnPaymentScreen: Boolean,
            val newOperation : OperationUiModel,
            val isOperationError : Boolean,
            val paymentEndDate : Pair<String,String>
        ) : AddOpePopupAction()

        object AddTransfer : AddOpePopupAction()

        data class LaunchIoThread(
            val allCategories: List<CategoryUiModel>? = null,
            val allAccounts: List<AccountUiModel>? = null,
            val selectedAccount: AccountUiModel? = null
        ) : AddOpePopupAction()

        data class CheckError(
            val operationName: String,
            val operationAmount: String,
            val isRecurrentEndDateError: Boolean = true,
            val isBeneficiaryAccountError: Boolean = false
        ):AddOpePopupAction()
    }

    sealed class DeleteOperationPopUpAction : UiAction {
        data class InitPopUp<T : BaseOperation>(val operationToDelete: T) :
            DeleteOperationPopUpAction()

        object ClosePopUp : DeleteOperationPopUpAction()
        data class UpdateOperationToDelete<T : BaseOperation>(val operationToDelete: T) :
            DeleteOperationPopUpAction()

        data class DeleteOperation<T : BaseOperation>(val operationToDelete: T) :
            DeleteOperationPopUpAction()

        data class DeletePayment(val paymentToDelete: PaymentUiModel) : DeleteOperationPopUpAction()
        data class DeleteTransfer(val transferToDelete: TransferUiModel) :
            DeleteOperationPopUpAction()

        data class UpdateTransferRelatedAccount(val transferRelatedAccount: AccountUiModel) :
            DeleteOperationPopUpAction()

        data class UpdateIsDeletedPermanently(val isRelatedOperationDeleted: Boolean) :
            DeleteOperationPopUpAction()
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