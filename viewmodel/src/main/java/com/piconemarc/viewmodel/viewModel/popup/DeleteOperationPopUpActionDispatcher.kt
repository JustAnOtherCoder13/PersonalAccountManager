package com.piconemarc.viewmodel.viewModel.popup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.operation.DeleteOperationAndPaymentInteractor
import com.piconemarc.core.domain.interactor.operation.DeleteOperationInteractor
import com.piconemarc.core.domain.interactor.operation.GetOperationForIdInteractor
import com.piconemarc.core.domain.interactor.payment.DeletePaymentAndRelatedOperationInteractor
import com.piconemarc.core.domain.interactor.payment.DeletePaymentInteractor
import com.piconemarc.core.domain.interactor.transfer.DeleteTransferInteractor
import com.piconemarc.core.domain.interactor.transfer.GetTransferForIdInteractor
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.model.entity.TransferUiModel
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.deleteOperationPopUpVMState_
import com.piconemarc.viewmodel.viewModel.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteOperationPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val deleteOperationInteractor: DeleteOperationInteractor,
    private val getOperationForIdInteractor: GetOperationForIdInteractor,
    private val getAccountForIdInteractor: GetAccountForIdInteractor,
    private val getTransferForIdInteractor: GetTransferForIdInteractor,
    private val deletePaymentInteractor: DeletePaymentInteractor,
    private val deleteOperationAndPaymentInteractor: DeleteOperationAndPaymentInteractor,
    private val deletePaymentAndRelatedOperationInteractor: DeletePaymentAndRelatedOperationInteractor,
    private val deleteTransferInteractor: DeleteTransferInteractor

) : ActionDispatcher<ViewModelInnerStates.DeleteOperationPopUpVMState> {

    override val state: MutableStateFlow<ViewModelInnerStates.DeleteOperationPopUpVMState> =
        deleteOperationPopUpVMState_
    override val uiState: MutableState<ViewModelInnerStates.DeleteOperationPopUpVMState> =
        mutableStateOf(state.value)

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        var transfer: TransferUiModel
        var transferRelatedOperation: OperationUiModel


        updateState(GlobalAction.UpdateDeleteOperationPopUpState(action))
        scope.launch { state.collectLatest { uiState.value = it } }
        when (action) {
            is AppActions.DeleteOperationPopUpAction.InitPopUp<*> -> {
                // get operation to delete from account detail
                updateState(
                    GlobalAction.UpdateDeleteOperationPopUpState(
                        AppActions.DeleteOperationPopUpAction.UpdateOperationToDelete(action.operationToDelete)
                    )
                )
                //if operation to delete is not a payment
                //and have transfer id, get related account
                //to inform user that operation will be deleted on distant account
                if (action.operationToDelete is OperationUiModel
                    && action.operationToDelete.transferId != null
                ) {
                    scope.launchOnIOCatchingError(
                        block = {
                            transfer =
                                getTransferForIdInteractor.getTransferForId(action.operationToDelete.transferId!!)
                            transferRelatedOperation =
                                getOperationForIdInteractor.getOperationForId(
                                    //if operation id is equal to senderOperationId
                                    // that means that distant operation is beneficiary one,
                                    // else is sender one
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
            }
            is AppActions.DeleteOperationPopUpAction.DeleteOperation<*> -> {
                when (action.operationToDelete) {
                    is OperationUiModel -> {
                        when (action.operationToDelete.paymentId) {//check payment id
                            null -> {// if null check transfer id
                                when (action.operationToDelete.transferId) {
                                    null -> { // if null is base operation
                                        scope.launchOnIOCatchingError(
                                            block = {
                                                deleteOperationInteractor.deleteOperation(
                                                    action.operationToDelete
                                                )
                                            },
                                            doOnSuccess = { closePopUp() }
                                        )
                                    }
                                    else -> {//if transfer id exist
                                        scope.launchOnIOCatchingError(
                                            block = {
                                                transfer =
                                                    getTransferForIdInteractor.getTransferForId(
                                                        action.operationToDelete.transferId!!
                                                    )
                                                deleteTransferInteractor.deleteTransfer(
                                                    action.operationToDelete,
                                                    transfer
                                                )
                                            },
                                            doOnSuccess = { closePopUp() }
                                        )
                                    }
                                }
                            }
                            else -> { // if payment id exist
                                when (uiState.value.isRelatedOperationDeleted) {
                                    true -> { // and user want to delete operation and payment
                                        scope.launchOnIOCatchingError(
                                            block = {
                                                deleteOperationAndPaymentInteractor.deleteOperationAndPayment(
                                                    action.operationToDelete
                                                )
                                            },
                                            doOnSuccess = { closePopUp() }
                                        )
                                    }
                                    false -> { //else delete only operation
                                        scope.launchOnIOCatchingError(
                                            block = {
                                                deleteOperationInteractor.deleteOperation(
                                                    action.operationToDelete
                                                )
                                            },
                                            doOnSuccess = { closePopUp() }
                                        )
                                    }
                                }
                            }
                        }
                    }
                    is PaymentUiModel -> {
                        //if user want to delete related operation
                        if (action.operationToDelete.operationId != null
                            && uiState.value.isRelatedOperationDeleted
                        ) {
                            scope.launchOnIOCatchingError(
                                block = {
                                    deletePaymentAndRelatedOperationInteractor.deletePaymentAndRelatedOperation(
                                        action.operationToDelete
                                    )
                                },
                                doOnSuccess = { closePopUp() }
                            )
                        }
                        //else only delete payment
                        else {
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