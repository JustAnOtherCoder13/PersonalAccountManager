package com.piconemarc.viewmodel.viewModel

import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.payment.GetAllPaymentForAccountIdInteractor
import com.piconemarc.core.domain.utils.Constants.TODAY
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.model.getCalendarDate
import com.piconemarc.viewmodel.viewModel.popup.*
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.baseAppScreenVmState_
import com.piconemarc.viewmodel.viewModel.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    store: DefaultStore<GlobalVmState>,
    private val addOperationPopUpActionDispatcher: AddOperationPopUpActionDispatcher,
    private val deleteAccountPopUpActionDispatcher: DeleteAccountPopUpActionDispatcher,
    private val addAccountPopUpActionDispatcher: AddAccountPopUpActionDispatcher,
    private val deleteOperationPopUpActionDispatcher: DeleteOperationPopUpActionDispatcher,
    private val deleteObsoletePaymentPopUpActionDispatcher: DeleteObsoletePaymentPopUpActionDispatcher,

    private val getAllAccountsInteractor: GetAllAccountsInteractor,
    private val getAllPaymentForAccountIdInteractor: GetAllPaymentForAccountIdInteractor
) : BaseViewModel<UiAction, ViewModelInnerStates.BaseAppScreenVmState>(
    store,
    baseAppScreenVmState_
) {

    private var addOperationPopUpJob: Job? = null
    private var deleteAccountPopUpJob: Job? = null
    private var addAccountPopUpJob: Job? = null
    private var deleteOperationPopUpJob: Job? = null
    private var deleteObsoletePaymentPopUpJob: Job? = null

    val addOperationPopUpState by addOperationPopUpActionDispatcher.uiState
    val deleteOperationPopUpState by deleteOperationPopUpActionDispatcher.uiState
    val addAccountPopUpState by addAccountPopUpActionDispatcher.uiState
    val deleteAccountPopUpState by deleteAccountPopUpActionDispatcher.uiState
    val deleteObsoletePaymentPopUpState by deleteObsoletePaymentPopUpActionDispatcher.uiState

    val appUiState by uiState

    //List pop up to close them on back press if opened
    val popUpStates = listOf(
        Pair(
            addAccountPopUpActionDispatcher.uiState,
            AppActions.AddAccountPopUpAction.ClosePopUp
        ),
        Pair(
            deleteAccountPopUpActionDispatcher.uiState,
            AppActions.DeleteAccountAction.ClosePopUp
        ),
        Pair(
            addOperationPopUpActionDispatcher.uiState,
            AppActions.AddOperationPopupAction.ClosePopUp
        ),
        Pair(
            deleteOperationPopUpActionDispatcher.uiState,
            AppActions.DeleteOperationPopUpAction.ClosePopUp
        )
    )



    init {
        //init state
        viewModelScope.launch(block = { state.collectLatest { uiState.value = it } })
        dispatchAction(AppActions.BaseAppScreenAction.InitScreen)

    }

    override fun dispatchAction(action: UiAction) {
        when (action) {
            is AppActions.BaseAppScreenAction -> {
                when (action) {
                    is AppActions.BaseAppScreenAction.InitScreen -> {
                        viewModelScope.launchOnIOCatchingError(
                            block = {
                                getAllAccountsInteractor.getAllAccountsAsFlow(this)
                                    .collectLatest { allAccounts ->
                                        dispatchAction(
                                            AppActions.BaseAppScreenAction.UpdateAccounts(
                                                allAccounts
                                            )
                                        )
                                    }
                            }
                        )

                        viewModelScope.launchOnIOCatchingError(
                            block = {
                                val obsoletePaymentToDeleteList: List<PaymentUiModel> =
                                    getAllPaymentForAccountIdInteractor.getAllPayments().filter {
                                        it.endDate != null
                                                && getCalendarDate(it.endDate).get(Calendar.MONTH) < getCalendarDate(TODAY).get(Calendar.MONTH)
                                                && getCalendarDate(it.endDate).get(Calendar.YEAR) <= getCalendarDate(TODAY).get(Calendar.YEAR)
                                    }
                                dispatchAction(
                                    AppActions.DeleteObsoletePaymentPopUpAction.UpdateObsoletePaymentList(
                                        obsoletePaymentToDeleteList
                                    )
                                )
                                if (obsoletePaymentToDeleteList.isNotEmpty())
                                    dispatchAction(
                                        AppActions.DeleteObsoletePaymentPopUpAction.InitPopUp
                                    )
                            }
                        )
                    }
                    else -> {
                        updateState(GlobalAction.UpdateBaseAppScreenVmState(action))
                    }
                }
            }
            //launch job for each pop up when action is dispatched, cancel job on close
            is AppActions.AddOperationPopupAction -> {
                addOperationPopUpJob = viewModelScope.launch {
                    addOperationPopUpActionDispatcher.dispatchAction(action, this)
                }
                if (action is AppActions.AddOperationPopupAction.ClosePopUp) {
                    addOperationPopUpJob?.cancel()
                }
            }

            is AppActions.DeleteOperationPopUpAction -> {
                deleteAccountPopUpJob = viewModelScope.launch {
                    deleteOperationPopUpActionDispatcher.dispatchAction(action, this)
                }
                if (action is AppActions.DeleteOperationPopUpAction.ClosePopUp)
                    deleteOperationPopUpJob?.cancel()
            }

            is AppActions.DeleteAccountAction -> {
                deleteAccountPopUpJob = viewModelScope.launch {
                    deleteAccountPopUpActionDispatcher.dispatchAction(action, this)
                }
                if (action is AppActions.DeleteAccountAction.ClosePopUp)
                    deleteAccountPopUpJob?.cancel()
            }

            is AppActions.AddAccountPopUpAction -> {
                addAccountPopUpJob = viewModelScope.launch {
                    addAccountPopUpActionDispatcher.dispatchAction(action, this)
                }
                if (action is AppActions.AddAccountPopUpAction.ClosePopUp)
                    addAccountPopUpJob?.cancel()
            }

            is AppActions.DeleteObsoletePaymentPopUpAction -> {
                deleteObsoletePaymentPopUpJob = viewModelScope.launch {
                    deleteObsoletePaymentPopUpActionDispatcher.dispatchAction(action, this)
                }
                if (action is AppActions.DeleteObsoletePaymentPopUpAction.ClosePopUp)
                    deleteObsoletePaymentPopUpJob?.cancel()
            }
        }
    }
}