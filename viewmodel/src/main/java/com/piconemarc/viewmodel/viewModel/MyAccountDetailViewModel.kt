package com.piconemarc.viewmodel.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.piconemarc.core.domain.interactor.account.GetAccountAndRelatedOperationsForAccountIdInteractor
import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.operation.GetOperationForIdInteractor
import com.piconemarc.core.domain.interactor.payment.GetPaymentForIdInteractor
import com.piconemarc.core.domain.interactor.transfer.GetTransferForIdInteractor
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.myAccountDetailScreenVMState_
import com.piconemarc.viewmodel.viewModel.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val GetAccountAndRelatedOperationsForAccountIdInteractor: GetAccountAndRelatedOperationsForAccountIdInteractor,
    private val getPaymentForIdInteractor: GetPaymentForIdInteractor,
    private val getTransferForIdInteractor: GetTransferForIdInteractor,
    private val getOperationForIdInteractor: GetOperationForIdInteractor

) : BaseViewModel<AppActions.MyAccountDetailScreenAction, ViewModelInnerStates.MyAccountDetailScreenVMState>(
    store,
    myAccountDetailScreenVMState_
) {
    val myAccountDetailState by uiState

    fun onStart(selectedAccountId : String){
        Log.i("TAG", "onStart: my account detail ")
        //init state
        viewModelScope.launch(block = { state.collectLatest { uiState.value = it } })
        //update interlayer title
        updateState(
            GlobalAction.UpdateBaseAppScreenVmState(
                AppActions.BaseAppScreenAction.UpdateInterlayerTiTle(com.piconemarc.model.R.string.detail)
            )
        )
        val id = try {
           selectedAccountId.toLong()
        } catch (e: ParseException) {
            Log.e("TAG", "dispatchAction: ", e)
            0
        }
        viewModelScope.launchOnIOCatchingError(
            block = {
                GetAccountAndRelatedOperationsForAccountIdInteractor.getAccountForIdWithRelatedOperationsAsFlow(id, this)
                    .collectLatest { accountWithRelatedOperations ->
                        dispatchAction(
                            AppActions.MyAccountDetailScreenAction.UpdateAccountAndMonthlyOperations(
                                selectedAccount = accountWithRelatedOperations.account,
                                relatedMonthlyOperations = accountWithRelatedOperations.relatedOperations.filter {
                                    it.emitDate.month.compareTo(
                                        Calendar.getInstance().get(Calendar.MONTH)
                                    ) == 0
                                }
                            )
                        )
                    }
            }
        )
    }
    fun onStop(selectedAccountId : String){
        Log.d("TAG", "onStop: my account detail ")

        val id = try {
            selectedAccountId.toLong()
        } catch (e: ParseException) {
            Log.e("TAG", "dispatchAction: ", e)
            0
        }
        viewModelScope.launchOnIOCatchingError(
            block = {
                val accountWithRelatedOperations = GetAccountAndRelatedOperationsForAccountIdInteractor.getAccountForIdWithRelatedOperations(id)
                dispatchAction(
                    AppActions.MyAccountDetailScreenAction.UpdateAccountAndMonthlyOperations(
                        selectedAccount = accountWithRelatedOperations.account,
                        relatedMonthlyOperations = accountWithRelatedOperations.relatedOperations.filter {
                            it.emitDate.month.compareTo(
                                Calendar.getInstance().get(Calendar.MONTH)
                            ) == 0
                        }
                    )
                )
            }
        )

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
                        GetAccountAndRelatedOperationsForAccountIdInteractor.getAccountForIdWithRelatedOperationsAsFlow(id, this)
                            .collectLatest { accountWithRelatedOperations ->
                                dispatchAction(
                                    AppActions.MyAccountDetailScreenAction.UpdateAccountAndMonthlyOperations(
                                        selectedAccount = accountWithRelatedOperations.account,
                                        relatedMonthlyOperations = accountWithRelatedOperations.relatedOperations.filter {
                                            it.emitDate.month.compareTo(
                                                Calendar.getInstance().get(Calendar.MONTH)
                                            ) == 0
                                        }
                                    )
                                )
                            }
                    }
                )
            }

            is AppActions.MyAccountDetailScreenAction.GetSelectedOperation -> {
                if (action.operation.paymentId != null) {
                    viewModelScope.launchOnIOCatchingError(
                        block = {
                            val relatedPayment =
                                getPaymentForIdInteractor.getPaymentForId(action.operation.paymentId!!)
                            popUpPaymentMessage(relatedPayment)
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
                            popUpTransferMessage(relatedAccountName)
                        }
                    )
                }
            }
            else -> {updateState(GlobalAction.UpdateMyAccountDetailScreenState(action))}
        }
    }

    private suspend fun popUpTransferMessage(relatedAccountName: String) {
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

    private suspend fun popUpPaymentMessage(relatedPayment: PaymentUiModel) {
        updateState(
            GlobalAction.UpdateMyAccountDetailScreenState(
                AppActions.MyAccountDetailScreenAction.UpdateOperationMessage(
                    getPaymentMessage(relatedPayment)
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

    private fun getPaymentMessage(relatedPayment: PaymentUiModel) =
        if (relatedPayment.endDate != null) "This Payment will end on : ${
            SimpleDateFormat(
                "MMMM/yy",
                Locale.FRANCE
            ).format(relatedPayment.endDate!!)
        }"
        else "This payment don't have end date"
}