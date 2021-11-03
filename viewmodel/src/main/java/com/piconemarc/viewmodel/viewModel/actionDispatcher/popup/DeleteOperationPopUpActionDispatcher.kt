package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.operation.DeleteOperationInteractor
import com.piconemarc.core.domain.interactor.operation.GetOperationForIdInteractor
import com.piconemarc.core.domain.interactor.payment.DeletePaymentInteractor
import com.piconemarc.core.domain.interactor.transfer.GetTransferForIdInteractor
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.model.entity.TransferUiModel
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.launchOnIOCatchingError
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.deleteOperationPopUpUiState
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class DeleteOperationPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val deleteOperationInteractor: DeleteOperationInteractor,
    private val getOperationForIdInteractor: GetOperationForIdInteractor,
    private val getAccountForIdInteractor: GetAccountForIdInteractor,
    private val getTransferForIdInteractor: GetTransferForIdInteractor,
    private val deletePaymentInteractor: DeletePaymentInteractor

) : ActionDispatcher {
    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        var transfer: TransferUiModel
        var transferRelatedOperation: OperationUiModel

        updateState(GlobalAction.UpdateDeleteOperationPopUpState(action))
        when (action) {
            is AppActions.DeleteOperationPopUpAction.InitPopUp<*> -> {
                // get operation to delete from account detail
                updateState(
                    GlobalAction.UpdateDeleteOperationPopUpState(
                        AppActions.DeleteOperationPopUpAction.UpdateOperationToDelete(action.operationToDelete)
                    )
                )
                if (action.operationToDelete is OperationUiModel)
                //if operation have transfer id, get related account
                //to inform user that operation will deleted on distant account
                if (action.operationToDelete.transferId != null)
                    scope.launchOnIOCatchingError(
                        block = {
                            transfer =
                                getTransferForIdInteractor.getTransferForId(action.operationToDelete.transferId!!)
                            transferRelatedOperation = getOperationForIdInteractor.getOperationForId(
                                //if operation id is equal to senderOperationId
                                // that means that distant operation is beneficiary one, else is sender one
                                if (action.operationToDelete.id == transfer.senderOperationId) {
                                    transfer.beneficiaryOperationId
                                } else {
                                    transfer.senderOperationId
                                }
                            )
                            updateState(
                                GlobalAction.UpdateDeleteOperationPopUpState(
                                    AppActions.DeleteOperationPopUpAction.UpdateTransferRelatedAccount(
                                        getAccountForIdInteractor.getAccountForId(
                                            transferRelatedOperation.accountId
                                        )
                                    )
                                )
                            )
                        }
                    )
            }
            is AppActions.DeleteOperationPopUpAction.DeleteOperation<*> -> {

                when (action.operationToDelete){
                    is OperationUiModel ->{

                        if (action.operationToDelete.paymentId != null) {
                            // if payment id exist and user want to delete operation and payment
                            if (deleteOperationPopUpUiState.isRelatedOperationDeleted) {
                                scope.launchOnIOCatchingError(
                                    block = { deleteOperationInteractor.deleteOperationAndPayment(action.operationToDelete) },
                                    doOnSuccess = { closePopUp() }
                                )
                            }
                            //else delete only operation
                            else {
                                scope.launchOnIOCatchingError(
                                    block = { deleteOperationInteractor.deleteOperation(action.operationToDelete) },
                                    doOnSuccess = { closePopUp() }
                                )
                            }
                        }
                        // if transferId exist, delete transfer and related operations
                        else if (action.operationToDelete.transferId != null) {
                            scope.launchOnIOCatchingError(
                                block = {
                                    transfer =
                                        getTransferForIdInteractor.getTransferForId(action.operationToDelete.transferId!!)
                                    deleteOperationInteractor.deleteTransfer(
                                        action.operationToDelete,
                                        transfer
                                    )
                                },
                                doOnSuccess = { closePopUp() }
                            )

                        } else {
                            //else delete operation
                            scope.launchOnIOCatchingError(
                                block = { deleteOperationInteractor.deleteOperation(action.operationToDelete) },
                                doOnSuccess = { closePopUp() }
                            )
                        }
                    }
                    is PaymentUiModel -> {
                        //if user want to delete related operation
                        if (action.operationToDelete.operationId != null
                            && deleteOperationPopUpUiState.isRelatedOperationDeleted
                        ){
                            scope.launchOnIOCatchingError(
                                block = { deleteOperationInteractor.deletePaymentAndRelatedOperation(action.operationToDelete) },
                                doOnSuccess = { closePopUp() }
                            )
                        }
                        //else only delete payment
                        else{
                            scope.launchOnIOCatchingError(
                                block = {
                                    deletePaymentInteractor.deletePayment(action.operationToDelete)
                                },
                                doOnSuccess = { closePopUp() }

                            )
                        }
                    }
                }
            }
        }
    }


    private fun closePopUp() {
        updateState(
            GlobalAction.UpdateDeleteOperationPopUpState(
                AppActions.DeleteOperationPopUpAction.ClosePopUp
            )
        )
    }
}