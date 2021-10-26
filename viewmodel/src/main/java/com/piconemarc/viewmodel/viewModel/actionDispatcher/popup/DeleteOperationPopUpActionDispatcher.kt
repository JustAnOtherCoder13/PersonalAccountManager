package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.account.UpdateAccountBalanceInteractor
import com.piconemarc.core.domain.interactor.operation.DeleteOperationInteractor
import com.piconemarc.core.domain.interactor.operation.GetOperationForIdInteractor
import com.piconemarc.core.domain.interactor.payment.DeletePaymentInteractor
import com.piconemarc.core.domain.interactor.payment.GetPaymentForIdInteractor
import com.piconemarc.core.domain.interactor.transfer.DeleteTransferInteractor
import com.piconemarc.core.domain.interactor.transfer.GetTransferForIdInteractor
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.model.entity.TransferUiModel
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.launchCatchingError
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.deleteOperationPopUpUiState
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.myAccountDetailScreenUiState
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
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
                // get operation to delete from account detail
                updateState(
                    GlobalAction.UpdateDeleteOperationPopUpState(
                        AppActions.DeleteOperationPopUpAction.UpdateOperationToDelete(action.operationToDelete)
                    )
                )
                //if operation have transfer id get related account to inform
                // user that operation will deleted on distant account
                if (action.operationToDelete.transferId != null)
                    scope.launchCatchingError(
                        block = {
                            transfer = getTransferForIdInteractor.getTransferForId(action.operationToDelete.transferId!!)
                            transferRelatedOperation = getTransferRelatedOperation(action, transfer)
                            updateState(
                                GlobalAction.UpdateDeleteOperationPopUpState(
                                    AppActions.DeleteOperationPopUpAction.UpdateTransferRelatedAccount(
                                        getAccountForIdInteractor.getAccountForId(transferRelatedOperation.accountId)
                                    )
                                )
                            )
                        }
                    )
            }
            is AppActions.DeleteOperationPopUpAction.DeleteOperation -> {
                // if payment id exist and user want to delete operation and payment
                if (action.operationToDelete.paymentId != null
                    && deleteOperationPopUpUiState.isDeletedPermanently) {
                    scope.launchCatchingError(
                        block = {
                            payment = getPaymentForIdInteractor.getPaymentForId(action.operationToDelete.paymentId!!)
                            deletePaymentInteractor.deletePayment(payment)
                            deleteOperationInteractor.deleteOperation(action.operationToDelete)
                            updateAccountBalanceInteractor.updateAccountBalance(
                                myAccountDetailScreenUiState.selectedAccount.updateAccountBalance(
                                    action.operationToDelete.deleteOperation()
                                )
                            )
                        },
                        doOnSuccess = { closePopUp() }
                    )
                }
                // if transferId exist delete operations with cascade
                // and update sender and beneficiary account
                else if (action.operationToDelete.transferId != null) {

                    scope.launchCatchingError(
                        block = {
                            transfer = getTransferForIdInteractor.getTransferForId(action.operationToDelete.transferId!!)

                            transferRelatedOperation = getTransferRelatedOperation(action, transfer)
                            val transferRelatedAccount = getAccountForIdInteractor.getAccountForId(transferRelatedOperation.accountId)

                            // if payment id exist and user want to delete operation and payment
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
                        },
                        doOnSuccess = { closePopUp() }
                    )

                } else {
                    //else delete base operation and update account
                    scope.launchCatchingError(
                        block = { deleteOperationInteractor.deleteOperation(action.operationToDelete) }
                    )
                    scope.launchCatchingError(
                        block = {
                            updateAccountBalanceInteractor.updateAccountBalance(
                                myAccountDetailScreenUiState.selectedAccount.updateAccountBalance(
                                    action.operationToDelete.deleteOperation()
                                )
                            )
                        },
                        doOnSuccess = { closePopUp() }
                    )
                }
            }
        }
    }

    private suspend fun getTransferRelatedOperation(
        action: AppActions.DeleteOperationPopUpAction.DeleteOperation,
        transfer: TransferUiModel
    ) = getOperationForIdInteractor.getOperationForId(
        //if operation id is equal to senderOperationId
        // that means that distant operation is beneficiary one, else is sender one
        if (action.operationToDelete.id == transfer.senderOperationId) {
            transfer.beneficiaryOperationId
        } else {
            transfer.senderOperationId
        }
    )

    private suspend fun getTransferRelatedOperation(
        action: AppActions.DeleteOperationPopUpAction.InitPopUp,
        transfer: TransferUiModel
    ) = getOperationForIdInteractor.getOperationForId(
        //if operation id is equal to senderOperationId
        // that means that distant operation is beneficiary one, else is sender one
        if (action.operationToDelete.id == transfer.senderOperationId) {
            transfer.beneficiaryOperationId
        } else {
            transfer.senderOperationId
        }
    )

    private fun closePopUp() {
        updateState(
            GlobalAction.UpdateDeleteOperationPopUpState(
                AppActions.DeleteOperationPopUpAction.ClosePopUp
            )
        )
    }
}