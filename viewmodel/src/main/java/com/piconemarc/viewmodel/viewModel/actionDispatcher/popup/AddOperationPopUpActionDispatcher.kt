package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.account.UpdateAccountBalanceInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.core.domain.interactor.operation.AddNewOperationInteractor
import com.piconemarc.core.domain.interactor.operation.UpdateOperationPaymentIdInteractor
import com.piconemarc.core.domain.interactor.operation.UpdateOperationTransferIdInteractor
import com.piconemarc.core.domain.interactor.payment.AddNewPaymentInteractor
import com.piconemarc.core.domain.interactor.transfer.AddNewTransferInteractor
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.model.entity.TransferUiModel
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.launchCatchingError
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.addOperationPopUpUiState
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.myAccountDetailScreenUiState
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddOperationPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val getAllCategoriesInteractor: GetAllCategoriesInteractor,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
    private val addNewOperationInteractor: AddNewOperationInteractor,
    private val updateAccountBalanceInteractor: UpdateAccountBalanceInteractor,
    private val addNewPaymentInteractor: AddNewPaymentInteractor,
    private val updateOperationPaymentIdInteractor: UpdateOperationPaymentIdInteractor,
    private val addNewTransferInteractor: AddNewTransferInteractor,
    private val updateOperationTransferIdInteractor: UpdateOperationTransferIdInteractor
) : ActionDispatcher {

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(GlobalAction.UpdateAddOperationPopUpState(action))
        when (action) {
            is AppActions.AddOperationPopUpAction.SelectOptionIcon -> {
                //option selector on change redispatch action in same class to trigger interactors
                when (action.selectedIcon) {
                    is PAMIconButtons.Payment -> {
                        this.dispatchAction(
                            AppActions.AddOperationPopUpAction.ExpandPaymentOption,
                            scope
                        )
                        this.dispatchAction(
                            AppActions.AddOperationPopUpAction.ExpandRecurrentOption,
                            scope
                        )
                    }

                    is PAMIconButtons.Transfer -> {
                        this.dispatchAction(
                            AppActions.AddOperationPopUpAction.ExpandTransferOption,
                            scope
                        )
                        this.dispatchAction(
                            AppActions.AddOperationPopUpAction.CloseRecurrentOption,
                            scope
                        )
                    }
                    else -> {
                        this.dispatchAction(
                            AppActions.AddOperationPopUpAction.CollapseOptions,
                            scope
                        )
                        this.dispatchAction(
                            AppActions.AddOperationPopUpAction.CloseRecurrentOption,
                            scope
                        )
                    }

                }
            }
            is AppActions.AddOperationPopUpAction.InitPopUp ->
                scope.launchCatchingError(
                    block = {
                        getAllCategoriesInteractor.getAllCategories().collect {
                            updateState(
                                GlobalAction.UpdateAddOperationPopUpState(
                                    AppActions.AddOperationPopUpAction.UpdateCategoriesList(
                                        it
                                    )
                                )
                            )
                        }
                    }
                )
            is AppActions.AddOperationPopUpAction.ExpandTransferOption -> {
                scope.launchCatchingError(
                    block = {
                        updateState(
                            GlobalAction.UpdateAddOperationPopUpState(
                                AppActions.AddOperationPopUpAction.UpdateAccountList(
                                    getAllAccountsInteractor.getAllAccounts().filter {
                                        it.id != myAccountDetailScreenUiState.selectedAccount.id
                                    }
                                )
                            )
                        )
                    }
                )
            }

            is AppActions.AddOperationPopUpAction.AddNewOperation -> {
                var operationId: Long
                var paymentId: Long
                var beneficiaryOperationId: Long
                var transferId: Long

                //if no error on name and amount
                if (!addOperationPopUpUiState.isOperationNameError
                    && !addOperationPopUpUiState.isOperationAmountError
                ) {
                    //Check selected icon ------------------------------------------------------------
                    when (addOperationPopUpUiState.addPopUpOptionSelectedIcon) {
                        is PAMIconButtons.Operation -> {
                            //create operation and push it, update account balance
                            scope.launchCatchingError(
                                block = { addNewOperationInteractor.addNewOperation(action.operation) }
                            )
                            scope.launchCatchingError(
                                block = {
                                    updateAccountBalanceInteractor.updateAccountBalance(
                                        myAccountDetailScreenUiState.selectedAccount.updateAccountBalance(
                                            action.operation
                                        )
                                    )
                                },
                                doOnSuccess = { closePopUp() }
                            )
                        }
                        is PAMIconButtons.Payment -> {
                            scope.launchCatchingError(
                                block = {
                                    // create operation and push it
                                    operationId =
                                        addNewOperationInteractor.addNewOperation(action.operation)
                                    updateAccountBalanceInteractor.updateAccountBalance(
                                        myAccountDetailScreenUiState.selectedAccount.updateAccountBalance(
                                            action.operation
                                        )
                                    )
                                    //create payment and push it with related operation id
                                    paymentId = addNewPaymentInteractor.addNewPayment(
                                        PaymentUiModel(
                                            name = action.operation.name + "Payment",
                                            operationId = operationId,
                                            accountId = myAccountDetailScreenUiState.selectedAccount.id,
                                            endDate = try {
                                                SimpleDateFormat("MMMM/yyyy", Locale.FRANCE).parse(
                                                    addOperationPopUpUiState.enDateSelectedMonth
                                                            + "/" + addOperationPopUpUiState.endDateSelectedYear,
                                                )
                                            } catch (e: ParseException) {
                                                null
                                            }
                                        )
                                    )
                                    //update operation with paymentId and operationId
                                    updateOperationPaymentIdInteractor.updateOperationPaymentId(
                                        operationId,
                                        paymentId
                                    )
                                },
                                doOnSuccess = { closePopUp() }
                            )
                        }
                        is PAMIconButtons.Transfer -> {
                            if (!addOperationPopUpUiState.isBeneficiaryAccountError) {
                                //if no error on beneficiary account
                                // update operation with sender and beneficiary values
                                val senderOperation = action.operation.copy(
                                    amount = action.operation.senderAmount
                                )
                                val beneficiaryOperation = action.operation.copy(
                                    accountId = addOperationPopUpUiState.beneficiaryAccount.id,
                                    amount = action.operation.beneficiaryAmount
                                )
                                scope.launchCatchingError(
                                    block = {
                                        //push sender and beneficiary operation, store id
                                        operationId = addNewOperationInteractor.addNewOperation(
                                            senderOperation
                                        )
                                        beneficiaryOperationId = addNewOperationInteractor.addNewOperation(
                                                beneficiaryOperation
                                            )
                                        //update balance
                                        updateAccountBalanceInteractor.updateAccountBalance(
                                            myAccountDetailScreenUiState.selectedAccount.updateAccountBalance(
                                                senderOperation
                                            ),
                                            addOperationPopUpUiState.beneficiaryAccount.updateAccountBalance(
                                                beneficiaryOperation
                                            )
                                        )
                                        //create transfer and push it ,store id
                                        transferId = addNewTransferInteractor.addNewTransfer(
                                                TransferUiModel(
                                                    name = action.operation.name + "Transfer",
                                                    senderOperationId = operationId,
                                                    beneficiaryOperationId = beneficiaryOperationId
                                                )
                                            )
                                        //update operations with transferId
                                        updateOperationTransferIdInteractor.updateOperationTransferId(
                                            transferId,
                                            operationId,
                                            beneficiaryOperationId
                                        )
                                    },
                                    doOnSuccess = { closePopUp() }
                                )
                            }
                        }
                        else -> { closePopUp() }
                    }
                }
            }
        }
    }

    private fun closePopUp() {
        updateState(
            GlobalAction.UpdateAddOperationPopUpState(
                AppActions.AddOperationPopUpAction.ClosePopUp
            )
        )
    }
}