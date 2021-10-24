package com.piconemarc.viewmodel.viewModel

import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.CategoryModel
import com.piconemarc.model.entity.OperationModel
import com.piconemarc.viewmodel.UiAction

object AppActions {

    //SCREEN----------------------------------------------------------------------------------------

    sealed class BaseAppScreenAction : UiAction {
        object InitScreen : BaseAppScreenAction()
        object CloseApp : BaseAppScreenAction()
        data class SelectInterlayer(val selectedInterlayerButton: PAMIconButtons) : BaseAppScreenAction()
        data class UpdateInterlayerTiTle(val interlayerTitle : Int) : BaseAppScreenAction()
        data class UpdateAccounts(val allAccounts: List<AccountModel>) : BaseAppScreenAction()
        data class UpdateFooterBalance(val allAccounts: List<AccountModel>) : BaseAppScreenAction()
        data class UpdateFooterRest(val allAccounts: List<AccountModel>) : BaseAppScreenAction()
        data class UpdateFooterTitle(val footerTitle: String) : BaseAppScreenAction()
    }

    sealed class MyAccountScreenAction : UiAction{
        object InitScreen : MyAccountScreenAction()
        object CloseScreen : MyAccountScreenAction()
        data class UpdateAccountList(val accountList : List<AccountModel>): MyAccountScreenAction()
    }

    sealed class MyAccountDetailScreenAction : UiAction{
        data class InitScreen(val selectedAccount: AccountModel) : MyAccountDetailScreenAction()
        object CloseScreen : MyAccountDetailScreenAction()
        data class UpdateAccountMonthlyOperations(val accountMonthlyOperations : List<OperationModel>): MyAccountDetailScreenAction()
        data class UpdateAccountBalance(val accountBalance : String): MyAccountDetailScreenAction()
        data class UpdateAccountRest(val accountRest : String): MyAccountDetailScreenAction()
        data class UpdateAccountName(val account : AccountModel):MyAccountDetailScreenAction()
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
        data class UpdateCategoriesList(val allCategories: List<CategoryModel>) : AddOperationPopUpAction()
        data class UpdateAccountList(val accountList: List<AccountModel>) : AddOperationPopUpAction()
        data class SelectEndDateYear(val selectedEndDateYear: String) : AddOperationPopUpAction()
        data class SelectEndDateMonth(val selectedEndDateMonth: String) : AddOperationPopUpAction()
        data class SelectSenderAccount(val senderAccount: AccountModel) : AddOperationPopUpAction()
        data class SelectBeneficiaryAccount(val beneficiaryAccount: AccountModel) : AddOperationPopUpAction()
        data class SelectOptionIcon(val selectedIcon: PAMIconButtons) : AddOperationPopUpAction()
        data class SelectAddOrMinus(val isAddOperation: Boolean) : AddOperationPopUpAction()
        data class AddNewOperation(val operation : OperationModel) : AddOperationPopUpAction()
    }

    sealed class DeleteOperationPopUpAction : UiAction{
        data class InitPopUp(val operationToDelete : OperationModel): DeleteOperationPopUpAction()
        object ClosePopUp : DeleteOperationPopUpAction()
        data class UpdateOperationToDelete(val operationToDelete: OperationModel) : DeleteOperationPopUpAction()
        data class DeleteOperation(val operationToDelete: OperationModel) : DeleteOperationPopUpAction()
    }

    sealed class DeleteAccountAction : UiAction {
        data class InitPopUp(val accountToDelete: AccountModel) : DeleteAccountAction()
        object ClosePopUp : DeleteAccountAction()
        data class DeleteAccount(val accountToDelete: AccountModel) : DeleteAccountAction()
        data class UpdateAccountToDelete(val accountToDelete :AccountModel) : DeleteAccountAction()
    }

    sealed class AddAccountPopUpAction : UiAction {
        object InitPopUp : AddAccountPopUpAction()
        object ClosePopUp : AddAccountPopUpAction()
        data class AddNewAccount(
            val accountName: String,
            val accountBalance: String,
            val accountOverdraft: String
        ): AddAccountPopUpAction()
        data class FillAccountName(val accountName : String): AddAccountPopUpAction()
        data class FillAccountBalance (val accountBalance : String): AddAccountPopUpAction()
        data class FillAccountOverdraft (val accountOverdraft : String): AddAccountPopUpAction()
    }



}