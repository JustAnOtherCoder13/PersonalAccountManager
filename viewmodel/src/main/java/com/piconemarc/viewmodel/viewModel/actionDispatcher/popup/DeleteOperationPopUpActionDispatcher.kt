package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import android.util.Log
import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.account.UpdateAccountBalanceInteractor
import com.piconemarc.core.domain.interactor.operation.DeleteOperationInteractor
import com.piconemarc.core.domain.interactor.operation.GetOperationForIdInteractor
import com.piconemarc.core.domain.interactor.payment.DeletePaymentInteractor
import com.piconemarc.core.domain.interactor.payment.GetAllPaymentForAccountIdInteractor
import com.piconemarc.core.domain.interactor.payment.GetPaymentForIdInteractor
import com.piconemarc.core.domain.interactor.transfer.DeleteTransferInteractor
import com.piconemarc.core.domain.interactor.transfer.GetTransferForIdInteractor
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.model.entity.TransferUiModel
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.deleteOperationPopUpUiState
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.myAccountDetailScreenUiState
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class DeleteOperationPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val deleteOperationInteractor: DeleteOperationInteractor,
    private val getOperationForIdInteractor: GetOperationForIdInteractor,
    private val getAccountForIdInteractor: GetAccountForIdInteractor,
    private val updateAccountBalanceInteractor: UpdateAccountBalanceInteractor,
    private val getTransferForIdInteractor: GetTransferForIdInteractor,
    private val getPaymentForIdInteractor: GetPaymentForIdInteractor,
    private val deletePaymentInteractor: DeletePaymentInteractor,
    private val deleteTransferInteractor: DeleteTransferInteractor

) : ActionDispatcher {
    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        var transfer : TransferUiModel
        var transferRelatedOperation : OperationUiModel
        var payment : PaymentUiModel

        updateState(GlobalAction.UpdateDeleteOperationPopUpState(action))
        when (action) {
            is AppActions.DeleteOperationPopUpAction.InitPopUp -> {
                updateState(
                    GlobalAction.UpdateDeleteOperationPopUpState(
                        AppActions.DeleteOperationPopUpAction.UpdateOperationToDelete(action.operationToDelete)
                    )
                )
                if (action.operationToDelete.transferId != null)
                    scope.launch {
                        transfer = getTransferForIdInteractor.getTransferForId(action.operationToDelete.transferId!!)
                        transferRelatedOperation = getOperationForIdInteractor.getOperationForId(
                            if (action.operationToDelete.id == transfer.senderOperationId) {
                                transfer.beneficiaryOperationId
                            } else {
                                transfer.senderOperationId
                            }
                        )
                        updateState(
                            GlobalAction.UpdateDeleteOperationPopUpState(
                                AppActions.DeleteOperationPopUpAction.UpdateTransferRelatedAccount(
                                    getAccountForIdInteractor.getAccountForId(transferRelatedOperation.accountId)
                                )
                            )
                        )
                    }

            }
            is AppActions.DeleteOperationPopUpAction.DeleteOperation -> {
                if (action.operationToDelete.paymentId != null && deleteOperationPopUpUiState.isDeletedPermanently) {
                    scope.launch {
                        payment = getPaymentForIdInteractor.getPaymentForId(action.operationToDelete.paymentId!!)
                        deletePaymentInteractor.deletePayment(payment)
                        try {
                            deleteOperationInteractor.deleteOperation(action.operationToDelete)
                            updateAccountBalanceInteractor.updateAccountBalance(
                                myAccountDetailScreenUiState.selectedAccount.updateAccountBalance(
                                    action.operationToDelete.deleteOperation()
                                )
                            )
                            ClosePopUp()
                        } catch (e: Exception) {
                            Log.e("TAG", "dispatchAction: ", e)
                        }
                    }
                } else if (action.operationToDelete.transferId != null) {
                    scope.launch {
                        transfer = getTransferForIdInteractor.getTransferForId(action.operationToDelete.transferId!!)
                        transferRelatedOperation = getOperationForIdInteractor.getOperationForId(
                            if (action.operationToDelete.id == transfer.senderOperationId) {
                                transfer.beneficiaryOperationId
                            } else {
                                transfer.senderOperationId
                            }
                        )
                        val transferRelatedAccount = getAccountForIdInteractor.getAccountForId(transferRelatedOperation.accountId)

                        if (transfer.paymentId != null &&  deleteOperationPopUpUiState.isDeletedPermanently) {
                            payment = getPaymentForIdInteractor.getPaymentForId(transfer.paymentId!!)
                            deletePaymentInteractor.deletePayment(payment)
                        } else {
                            deleteTransferInteractor.deleteTransfer(transfer)
                            updateAccountBalanceInteractor.updateAccountBalance(
                                myAccountDetailScreenUiState.selectedAccount.updateAccountBalance(
                                    action.operationToDelete.deleteOperation()
                                ),
                                transferRelatedAccount.updateAccountBalance(
                                    transferRelatedOperation.deleteOperation()
                                )
                            )
                        }
                        ClosePopUp()
                    }

                } else {
                    scope.launch {
                        try {
                            deleteOperationInteractor.deleteOperation(action.operationToDelete)
                            updateAccountBalanceInteractor.updateAccountBalance(
                                myAccountDetailScreenUiState.selectedAccount.updateAccountBalance(
                                    action.operationToDelete.deleteOperation()
                                )
                            )
                            ClosePopUp()
                        } catch (e: Exception) {
                            Log.e("TAG", "dispatchAction: ", e)
                        }

                    }


                    /* if (action.operationToDelete.beneficiaryAccountId != null) {
                         deleteOperationInteractor.deleteOperation(
                             getOperationForIdInteractor.getOperationForId(
                                 operationId = action.operationToDelete.distantOperationIdRef!!,
                                 accountId = action.operationToDelete.beneficiaryAccountId!!,
                             )
                         )
                         updateAccountBalanceInteractor.updateAccountBalanceOnDeleteOperation(
                             accountId = action.operationToDelete.beneficiaryAccountId!!,
                             deletedOperationAMount = action.operationToDelete.updatedAmount * -1,
                             oldAccountBalance = getAccountForIdInteractor.getAccountForId(
                                 action.operationToDelete.beneficiaryAccountId!!
                             ).accountBalance,
                         )
                     }
                     deleteOperationInteractor.deleteOperation(action.operationToDelete)
                     updateAccountBalanceInteractor.updateAccountBalanceOnDeleteOperation(
                         accountId = action.operationToDelete.accountId,
                         deletedOperationAMount = action.operationToDelete.updatedAmount,
                         oldAccountBalance = AppSubscriber.AppUiState.myAccountDetailScreenUiState.selectedAccountUi.accountBalance,
                     )*/
                }
            }
        }
    }

    private fun ClosePopUp() {
        updateState(
            GlobalAction.UpdateDeleteOperationPopUpState(
                AppActions.DeleteOperationPopUpAction.ClosePopUp
            )
        )
    }
}