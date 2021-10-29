package com.piconemarc.viewmodel.viewModel.actionDispatcher.screen

import android.util.Log
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.payment.GetAllPaymentForAccountIdInteractor
import com.piconemarc.viewmodel.*
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import java.security.PrivateKey
import javax.inject.Inject

class PaymentScreenActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalVmState>,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,

    ) : ActionDispatcher {

    override fun dispatchAction(action: UiAction, scope: CoroutineScope) {
        updateState(GlobalAction.UpdatePaymentScreenState(action))

        when(action){
            is AppActions.PaymentScreenAction.InitScreen -> {
                scope.launchCatchingError(
                  block = {
                      getAllAccountsInteractor.getAllAccountsWithRelatedPaymentAsFlow().collect {
                          Log.i("TAG", "dispatchAction: ${it[0]}")
                          updateState(
                              GlobalAction.UpdatePaymentScreenState(
                                  AppActions.PaymentScreenAction.UpdateAllAccounts(it)
                              )
                          )
                      }
                  }
                )
            }
            is AppActions.PaymentScreenAction.UpdatePaymentsForAccount -> {

            }
            is AppActions.PaymentScreenAction.UpdateBoxHeightForAccount -> {


            }
            is AppActions.PaymentScreenAction.UpdateAllAccounts -> {
                /*updateState(
                    GlobalAction.UpdatePaymentScreenState(
                        AppActions.PaymentScreenAction.UpdateAllAccounts(action.allAccounts)
                    )
                )*/
            }
        }
    }
}