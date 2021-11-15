package com.piconemarc.viewmodel.viewModel


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.operation.GetOperationForIdInteractor
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.paymentScreenVMState_
import com.piconemarc.viewmodel.viewModel.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MyPaymentViewModel @Inject constructor(
    store: DefaultStore<GlobalVmState>,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
    private val getOperationForIdInteractor: GetOperationForIdInteractor
) : BaseViewModel<AppActions.PaymentScreenAction, ViewModelInnerStates.PaymentScreenVmState>(
    store,
    paymentScreenVMState_
) {
    val paymentState by uiState
    init {
        viewModelScope.launch(block = { state.collectLatest { uiState.value = it } })
        viewModelScope.launchOnIOCatchingError(
            block = {
                getAllAccountsInteractor.getAllAccountsWithRelatedPaymentAsFlow(this).collect {accountsWithPaymentList ->
                    accountsWithPaymentList.forEach {accountWithPayment ->
                        accountWithPayment.relatedPayment.forEach {payment ->
                            var relatedOperation  = OperationUiModel()
                            if (payment.operationId != null) {
                                relatedOperation =
                                    getOperationForIdInteractor.getOperationForId(payment.operationId!!)
                            }
                            payment.isPaymentPassForThisMonth = relatedOperation.paymentId != null && relatedOperation.emitDate.month.compareTo(Calendar.getInstance().time.month) == 0
                        }
                    }
                    dispatchAction(
                        AppActions.PaymentScreenAction.UpdateAllAccounts(accountsWithPaymentList)
                    )
                }
            }
        )
    }

    override fun dispatchAction(action: AppActions.PaymentScreenAction) {
        when(action){
            is AppActions.PaymentScreenAction.PassSinglePayment -> {
                Log.d("TAG", "dispatchAction: ")
                //todo add new operation, and update payment related operation id
            }
            else ->updateState(GlobalAction.UpdatePaymentScreenState(action))
        }

    }
}