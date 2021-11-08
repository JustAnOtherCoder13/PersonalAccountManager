package com.piconemarc.viewmodel.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.operation.GetAllOperationsForAccountIdInteractor
import com.piconemarc.core.domain.interactor.operation.GetOperationForIdInteractor
import com.piconemarc.core.domain.interactor.payment.GetPaymentForIdInteractor
import com.piconemarc.core.domain.interactor.transfer.GetTransferForIdInteractor
import com.piconemarc.viewmodel.viewModel.utils.DefaultStore
import com.piconemarc.viewmodel.viewModel.utils.launchOnIOCatchingError
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.myAccountDetailScreenVMState_
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.BaseViewModel
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MyAccountDetailViewModel @Inject constructor(
    store: DefaultStore<GlobalVmState>,
    private val getAccountForIdInteractor: GetAccountForIdInteractor,
    private val getAllOperationsForAccountIdInteractor: GetAllOperationsForAccountIdInteractor,
    private val getPaymentForIdInteractor: GetPaymentForIdInteractor,
    private val getTransferForIdInteractor: GetTransferForIdInteractor,
    private val getOperationForIdInteractor: GetOperationForIdInteractor

) : BaseViewModel<AppActions.MyAccountDetailScreenAction, ViewModelInnerStates.MyAccountDetailScreenVMState>(
    store,
    myAccountDetailScreenVMState_
) {

    init {
        //init state
        viewModelScope.launch(block = { state.collectLatest { uiState.value = it } })
        //update interlayer title
        updateState(
            GlobalAction.UpdateBaseAppScreenVmState(
                AppActions.BaseAppScreenAction.UpdateInterlayerTiTle(com.piconemarc.model.R.string.detail)
            )
        )
        // update selected account and related operation


    }

    override fun dispatchAction(action: AppActions.MyAccountDetailScreenAction) {
        updateState(GlobalAction.UpdateMyAccountDetailScreenState(action))
        when (action) {
            is AppActions.MyAccountDetailScreenAction.InitScreen -> {

                val id = try {
                    action.selectedAccountId.toLong()
                } catch (e: ParseException) {
                    Log.e("TAG", "dispatchAction: ", e)
                    0
                }

                viewModelScope.launchOnIOCatchingError(
                    block = {
                        getAllOperationsForAccountIdInteractor.getAllOperationsForAccountIdFlow(
                            id

                        ).collect { accountOperations ->
                            dispatchAction(
                                AppActions.MyAccountDetailScreenAction.UpdateAccountMonthlyOperations(
                                    accountOperations.filter {
                                        it.emitDate.month.compareTo(
                                            Calendar.getInstance().get(Calendar.MONTH)
                                        ) == 0
                                    }
                                )
                            )
                        }

                    }
                )
                viewModelScope.launchOnIOCatchingError(
                    block = {
                        getAccountForIdInteractor.getAccountForIdFlow(id)
                            .collect {
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
                if (action.operation.paymentId != null) {
                    viewModelScope.launchOnIOCatchingError(
                        block = {
                            val relatedPayment =
                                getPaymentForIdInteractor.getPaymentForId(action.operation.paymentId!!)
                            updateState(
                                GlobalAction.UpdateMyAccountDetailScreenState(
                                    AppActions.MyAccountDetailScreenAction.UpdateOperationMessage(
                                        if (relatedPayment.endDate != null) "This Payment will end on : ${
                                            SimpleDateFormat(
                                                "MMMM/yy",
                                                Locale.FRANCE
                                            ).format(relatedPayment.endDate!!)
                                        }"
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
                if (action.operation.transferId != null) {
                    viewModelScope.launchOnIOCatchingError(
                        block = {
                            val relatedTransfer =
                                getTransferForIdInteractor.getTransferForId(action.operation.transferId!!)
                            val relatedOperationId =
                                if (action.operation.id == relatedTransfer.beneficiaryOperationId) relatedTransfer.senderOperationId else relatedTransfer.beneficiaryOperationId
                            val relatedOperation =
                                getOperationForIdInteractor.getOperationForId(relatedOperationId)
                            val relatedAccountName =
                                getAccountForIdInteractor.getAccountForId(relatedOperation.accountId).name
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
            else -> {
            }
        }
    }


}