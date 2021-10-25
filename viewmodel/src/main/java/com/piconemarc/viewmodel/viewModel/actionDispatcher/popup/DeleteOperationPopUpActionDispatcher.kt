package com.piconemarc.viewmodel.viewModel.actionDispatcher.popup

import android.util.Log
import com.piconemarc.core.domain.interactor.account.GetAccountForIdInteractor
import com.piconemarc.core.domain.interactor.account.UpdateAccountBalanceInteractor
import com.piconemarc.core.domain.interactor.operation.DeleteOperationInteractor
import com.piconemarc.core.domain.interactor.operation.GetOperationForIdInteractor
import com.piconemarc.viewmodel.ActionDispatcher
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.myAccountDetailScreenUiState
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class DeleteOperationPopUpActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val deleteOperationInteractor: DeleteOperationInteractor,
    private val getOperationForIdInteractor: GetOperationForIdInteractor,
    private val getAccountForIdInteractor: GetAccountForIdInteractor,
    private val updateAccountBalanceInteractor: UpdateAccountBalanceInteractor

) : ActionDispatcher {
    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(GlobalAction.UpdateDeleteOperationPopUpState(action))
        when (action) {
            is AppActions.DeleteOperationPopUpAction.InitPopUp -> {
                updateState(
                    GlobalAction.UpdateDeleteOperationPopUpState(
                        AppActions.DeleteOperationPopUpAction.UpdateOperationToDelete(action.operationToDelete)
                    )
                )

            }
            is AppActions.DeleteOperationPopUpAction.DeleteOperation -> {
                scope.launch {
                    deleteOperationInteractor.deleteOperation(action.operationToDelete)
                    updateAccountBalanceInteractor.updateAccountBalance(
                        myAccountDetailScreenUiState.selectedAccount.updateAccountBalance(
                            action.operationToDelete.deleteOperation()
                        )
                    )


                    /* if (action.operationToDelete.beneficiaryAccountId != null) {
                         deleteOperationInteractor.deleteOperation(
                             getOperationForIdInteractor.getOperationForId(
                                 operationId = action.operationToDelete.distantOperationIdRef!!,
                                 accountId = action.operationToDelete.beneficiaryAccountId!!,
                             )
                         )
                         updateAccountBalanceInteractor.updateAccountBalanceOnDeleteOperation(
                             accountId = action.operationToDelete.beneficiaryAccountId!!,
                             deletedOperationAMount = action.operationToDelete.updatedAmount * -1,
                             oldAccountBalance = getAccountForIdInteractor.getAccountForId(
                                 action.operationToDelete.beneficiaryAccountId!!
                             ).accountBalance,
                         )
                     }
                     deleteOperationInteractor.deleteOperation(action.operationToDelete)
                     updateAccountBalanceInteractor.updateAccountBalanceOnDeleteOperation(
                         accountId = action.operationToDelete.accountId,
                         deletedOperationAMount = action.operationToDelete.updatedAmount,
                         oldAccountBalance = AppSubscriber.AppUiState.myAccountDetailScreenUiState.selectedAccountUi.accountBalance,
                     )*/
                }
            }
        }
    }
}