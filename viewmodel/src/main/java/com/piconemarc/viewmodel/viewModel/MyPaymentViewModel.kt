package com.piconemarc.viewmodel.viewModel


import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.piconemarc.core.domain.interactor.account.GetAllAccountsWithRelatedPaymentsInteractor
import com.piconemarc.core.domain.interactor.payment.PassAllPaymentsForThisMonthInteractor
import com.piconemarc.core.domain.interactor.payment.PassSinglePaymentForThisMonthInteractor
import com.piconemarc.model.entity.CategoryUiModel
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.paymentScreenVMState_
import com.piconemarc.viewmodel.viewModel.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MyPaymentViewModel @Inject constructor(
    store: DefaultStore<GlobalVmState>,
    private val getAllAccountsWithRelatedPaymentsInteractor: GetAllAccountsWithRelatedPaymentsInteractor,
    private val passSinglePaymentForThisMonthInteractor: PassSinglePaymentForThisMonthInteractor,
    private val passAllPaymentsForThisMonthInteractor: PassAllPaymentsForThisMonthInteractor
) : BaseViewModel<AppActions.PaymentScreenAction, ViewModelInnerStates.PaymentScreenVmState>(
    store,
    paymentScreenVMState_
) {
    val paymentState by uiState

    init {
        viewModelScope.launch(block = { state.collectLatest { uiState.value = it } })
    }

    fun onStart() {
        viewModelScope.launchOnIOCatchingError(
            block = {
                getAllAccountsWithRelatedPaymentsInteractor.getAllAccountsWithRelatedPaymentAsFlow(
                    this
                ).collect { accountsWithPaymentList ->
                    dispatchAction(
                        AppActions.PaymentScreenAction.UpdateAllAccounts(accountsWithPaymentList)
                    )
                }
            }
        )
    }

    override fun dispatchAction(action: AppActions.PaymentScreenAction) {
        when (action) {
            is AppActions.PaymentScreenAction.PassSinglePayment -> {
                viewModelScope.launchOnIOCatchingError(
                    block = {
                        passSinglePaymentForThisMonthInteractor.passPaymentForThisMonth(
                            OperationUiModel(
                                accountId = action.payment.accountId,
                                name = action.payment.name,
                                amount = action.payment.amount,
                                categoryId = CategoryUiModel().id,//todo replace with real category
                                emitDate = Calendar.getInstance().time,
                                paymentId = action.payment.id
                            ),
                            action.payment.id
                        )
                    },
                    doOnSuccess = {
                        viewModelScope.launch {
                            dispatchAction(
                                AppActions.PaymentScreenAction.UpdatePassPaymentToastMessage(
                                    "this payment have been passed  : ${action.payment.name}"
                                )
                            )
                            delay(1000)
                            dispatchAction(
                                AppActions.PaymentScreenAction.UpdatePassPaymentToastMessage(
                                    ""
                                )
                            )
                        }

                    }
                )
            }
            is AppActions.PaymentScreenAction.PassAllPaymentsForAccount -> {
                viewModelScope.launchOnIOCatchingError(
                    block = {
                        passAllPaymentsForThisMonthInteractor.passAllPaymentForAccountOnThisMonth(
                            allPaymentToPass = action.allPaymentsForAccount
                        )
                    },
                    doOnSuccess = {
                        viewModelScope.launch {
                            dispatchAction(
                                AppActions.PaymentScreenAction.UpdatePassPaymentToastMessage(
                                    "those payments have been passed  : ${action.allPaymentsForAccount.map { it.name }}"
                                )
                            )
                            delay(1000)
                            dispatchAction(
                                AppActions.PaymentScreenAction.UpdatePassPaymentToastMessage(
                                    ""
                                )
                            )
                        }
                    }
                )
            }
            else -> updateState(GlobalAction.UpdatePaymentScreenState(action))
        }
    }
}