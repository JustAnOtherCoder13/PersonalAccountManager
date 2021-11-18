package com.piconemarc.viewmodel.viewModel


import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.operation.GetOperationForIdInteractor
import com.piconemarc.core.domain.interactor.payment.AddPaymentAndOperationInteractor
import com.piconemarc.model.entity.CategoryUiModel
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
    private val getOperationForIdInteractor: GetOperationForIdInteractor,
    private val addPaymentAndOperationInteractor: AddPaymentAndOperationInteractor,
) : BaseViewModel<AppActions.PaymentScreenAction, ViewModelInnerStates.PaymentScreenVmState>(
    store,
    paymentScreenVMState_
) {
    val paymentState by uiState

    init {
        viewModelScope.launch(block = { state.collectLatest { uiState.value = it } })
    }

    fun onStart (){
        viewModelScope.launchOnIOCatchingError(
            block = {
                //todo have to pass cleaner way
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
                viewModelScope.launchOnIOCatchingError(
                    block = {
                        addPaymentAndOperationInteractor.passPaymentForThisMonth(
                            OperationUiModel(
                                accountId = action.payment.accountId,
                                name = action.payment.name,
                                amount = action.payment.amount ,
                                categoryId = CategoryUiModel().id,//todo replace with real category
                                emitDate = Calendar.getInstance().time ,
                                paymentId = action.payment.id
                            ),
                            action.payment.id
                        )
                    }
                )
            }
            is AppActions.PaymentScreenAction.PassAllPaymentsForAccount -> {
                viewModelScope.launchOnIOCatchingError(
                    block = {
                        addPaymentAndOperationInteractor.passAllPaymentForAccountOnThisMonth(allPaymentToPass = action.allPaymentsForAccount)
                    }
                )
            }
            else ->updateState(GlobalAction.UpdatePaymentScreenState(action))
        }

    }
}