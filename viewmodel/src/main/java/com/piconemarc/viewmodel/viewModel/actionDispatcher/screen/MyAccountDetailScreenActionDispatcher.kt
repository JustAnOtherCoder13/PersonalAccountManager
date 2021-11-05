package com.piconemarc.viewmodel.viewModel.actionDispatcher.screen

import android.util.Log
import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.operation.GetAllOperationsForAccountIdInteractor
import com.piconemarc.core.domain.interactor.operation.GetOperationForIdInteractor
import com.piconemarc.core.domain.interactor.payment.GetPaymentForIdInteractor
import com.piconemarc.core.domain.interactor.transfer.GetTransferForIdInteractor
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.launchOnIOCatchingError
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.myAccountDetailScreenUiState
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MyAccountDetailScreenActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val getAccountForIdInteractor: GetAccountForIdInteractor,
    private val getAllOperationsForAccountIdInteractor: GetAllOperationsForAccountIdInteractor,
    private val getPaymentForIdInteractor: GetPaymentForIdInteractor,
    private val getTransferForIdInteractor: GetTransferForIdInteractor,
    private val getOperationForIdInteractor: GetOperationForIdInteractor
) : ActionDispatcher {

    //todo got trouble only when select different account change account id when add or delete transfer
    //seems that if click on account go back and select another account,
    // remember all accounts and don't know witch to choose perhaps due to add and delete.
    //todo have to simplify add and delete
    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(GlobalAction.UpdateMyAccountDetailScreenState(action))
        Log.i("TAG", "dispatchAction account detail screen:  $action")
        when (action) {
            is AppActions.MyAccountDetailScreenAction.InitScreen -> {
                updateState(
                    GlobalAction.UpdateBaseAppScreenVmState(
                        AppActions.BaseAppScreenAction.UpdateInterlayerTiTle(com.piconemarc.model.R.string.detail)
                    )
                )
                scope.launchOnIOCatchingError(
                    block = {
                        Log.e("TAG", "dispatchAction before:  ${action.selectedAccount.id}")
                        getAllOperationsForAccountIdInteractor.getAllOperationsForAccountIdFlow(
                            action.selectedAccount.id
                        ).collect { accountOperations ->
                            Log.e("TAG", "dispatchAction operations:  ${action.selectedAccount.id}")

                            updateState(
                                GlobalAction.UpdateMyAccountDetailScreenState(
                                    AppActions.MyAccountDetailScreenAction.UpdateAccountMonthlyOperations(
                                        accountOperations.filter {
                                            it.emitDate.month.compareTo(
                                                Calendar.getInstance().get(Calendar.MONTH)
                                            ) == 0
                                        }
                                    )
                                ),
                            )
                        }

                    }
                )
                scope.launchOnIOCatchingError(
                    block = {
                        Log.i("TAG", "dispatchAction before account:  ${action.selectedAccount.id}")
                        getAccountForIdInteractor.getAccountForIdFlow(action.selectedAccount.id)
                            .collect {
                                Log.i("TAG", "dispatchAction account:  ${action.selectedAccount.id}")
                                updateState(
                                    GlobalAction.UpdateMyAccountDetailScreenState(
                                        AppActions.MyAccountDetailScreenAction.UpdateSelectedAccount(
                                            it
                                        )
                                    )
                                )
                            }
                    }
                )
            }
            is AppActions.MyAccountDetailScreenAction.GetSelectedOperation -> {
                //todo delay cause trouble on multiple click
                if (action.operation.paymentId != null){
                    scope.launchOnIOCatchingError(
                        block = {
                            val relatedPayment = getPaymentForIdInteractor.getPaymentForId(action.operation.paymentId!!)
                            updateState(
                                GlobalAction.UpdateMyAccountDetailScreenState(
                                    AppActions.MyAccountDetailScreenAction.UpdateOperationMessage(
                                        if (relatedPayment.endDate != null) "This Payment will end on : ${SimpleDateFormat("MMMM/yy",
                                            Locale.FRANCE).format(relatedPayment.endDate!!)}"
                                        else "This payment don't have end date"
                                    )
                                )
                            )
                            delay(2500)
                            updateState(
                                GlobalAction.UpdateMyAccountDetailScreenState(
                                    AppActions.MyAccountDetailScreenAction.UpdateOperationMessage(
                                       ""
                                    )
                                )
                            )
                        }
                    )
                }
                if (action.operation.transferId != null){
                    scope.launchOnIOCatchingError(
                        block = {
                            val relatedTransfer = getTransferForIdInteractor.getTransferForId(action.operation.transferId!!)
                            val relatedOperationId = if(action.operation.id == relatedTransfer.beneficiaryOperationId) relatedTransfer.senderOperationId else relatedTransfer.beneficiaryOperationId
                            val relatedOperation = getOperationForIdInteractor.getOperationForId(relatedOperationId)
                            val relatedAccountName = getAccountForIdInteractor.getAccountForId(relatedOperation.accountId).name
                            updateState(
                                GlobalAction.UpdateMyAccountDetailScreenState(
                                    AppActions.MyAccountDetailScreenAction.UpdateOperationMessage(
                                       "This operation is a transfer from : $relatedAccountName"
                                    )
                                )
                            )
                            delay(2500)
                            updateState(
                                GlobalAction.UpdateMyAccountDetailScreenState(
                                    AppActions.MyAccountDetailScreenAction.UpdateOperationMessage(
                                        ""
                                    )
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}